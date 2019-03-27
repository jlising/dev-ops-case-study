import { environment } from '../../../environments/environment';
import { Component, OnInit, ViewEncapsulation, Inject } from '@angular/core';
import { ToasterService } from 'angular2-toaster';
import { HttpErrorResponse } from '@angular/common/http';

import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

import { AppGlobal } from '../../app.global';
import { ActionItemsComponent } from './action-items.component';
import { ActionItem } from './action-item';
import { ActionItemsService } from './action-items.service';
import { EmployeesService } from '../employees/employees.service';
import { Employee } from '../common/employee';
import { EmployeeActionItem } from './employee-action-item';

@Component({
  selector: 'app-assign-action-item-dialog',
  templateUrl: './assign-action-item-dialog.component.html',
  styleUrls: ['./assign-action-item-dialog.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class AssignActionItemDialogComponent implements OnInit {
   public actionItemDialogModel : ActionItem = new ActionItem();
   public formSubmitted : boolean = false;

    public employees : Employee[];
    public deliveryManagers;
    public totalRecords : number;
    public assignedEmployees = {};
    public assignedEmployeesFromDB = [];
    public selectedAll : any;
    public deliveryManagerId : string;

    public pageParams = {
                            page : environment.pagingOptions.start,
                            size : environment.pagingOptions.size,
                            order : environment.pagingOptions.sortOrder,
                            sort :  environment.pagingOptions.sort,
                            searchString : '',
                            deliveryManagerId : ''
            };


  // Inject private classes via constructor
  constructor( public _appGlobal : AppGlobal,
               private _actionItemsService : ActionItemsService,
               private _employeesService : EmployeesService,
               private _toasterService : ToasterService,
               public dialogRef: MatDialogRef<ActionItemsComponent>, @Inject(MAT_DIALOG_DATA) public data: any) {}

  // Apply definition since we implemented OnInit
  ngOnInit() {
    this._getActionItem(this.data.id);
    this._getDeliveryManagers("");
    this.search();
    this._getAssignedEmployeesFromDB();
  }

  /**
     * Search
     */
    public search(){
       if(this.pageParams.deliveryManagerId === ''){
        this._getEmployees(this.pageParams.searchString);
       }else{
        this._getEmployeesByDeliveryManager(this.pageParams.deliveryManagerId, this.pageParams.searchString);
       }
    }

    /**
     * Reset search
     */
    public resetSearch(){
        this.search();
    }

    /**
     * Detect page change
     */
    public pageChanged(event : any):void {
        this.pageParams.page = event.page;
        this.search();
    }

  /**
   * Get action item by ID
   * @param id : String
   */
  private _getActionItem(id : string){
      id = id || '';
      this._actionItemsService.getById(id).subscribe(
                response => {
                      this.actionItemDialogModel = response;
                      if(response.dateDue != "" && response.dateDue != null){
                          this.actionItemDialogModel.dateDue = new Date(response.dateDue);
                      }
                },
                (err: HttpErrorResponse) => {
                  this._toasterService.pop('error', '', err.status + ' ' + err.statusText);
                  if (err.error instanceof Error) {
                    console.log("Client-side error occured.");
                  } else {
                    console.log("Server-side error occured.");
                  }
                }
          );
  }

   /**
     * Get employees
     * @param searchName : string
     */
    private _getEmployees(searchName : string){
        searchName = searchName || '';

        //Build ur parameters
        let urlParams = 'search=' + searchName;
        urlParams += '&page=' + this.pageParams.page;
        urlParams += '&size=' + this.pageParams.size;
        urlParams += '&sort=' + this.pageParams.sort;
        urlParams += '&order=' + this.pageParams.order;

        let offset =  (this.pageParams.page - 1) > 0 ? ((this.pageParams.page * this.pageParams.size) - this.pageParams.size) + 1 : this.pageParams.page;

        this._employeesService.getList(urlParams).subscribe(
                  response => {
                    this.employees = response.results;
                    this.totalRecords = response.totalElements;
                    this.selectedAll = false;
                  },
                  (err: HttpErrorResponse) => {
                    this._toasterService.pop('error', '', err.status + ' ' + err.statusText);
                    if (err.error instanceof Error) {
                      console.log("Client-side error occured.");
                    } else {
                      console.log("Server-side error occured.");
                    }
                  }
            );
    }

    /**
     * Get delivery managers
     * @param searchName : string
     */
    private _getDeliveryManagers(searchName : string){
      searchName = searchName || '';

      //Build ur parameters
      let urlParams = 'search=' + searchName;
          urlParams += '&size=5000';
      this._employeesService.getDeliveryManagers(urlParams).subscribe(
                response => {
                  this.deliveryManagers = response.results;
                },
                (err: HttpErrorResponse) => {
                  this._toasterService.pop('error', '', err.status + ' Error in retrieving the list of managers. (' + err.statusText + ')');
                  if (err.error instanceof Error) {
                    console.log("Client-side error occured.");
                  } else {
                    console.log("Server-side error occured.");
                  }
                }
          );
    }

    /**
         * Get employees
         * @param searchName : string
         */
        private _getEmployeesByDeliveryManager(id : string, searchName : string){
            searchName = searchName || '';

            //Build ur parameters
            let urlParams = 'search=' + searchName;
            urlParams += '&page=' + this.pageParams.page;
            urlParams += '&size=' + this.pageParams.size;
            urlParams += '&sort=' + this.pageParams.sort;
            urlParams += '&order=' + this.pageParams.order;

            let offset =  (this.pageParams.page - 1) > 0 ? ((this.pageParams.page * this.pageParams.size) - this.pageParams.size) + 1 : this.pageParams.page;

            this._employeesService.getByDeliveryManager(id, urlParams).subscribe(
                      response => {
                        this.employees = response.results;
                        this.totalRecords = response.totalElements;
                        this.selectedAll = false;
                      },
                      (err: HttpErrorResponse) => {
                        this._toasterService.pop('error', '', err.status + ' ' + err.statusText);
                        if (err.error instanceof Error) {
                          console.log("Client-side error occured.");
                        } else {
                          console.log("Server-side error occured.");
                        }
                      }
                );
        }

        public saveAssignments(){
           if(!this.formSubmitted){
               /*let selectedEmployeeIds = Object.keys(this.assignedEmployees).filter((item, index) => {
                 return this.assignedEmployees[item];
               });*/

               let selectedEmployeeIds = Object.entries(this.assignedEmployees).map(([key, value]) => ({ "employee_id" : key, "selected" : value}))

               this.formSubmitted = true;
               this._actionItemsService.assign(this.data.id, selectedEmployeeIds).subscribe(
                         response => {
                            this._toasterService.pop('info', '', 'Action item has been assigned successfully.');
                            this.dialogRef.close(true);
                            this.formSubmitted = false;
                         },
                         (err: HttpErrorResponse) => {
                           this._toasterService.pop('error', '', err.status + ' ' + err.statusText);
                           if (err.error instanceof Error) {
                             console.log("Client-side error occured.");
                           } else {
                             console.log("Server-side error occured.");
                           }
                         }
               );
           }
        }

        public _getAssignedEmployeesFromDB(){
               // Get the list of assigned employees based from employees[]
                 this._actionItemsService.getAssigned(this.data.id).subscribe(
                      response => {
                            if(response.results != undefined){
                                 let ids = {};
                                 response.results.forEach(function(row, key){
                                    ids[row.employee.id] = true;
                                 });

                                 this.assignedEmployees = ids;
                            }
                      },
                      (err: HttpErrorResponse) => {
                        this._toasterService.pop('error', '', err.status + ' ' + err.statusText);
                        if (err.error instanceof Error) {
                          console.log("Client-side error occured.");
                        } else {
                          console.log("Server-side error occured.");
                        }
                      }
                );
        }

        public selectAll(selected : boolean){
            let ids = this.assignedEmployees;
            this.employees.forEach(function(row, key){
                ids[row.id] = selected;
            });

            this.assignedEmployees = ids;
        }

        public checkIfSelectedAll(selected : boolean){
            this.selectedAll = this.selectedAll && selected;
        }
}

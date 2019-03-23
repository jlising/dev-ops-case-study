import { environment } from '../../../environments/environment';
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { ToasterService } from 'angular2-toaster';
import { MatDialog, MatDialogRef } from '@angular/material'

import { AppGlobal } from '../../app.global';
import { EmployeesService } from'./employees.service';
import { Employee } from '../common/employee';
import { EmployeeProfileDialogComponent } from './employee-profile-dialog.component';
import { EmployeeW3DialogComponent } from './employee-w3-dialog.component';

@Component({
    selector: 'app-employees',
    templateUrl: './employees.component.html',
    styleUrls: ['./employees.component.css'],
    encapsulation: ViewEncapsulation.None
})

export class EmployeesComponent  implements OnInit {
    public employees : Employee[];
    public totalRecords : number;
    public employeeBasicInformationModel : Employee = new Employee();

    public pageParams = {
						page : environment.pagingOptions.start,
						size : environment.pagingOptions.size,
						order : environment.pagingOptions.sortOrder,
						sort :  environment.pagingOptions.sort,
						searchString : '',
						emailFromW3 : ''
		};

    // Inject private classes via constructor
    constructor (public _appGlobal : AppGlobal,
                 private _employeesService : EmployeesService,
                 private _toasterService : ToasterService,
                 public _dialog: MatDialog){
    }

    //Apply definition since we implemented OnInit
    ngOnInit() {
        // View list of employees by default
        this._getEmployees(this.pageParams.searchString);
    }

    /**
     * Open employee profile
     * @param employeeId : string
     */
    public openEmployeeProfile(employeeId : string): void {

        // Open the dialog and pass the data
        let dialogRef = this._dialog.open(EmployeeProfileDialogComponent, {
          height: '520px',
          width: '800px',
          maxHeight: '520px',
          minHeight: '520px',
          minWidth:'800px',
          maxWidth: '800px',
          data: { employeeId : employeeId }
        });

        // After close, determine the selected employee
        dialogRef.afterClosed().subscribe(result => {

        });
    }

    /**
     * Open dialog and show the search result from W3
     * @param response : any
     */
    public openAddFromW3(response : any){
        // Open the dialog and pass the data
        let dialogRef = this._dialog.open(EmployeeW3DialogComponent, {
          height: '260px',
          width: '800px',
          maxHeight: '260px',
          minHeight: '260px',
          minWidth:'800px',
          maxWidth: '800px',
          data: response
        });

        // After close, determine the selected employee
        dialogRef.afterClosed().subscribe(result => {
            if(result != undefined){
                if(result){
                    this.search();
                    this.pageParams.emailFromW3 = "";
                }
            }
        });
    }

    /**
     * Get the list of employees
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
                     if(response.results != undefined){
                        //Just add a counter field
                        response.results.forEach(function(row, key){
                            row.counter = key + offset;

                            // Get the primary contact
                            row.employeeContacts.forEach(function(v, k){
                               if(v.priority == 1){
                                 row.employeeContacts = v;
                               }
                            });
                        });
                     }
                    this.employees = response.results;
					this.totalRecords = response.totalElements;
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
	 * Search
	 */
    public search(){
        this.pageParams.page = 1;
        this._getEmployees(this.pageParams.searchString);
    }

    /**
     * Refresh without changing the pagination
     */
    public refresh(){
        this._getEmployees(this.pageParams.searchString);
    }

	/**
	 * Reset search
	 */
    public resetSearch(){
        this.pageParams.searchString = '';
        this.search();
    }

    /**
	 * Reset form
	 */
    public resetForm(){
        this.employeeBasicInformationModel = new Employee();
    }

	/**
	 * Page changed
	 * @param event : any
	 */
    public pageChanged(event : any):void {
		this.pageParams.page = event.page;
		this._getEmployees(this.pageParams.searchString);
    }

    /**
     * Get from W3
     */
    public getEmployeeFromW3(){
        this._employeesService.getByW3Email(this.pageParams.emailFromW3.trim()).subscribe(
          response => {
            this.openAddFromW3(response);
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
     * Tag/Untag as lead
     * @param employeeId : string,
     * @param lead : boolean
     */
    public tagAsTeamLead(employeeId : string, lead : boolean){
        this._employeesService.tagAsTeamLead(employeeId,  lead).subscribe(
           response => {
                   if(lead){
                     this._toasterService.pop('info', '', 'The employee was tagged as team lead successfully.');
                   }else{
                     this._toasterService.pop('info', '', 'The employee was untagged as team lead successfully.');
                   }

                   this.refresh();
                }, (err: HttpErrorResponse) => {
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
     * Tag/Untag as people manager
     * @param employeeId : string,
     * @param manager : boolean
     */
    public tagAsPeopleManager(employeeId : string, manager : boolean){
        this._employeesService.tagAsPeopleManager(employeeId,  manager).subscribe(
           response => {
                   if(manager){
                     this._toasterService.pop('info', '', 'The employee was tagged as people manager successfully.');
                   }else{
                     this._toasterService.pop('info', '', 'The employee was untagged as people manager successfully.');
                   }

                   this.refresh();
                }, (err: HttpErrorResponse) => {
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
     * Tag/Untag as delivery manager
     * @param employeeId : string,
     * @param manager : boolean
     */
    public tagAsDeliveryManager(employeeId : string, manager : boolean){
        this._employeesService.tagAsDeliveryManager(employeeId,  manager).subscribe(
           response => {
                   if(manager){
                     this._toasterService.pop('info', '', 'The employee was tagged as delivery manager successfully.');
                   }else{
                     this._toasterService.pop('info', '', 'The employee was untagged as delivery manager successfully.');
                   }

                   this.refresh();
                }, (err: HttpErrorResponse) => {
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

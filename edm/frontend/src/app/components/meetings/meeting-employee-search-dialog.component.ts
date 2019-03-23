import { environment } from '../../../environments/environment';
import { Component, OnInit, ViewEncapsulation, Inject } from '@angular/core';
import { ToasterService } from 'angular2-toaster';
import { HttpErrorResponse } from '@angular/common/http';

import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { MeetingAddComponent } from './meeting-add.component';
import { EmployeesService } from '../employees/employees.service';
import { Employee } from '../common/employee';

import { EmployeeBasicInformationService } from '../profile/employee-basic-information.service';

@Component({
  selector: 'app-meeting-employee-search-dialog',
  templateUrl: './meeting-employee-search-dialog.component.html',
  styleUrls: ['./meeting-employee-search-dialog.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class MeetingEmployeeSearchDialogComponent implements OnInit {

  public employees : Employee[];
  public totalRecords : number;
  public origEmployeeId : string;

  public pageParams = {
  						page : environment.pagingOptions.start,
  						size : environment.pagingOptions.size,
  						order : environment.pagingOptions.sortOrder,
  						sort :  environment.pagingOptions.sort,
  						searchString : ''
  		};

  // Inject private classes via constructor
  constructor( private _employeesService : EmployeesService,
               private _employeeBasicInformationService : EmployeeBasicInformationService,
               private _toasterService : ToasterService,
               public dialogRef: MatDialogRef<MeetingAddComponent>, @Inject(MAT_DIALOG_DATA) public data: any) {}

  // Apply definition since we implemented OnInit
  ngOnInit() {
    this.origEmployeeId = this.data.employeeId;
    this.search();
  }

  /**
   * Search
   */
  public search(){
          switch(this.data.searchType){
            case "people-manager": this._getPeopleManagers(this.pageParams.searchString); break;
            case "delivery-manager": this._getDeliveryManagers(this.pageParams.searchString); break;
            case "team-lead": this._getTeamLeads(this.pageParams.searchString);break;
            case "backup": this._getEmployees(this.pageParams.searchString);break;
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
   * Get employees
   * @param searchName : string
   */
  private _getEmployees(searchName : String){
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
   * Get people managers
   * @param searchName : string
   */
  private _getPeopleManagers(searchName : string){
        searchName = searchName || '';

        //Build ur parameters
        let urlParams = 'search=' + searchName;
        urlParams += '&page=' + this.pageParams.page;
        urlParams += '&size=' + this.pageParams.size;
        urlParams += '&sort=' + this.pageParams.sort;
        urlParams += '&order=' + this.pageParams.order;

        this._employeeBasicInformationService.getPeopleManagers(urlParams).subscribe(
                  response => {
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
     * Get delivery managers
     * @param searchName : string
     */
    private _getDeliveryManagers(searchName : string){
          searchName = searchName || '';

          //Build ur parameters
          let urlParams = 'search=' + searchName;
          urlParams += '&page=' + this.pageParams.page;
          urlParams += '&size=' + this.pageParams.size;
          urlParams += '&sort=' + this.pageParams.sort;
          urlParams += '&order=' + this.pageParams.order;

          this._employeeBasicInformationService.getDeliveryManagers(urlParams).subscribe(
                    response => {
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
   * Get team leads
   * @param searchName : string
   */
  private _getTeamLeads(searchName : string){
     searchName = searchName || '';

     //Build ur parameters
     let urlParams = 'search=' + searchName;
     urlParams += '&page=' + this.pageParams.page;
     urlParams += '&size=' + this.pageParams.size;
     urlParams += '&sort=' + this.pageParams.sort;
     urlParams += '&order=' + this.pageParams.order;

     this._employeeBasicInformationService.getTeamLeads(urlParams).subscribe(
                response => {
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
}

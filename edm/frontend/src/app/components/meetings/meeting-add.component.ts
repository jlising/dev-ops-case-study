import { environment } from '../../../environments/environment';
import { Component, OnInit } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { MatDialog, MatDialogRef } from '@angular/material';

import { ToasterService } from 'angular2-toaster';
import { AppGlobal } from '../../app.global';
import { MeetingEmployeeSearchDialogComponent } from './meeting-employee-search-dialog.component';

import { EmployeeBasicInformationService } from '../profile/employee-basic-information.service';
import { Meeting } from './meeting';

@Component({
  selector: 'app-meeting-add',
  templateUrl: './meeting-add.component.html',
  styleUrls: ['./meeting-add.component.css']
})
export class MeetingAddComponent implements OnInit {
  public meetingModel : Meeting = new Meeting();
  public employees;

  constructor(public _appGlobal : AppGlobal,
              private _employeeBasicInformationService : EmployeeBasicInformationService,
              public _dialog: MatDialog,
              private _toasterService : ToasterService) { }

  ngOnInit() {
    this.meetingModel.facilitator = this._appGlobal.userSession.user;
    this._getEmployees("");
  }

  /**
   * Get employees
   * @param searchName : string
   */
  private _getEmployees(searchName : string){
    searchName = searchName || '';

    //Build ur parameters
    let urlParams = 'search=' + searchName;
    urlParams += '&size=5000';

    this._employeeBasicInformationService.getEmployees(urlParams).subscribe(
              response => {
                this.employees = response.results;
              },
              (err: HttpErrorResponse) => {
                this._toasterService.pop('error', '', err.status + ' Error in retrieving the list of employees. (' + err.statusText + ')');
                if (err.error instanceof Error) {
                  console.log("Client-side error occured.");
                } else {
                  console.log("Server-side error occured.");
                }
              }
        );
  }

  /**
   * Open search dialog
   * @param searchType : string
   */
  public openEmployeeSearchDialog(searchType : string): void {

      // Open the dialog and pass the data
      let dialogRef = this._dialog.open(MeetingEmployeeSearchDialogComponent, {
        height: '395px',
        width: '650px',
        maxHeight: '395px',
        minHeight: '395px',
        minWidth:'650px',
        maxWidth: '650px',
        data: { attendees : this.meetingModel.attendees}
      });

      // After close, determine the selected employee
      dialogRef.afterClosed().subscribe(result => {
          if(result != undefined){
              //this.searchDialogEmployeeId = result;
          }

          this.meetingModel.attendees = result;
      });
  }
}

import { environment } from '../../../environments/environment';
import { Component, OnInit, ViewEncapsulation, Inject } from '@angular/core';
import { ToasterService } from 'angular2-toaster';
import { HttpErrorResponse } from '@angular/common/http';

import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

import { EmployeesComponent } from './employees.component';
import { Employee } from '../common/employee';
import { EmployeeBasicInformationService } from '../profile/employee-basic-information.service';
import { Address } from '../common/address';

@Component({
  selector: 'app-employee-profile-dialog',
  templateUrl: './employee-profile-dialog.component.html',
  styleUrls: ['./employee-profile-dialog.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class EmployeeProfileDialogComponent implements OnInit {

  public employeeProfileDialogModel : Employee = new Employee();

  // Inject private classes via constructor
  constructor( private _employeeBasicInformationService : EmployeeBasicInformationService,
               private _toasterService : ToasterService,
               public dialogRef: MatDialogRef<EmployeesComponent>, @Inject(MAT_DIALOG_DATA) public data: any) {}

  // Apply definition since we implemented OnInit
  ngOnInit() {
    this._getEmployeeByID(this.data.employeeId);
  }

  /**
   * Get employee by ID
   * @param id : String
   */
  private _getEmployeeByID(id : string){
      id = id || '';
      this._employeeBasicInformationService.getBasicInformation(id).subscribe(
                response => {
                      this.employeeProfileDialogModel = response;
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

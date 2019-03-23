import { environment } from '../../../environments/environment';
import { Component, OnInit, ViewEncapsulation, Inject } from '@angular/core';
import { ToasterService } from 'angular2-toaster';
import { HttpErrorResponse } from '@angular/common/http';

import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

import { EmployeesComponent } from './employees.component';
import { Employee } from '../common/employee';
import { EmployeesService } from './employees.service';

@Component({
  selector: 'app-employee-w3-dialog',
  templateUrl: './employee-w3-dialog.component.html',
  styleUrls: ['./employee-w3-dialog.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class EmployeeW3DialogComponent implements OnInit {
  public employeeDialogModel : Employee = new Employee();

  public formSubmitted : boolean = false;

  // Inject private classes via constructor
  constructor( private _employeesService : EmployeesService,
               private _toasterService : ToasterService,
               public dialogRef: MatDialogRef<EmployeesComponent>, @Inject(MAT_DIALOG_DATA) public data: any) {}

  // Apply definition since we implemented OnInit
  ngOnInit() {}

  /**
   * Add employee
   */
  public addEmployee(){
        if(!this.formSubmitted){
            this.formSubmitted = true;
            this.employeeDialogModel.email = this.data.email;
            this._employeesService.addFromW3(this.employeeDialogModel).subscribe(
                      response => {
                         this._toasterService.pop('info', '', 'Employee information from W3 has been added successfully.');
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
}

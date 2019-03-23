import { environment } from '../../../environments/environment';
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { ToasterService } from 'angular2-toaster';
import { Observable } from 'rxjs/Observable';
import { MatDialog, MatDialogRef } from '@angular/material';

import { AppGlobal } from '../../app.global';
import { Employee } from '../common/employee';
import { EmployeeBasicInformationService } from './employee-basic-information.service';

import { EmployeeContact } from '../common/employee-contact';
import { Address } from '../common/address';
import { EmployeeContactDetailsService } from './employee-contact-details.service';

import { EmployeeProject } from '../common/employee-project';
import { EmployeeProjectService } from './employee-project.service';

import { EmployeeReimbursement } from '../common/employee-reimbursement';
import { EmployeeReimbursementService } from './employee-reimbursement.service';

import { EmployeeSearchDialogComponent } from './employee-search-dialog.component';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class ProfileComponent implements OnInit {
    public employeeBasicInformationModel : Employee = new Employee();
    public employeeContactDetailsModel : EmployeeContact = new EmployeeContact();
    public employeeProjectModel : EmployeeProject = new EmployeeProject();
    public employeeReimbursementsModel : EmployeeReimbursement = new EmployeeReimbursement();

    public employeeBasicInformationFormSubmitted : boolean = false;
    public employeeContactDetailsFormSubmitted : boolean = false;
    public employeeProjectFormSubmitted : boolean = false;
    public employeeReimbursementsFormSubmitted : boolean = false;

    public deliveryManagers;
    public peopleManagers;
    public teamleads;
    public employees;
    public searchDialogEmployeeId : string;

    // Inject private classes via constructor
    constructor (public _appGlobal : AppGlobal,
                 private _employeeBasicInformationService : EmployeeBasicInformationService,
                 private _employeeContactDetailsService : EmployeeContactDetailsService,
                 private _employeeProjectService : EmployeeProjectService,
                 private _employeeReimbursementService : EmployeeReimbursementService,
                 private _toasterService : ToasterService,
                 public _dialog: MatDialog){

                this.employeeBasicInformationModel.peopleManager = new Employee();

                this.employeeContactDetailsModel.homeEmployeeAddress = new Address();
                this.employeeContactDetailsModel.businessEmployeeAddress = new Address();

                this.employeeProjectModel.deliveryManager = new Employee();
                this.employeeProjectModel.teamLead = new Employee();
                this.employeeProjectModel.employeeBackup = new Employee();
    }

    // Apply definition since we implemented OnInit
    ngOnInit() {
        this._getPeopleManagers("");
        this._getDeliveryManagers("");
        this._getTeamLeads("");
        this._getEmployees("");

        this._getEmployeeBasicInformation(this._appGlobal.userSession.user.id);
        this._getEmployeeContactDetails(this._appGlobal.userSession.user.id);
        this._getEmployeeProject(this._appGlobal.userSession.user.id);
        this._getEmployeeReimbursements(this._appGlobal.userSession.user.id);
    }

    /**
     * Update all sections
     * @param employeeId : string
     */
    public updateAll(employeeId : string){
        this._updateEmployeeBasicInformation(employeeId);
    }

    /**
     * Open search dialog
     * @param searchType : string
     */
    public openEmployeeSearchDialog(searchType : string): void {

        // Set the default selected based from searchType
        switch(searchType){
            case "people-manager": this.searchDialogEmployeeId = this.employeeBasicInformationModel.peopleManager.id; break;
            case "delivery-manager": this.searchDialogEmployeeId = this.employeeProjectModel.deliveryManager.id; break;
            case "team-lead": this.searchDialogEmployeeId = this.employeeProjectModel.teamLead.id; break;
            case "backup" : this.searchDialogEmployeeId = this.employeeProjectModel.employeeBackup.id; break;
        }

        // Open the dialog and pass the data
        let dialogRef = this._dialog.open(EmployeeSearchDialogComponent, {
          height: '395px',
          width: '650px',
          maxHeight: '395px',
          minHeight: '395px',
          minWidth:'650px',
          maxWidth: '650px',
          data: { employeeId : this.searchDialogEmployeeId, searchType : searchType}
        });

        // After close, determine the selected employee
        dialogRef.afterClosed().subscribe(result => {
            if(result != undefined){
                this.searchDialogEmployeeId = result;
            }

            // Match the model depending on the search type
            switch(searchType){
               case "people-manager": this.employeeBasicInformationModel.peopleManager.id = this.searchDialogEmployeeId; break;
               case "delivery-manager": this.employeeProjectModel.deliveryManager.id = this.searchDialogEmployeeId; break;
               case "team-lead": this.employeeProjectModel.teamLead.id = this.searchDialogEmployeeId; break;
               case "backup" : this.employeeProjectModel.employeeBackup.id = this.searchDialogEmployeeId; break;
            }
        });
    }

    /**
     * Update basic information
     * @param employeeId : string
     */
    private _updateEmployeeBasicInformation(employeeId : string){
         if(!this.employeeBasicInformationFormSubmitted){
             this.employeeBasicInformationFormSubmitted = true;

             this.employeeBasicInformationModel.updatedBy = new Employee();
             this.employeeBasicInformationModel.updatedBy = this._appGlobal.userSession.user;
             this.employeeBasicInformationModel.dateUpdated = Date.now();

             this._employeeBasicInformationService.update(employeeId,  this.employeeBasicInformationModel).subscribe(
                   response => {
                                   //this._toasterService.pop('info', '', 'Your basic information has been saved successfully.');
                                   this._updateEmployeeContact(employeeId, this.employeeContactDetailsModel.id);
                                   this.employeeBasicInformationFormSubmitted = false;
                                }, (err: HttpErrorResponse) => {
                                   this._toasterService.pop('error', '', err.status + ' Error in saving employee basic information. (' + err.statusText + ')');
                                   if (err.error instanceof Error) {
                                     console.log("Client-side error occured.");
                                   } else {
                                     console.log("Server-side error occured.");
                                   }
                                }
                             );
         }
    }

    /**
     * Update contact
     * @param employeeId : string
     * @param employeeContactId : string
     */
    private _updateEmployeeContact(employeeId : string, employeeContactId : string){
      if(!this.employeeContactDetailsFormSubmitted){
          this.employeeContactDetailsFormSubmitted = true;
          this.employeeContactDetailsModel.updatedBy = this._appGlobal.userSession.user;
          this.employeeContactDetailsModel.dateUpdated = Date.now();

          this._employeeContactDetailsService.update(employeeId, employeeContactId, this.employeeContactDetailsModel).subscribe(
                            response => {
                               //this._toasterService.pop('info', '', 'Your contact information has been saved successfully.');
                               this._updateEmployeeProject(employeeId,this.employeeProjectModel.id);
                               this.employeeContactDetailsFormSubmitted = false;
                            },
                            (err: HttpErrorResponse) => {
                              this._toasterService.pop('error', '', err.status + ' Error in saving contact details. (' + err.statusText + ')');
                              if (err.error instanceof Error) {
                                console.log("Client-side error occured.");
                              } else {
                                console.log("Server-side error occured.");
                              }
                            }
          );
      }
    }

    /**
     * Update project
     * @param employeeId : string
     * @param employeeProjectId : string
     */
    private _updateEmployeeProject(employeeId : string, employeeProjectId : string){
        if(!this.employeeProjectFormSubmitted){
                this.employeeProjectFormSubmitted = true;
                // Transfer to temporary payload to skip null or empty properties
                let payLoad : EmployeeProject = new EmployeeProject();

                payLoad.id = this.employeeProjectModel.id;
                payLoad.employee = this.employeeProjectModel.employee;
                payLoad.teamName = this.employeeProjectModel.teamName;
                payLoad.teamLead = this.employeeProjectModel.teamLead;
                payLoad.deliveryManager = this.employeeProjectModel.deliveryManager;
                payLoad.priority = this.employeeProjectModel.priority;
                payLoad.shift = this.employeeProjectModel.shift;
                payLoad.projectEmailAddress =  this.employeeProjectModel.projectEmailAddress;
                payLoad.onCallSchedule = (this.employeeProjectModel.onCallSchedule != '') ? this.employeeProjectModel.onCallSchedule : 'NO';
                payLoad.totalOnCallHours = (this.employeeProjectModel.onCallSchedule != '' && this.employeeProjectModel.onCallSchedule != 'NO') ? this.employeeProjectModel.totalOnCallHours : '0';
                payLoad.bcpRole = this.employeeProjectModel.bcpRole;

                payLoad.capped = this.employeeProjectModel.capped;

                if(payLoad.capped != 'NO'){
                     payLoad.cappedHours = this.employeeProjectModel.cappedHours;
                }

                if(payLoad.bcpRole == 'BCP_CRITICAL'){
                    payLoad.criticalServiceSupported = this.employeeProjectModel.criticalServiceSupported;
                    if(this.employeeProjectModel.employeeBackup.id != "") {
                        payLoad.employeeBackup  = this.employeeProjectModel.employeeBackup;
                    }
                }

                payLoad.updatedBy = this._appGlobal.userSession.user;
                payLoad.dateUpdated = Date.now();

                this._employeeProjectService.update(employeeId, employeeProjectId,  payLoad).subscribe(
                                                  response => {
                                                     //this._toasterService.pop('info', '', 'Your team information has been saved successfully.');
                                                      this._updateEmployeeReimbursements(employeeId,this.employeeReimbursementsModel.id);
                                                      this.employeeProjectFormSubmitted = false;
                                                  },
                                                  (err: HttpErrorResponse) => {
                                                    this._toasterService.pop('error', '', err.status + ' Error in saving team information. (' + err.statusText + ')');
                                                    if (err.error instanceof Error) {
                                                      console.log("Client-side error occured.");
                                                    } else {
                                                      console.log("Server-side error occured.");
                                                    }
                                                  }
                                );
        }
    }

    /**
     * Update reimbursement
     * @param employeeId : string
     * @param employeeReimbursementId : string
     */
    private _updateEmployeeReimbursements(employeeId : string, employeeReimbursementId : string){
        if(!this.employeeReimbursementsFormSubmitted){
            this.employeeReimbursementsFormSubmitted = true;
            this.employeeReimbursementsModel.updatedBy = this._appGlobal.userSession.user;
            this.employeeReimbursementsModel.dateUpdated = Date.now();
            //this.employeeReimbursementsModel.employeeProject = new EmployeeProject();
            //this.employeeReimbursementsModel.employeeProject.id = this.employeeReimbursementsModel.employeeProjectId;

            this._employeeReimbursementService.update(employeeId, employeeReimbursementId,  this.employeeReimbursementsModel).subscribe(
                                              response => {
                                                this._toasterService.pop('info', '', 'Your profile has been saved successfully.');
                                                this.employeeReimbursementsFormSubmitted = false;
                                              },
                                              (err: HttpErrorResponse) => {
                                                this._toasterService.pop('error', '', err.status + ' Error in saving reimbursement information. (' + err.statusText + ')');
                                                if (err.error instanceof Error) {
                                                  console.log("Client-side error occured.");
                                                } else {
                                                  console.log("Server-side error occured.");
                                                }
                                              }
                            );
        }
    }

    /**
     * Get basic information
     * @param employeeId : string
     */
    private _getEmployeeBasicInformation(employeeId : string){
         employeeId = employeeId || '';
         this._employeeBasicInformationService.getBasicInformation(employeeId).subscribe(
                   response => {
                     this.employeeBasicInformationModel = response;

                     if(this.employeeBasicInformationModel.peopleManager == undefined
                        || this.employeeBasicInformationModel.peopleManager == null){
                          this.employeeBasicInformationModel.peopleManager = new Employee();
                     }
                   },
                   (err: HttpErrorResponse) => {
                     this._toasterService.pop('error', '', err.status + ' Error in retrieving employee basic information. (' + err.statusText + ')');
                     if (err.error instanceof Error) {
                       console.log("Client-side error occured.");
                     } else {
                       console.log("Server-side error occured.");
                     }
                   }
             );
    }

    /**
     * Get contact details
     * @param employeeId : string
     */
    private _getEmployeeContactDetails(employeeId : string){
         employeeId = employeeId || '';

         this._employeeContactDetailsService.getContactsByPriority(employeeId, "1").subscribe(
                   response => {
                     this.employeeContactDetailsModel = response;

                     if(response.homeEmployeeAddress == null){
                         this.employeeContactDetailsModel.homeEmployeeAddress = new Address();
                     }

                     this.employeeContactDetailsModel.homeEmployeeAddress.employee = new Employee();
                     this.employeeContactDetailsModel.homeEmployeeAddress.employee.id = employeeId;


                     if(response.businessEmployeeAddress == null){
                          this.employeeContactDetailsModel.businessEmployeeAddress = new Address();
                     }

                     this.employeeContactDetailsModel.businessEmployeeAddress.employee = new Employee();
                     this.employeeContactDetailsModel.businessEmployeeAddress.employee.id = employeeId;
                   },
                   (err: HttpErrorResponse) => {
                     this._toasterService.pop('error', '', err.status + ' Error in retrieving contact details. (' + err.statusText + ')');
                     if (err.error instanceof Error) {
                       console.log("Client-side error occured.");
                     } else {
                       console.log("Server-side error occured.");
                     }
                   }
             );
    }

    /**
     * Get project
     * @param employeeId : string
     */
    private _getEmployeeProject(employeeId : string){
     employeeId = employeeId || '';
     this._employeeProjectService.getEmployeeProjectByPriority(employeeId, "1").subscribe(
               response => {
                   this.employeeProjectModel = response;

                   if(this.employeeProjectModel.deliveryManager == undefined
                     ||this.employeeProjectModel.deliveryManager == null){
                      this.employeeProjectModel.deliveryManager = new Employee();
                   }

                   if(this.employeeProjectModel.teamLead == undefined
                    ||this.employeeProjectModel.teamLead == null){
                     this.employeeProjectModel.teamLead = new Employee();
                   }

                   if(this.employeeProjectModel.employee == undefined
                       ||this.employeeProjectModel.employee == null){
                        this.employeeProjectModel.employee = new Employee();
                        this.employeeProjectModel.employee.id = employeeId;
                   }

                   if(this.employeeProjectModel.employeeBackup == undefined
                     ||this.employeeProjectModel.employeeBackup == null){
                      this.employeeProjectModel.employeeBackup = new Employee();
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
     * Get reimbursement
     * @param employeeId : string
     */
    private _getEmployeeReimbursements(employeeId : string){
         employeeId = employeeId || '';
         this._employeeReimbursementService.getEmployeeReimbursementsByProjectAndPriority(employeeId, "1").subscribe(
                   response => {
                       this.employeeReimbursementsModel = response;

                       if(this.employeeReimbursementsModel.employee == undefined
                           ||this.employeeReimbursementsModel.employee == null){
                            this.employeeReimbursementsModel.employee = new Employee();
                            this.employeeReimbursementsModel.employee.id = employeeId;
                       }
                   },
                   (err: HttpErrorResponse) => {
                     this._toasterService.pop('error', '', err.status + ' Error in retrieving reimbursement information. (' + err.statusText + ')');
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
          urlParams += '&size=5000';
      this._employeeBasicInformationService.getPeopleManagers(urlParams).subscribe(
                response => {
                  this.peopleManagers = response.results;
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
     * Get delivery managers
     * @param searchName : string
     */
    private _getDeliveryManagers(searchName : string){
      searchName = searchName || '';

      //Build ur parameters
      let urlParams = 'search=' + searchName;
          urlParams += '&size=5000';
      this._employeeBasicInformationService.getDeliveryManagers(urlParams).subscribe(
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
     * Get team leads
     * @param searchName : string
     */
    private _getTeamLeads(searchName : string){
          searchName = searchName || '';

          //Build ur parameters
          let urlParams = 'search=' + searchName;
              urlParams += '&size=5000';
          this._employeeBasicInformationService.getTeamLeads(urlParams).subscribe(
                    response => {
                      this.teamleads = response.results;
                    },
                    (err: HttpErrorResponse) => {
                      this._toasterService.pop('error', '', err.status + ' Error in retrieving the list of team leads. (' + err.statusText + ')');
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
}

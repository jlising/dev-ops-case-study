import { environment } from '../../../environments/environment';
import { Component, OnInit, ViewEncapsulation, Inject } from '@angular/core';
import { ToasterService } from 'angular2-toaster';
import { HttpErrorResponse } from '@angular/common/http';

import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material';

import { ActionItemsComponent } from './action-items.component';
import { ActionItem } from './action-item';
import { ActionItemsService } from './action-items.service';
import { Employee } from '../common/employee';
import { AppGlobal } from '../../app.global';

@Component({
  selector: 'app-action-item-dialog',
  templateUrl: './action-item-dialog.component.html',
  styleUrls: ['./action-item-dialog.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class ActionItemDialogComponent implements OnInit {
   public actionItemDialogModel : ActionItem = new ActionItem();
   public formSubmitted : boolean = false;

    // Inject private classes via constructor
    constructor( public _appGlobal : AppGlobal,
                 private _actionItemsService : ActionItemsService,
                 private _toasterService : ToasterService,
                 public dialogRef: MatDialogRef<ActionItemsComponent>, @Inject(MAT_DIALOG_DATA) public data: any) {}

    // Apply definition since we implemented OnInit
    ngOnInit() {
        switch(this.data.action){
            case "add" :
            this.actionItemDialogModel.required = true;
            break;

            default:
            this._getActionItem(this.data.id);
        }
    }
      public saveActionItem(action : string){
        switch(action){
            case "add": this._addActionItem();break;
            case "edit" : this._updateActionItem(this.data.id); break;
        }
      }
      /**
       * Add action item
       */
      private _addActionItem(){
            if(!this.formSubmitted){
                this.formSubmitted = true;
                this._actionItemsService.save(this.actionItemDialogModel).subscribe(
                          response => {
                             this._toasterService.pop('info', '', 'Action item has been added successfully.');
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

      /**
       * Update action item
       * @param actionItemId : string
       */
      private _updateActionItem(actionItemId : string){
          if(!this.formSubmitted){
               this.formSubmitted = true;

               this.actionItemDialogModel.updatedBy = new Employee();
               this.actionItemDialogModel.updatedBy = this._appGlobal.userSession.user;
               this.actionItemDialogModel.dateUpdated = Date.now();

               this._actionItemsService.update(actionItemId,  this.actionItemDialogModel).subscribe(
                     response => {
                                     this._toasterService.pop('info', '', 'The action item has been updated successfully.');
                                     this.dialogRef.close(true);
                                     this.formSubmitted = false;

                                  }, (err: HttpErrorResponse) => {
                                     this._toasterService.pop('error', '', err.status + ' Error in saving action item. (' + err.statusText + ')');
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
}

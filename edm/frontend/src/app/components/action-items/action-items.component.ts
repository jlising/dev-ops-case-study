import { environment } from '../../../environments/environment';
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { ToasterService } from 'angular2-toaster';
import { MatDialog, MatDialogRef } from '@angular/material'

import { AppGlobal } from '../../app.global';

import { ActionItem } from'./action-item';
import { ActionItemsService } from'./action-items.service';
import { ActionItemDialogComponent } from './action-item-dialog.component';
import { AssignActionItemDialogComponent } from './assign-action-item-dialog.component';

@Component({
  selector: 'app-action-items',
  templateUrl: './action-items.component.html',
  styleUrls: ['./action-items.component.css']
})
export class ActionItemsComponent implements OnInit {

   public actionItems : ActionItem[];
   public totalRecords : number;

   public pageParams = {
   						page : environment.pagingOptions.start,
   						size : environment.pagingOptions.size,
   						order : environment.pagingOptions.sortOrder,
   						sort :  environment.pagingOptions.sort,
   						searchString : '',
   						name : ""
   		};

   constructor(public _appGlobal : AppGlobal,
                private _actionItemsService : ActionItemsService,
                private _toasterService : ToasterService,
                public _dialog: MatDialog) { }

   //Apply definition since we implemented OnInit
   ngOnInit() {
       // View list of action items by default
       this._getActionItems(this.pageParams.searchString);
   }

   /**
   	 * Search
  */
   public search(){
       this.pageParams.page = 1;
       this._getActionItems(this.pageParams.searchString);
   }

  /**
   * Refresh without changing the pagination
   */
  public refresh(){
      this._getActionItems(this.pageParams.searchString);
  }

 /**
  * Reset search
  */
   public resetSearch(){
       this.pageParams.searchString = '';
       this.search();
   }

   /**
   	 * Page changed
   	 * @param event : any
   	 */
   public pageChanged(event : any):void {
    this.pageParams.page = event.page;
    this._getActionItems(this.pageParams.searchString);
   }

   /**
    * Open assign action item window
    * @param id : string
    */
   public openAssignActionItemDialog(id : string){
        // Open the dialog and pass the data
       let dialogRef = this._dialog.open(AssignActionItemDialogComponent, {
         height: '540px',
         width: '800px',
         maxHeight: '540px',
         minHeight: '540px',
         minWidth:'800px',
         maxWidth: '800px',
         data: {id : id}
       });

       dialogRef.afterClosed().subscribe(result => {

       });
   }

   /**
    * Open new action item
    * @param id : string
    * @param action : string
    */
   public openActionItemDialog(id : string, action :  string): void {

       // Open the dialog and pass the data
       let dialogRef = this._dialog.open(ActionItemDialogComponent, {
         height: '555px',
         width: '800px',
         maxHeight: '555px',
         minHeight: '555px',
         minWidth:'800px',
         maxWidth: '800px',
         data: {id : id, action : action}
       });

       dialogRef.afterClosed().subscribe(result => {
           if(result != undefined){
               if(result){
                   switch(action){
                    case "add" : this.search(); break;
                    case "edit" : this.refresh(); break;
                   }

                   this.pageParams.name = "";
               }
           }
       });
   }

   /**
    * Get the list of action items
    * @param searchName : string
    */
   private _getActionItems(searchName : string){
       searchName = searchName || '';

       //Build ur parameters
       let urlParams = 'search=' + searchName;
       urlParams += '&page=' + this.pageParams.page;
       urlParams += '&size=' + this.pageParams.size;
       urlParams += '&sort=' + this.pageParams.sort;
       urlParams += '&order=' + this.pageParams.order;

       let offset =  (this.pageParams.page - 1) > 0 ? ((this.pageParams.page * this.pageParams.size) - this.pageParams.size) + 1 : this.pageParams.page;

       this._actionItemsService.getList(urlParams).subscribe(
                 response => {
                    if(response.results != undefined){
                       //Just add a counter field
                       response.results.forEach(function(row, key){
                           row.counter = key + offset;
                       });
                    }
                   this.actionItems = response.results;
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
     * Delete action item
     * @param actionItemId : string
     */
    public deleteActionItem(actionItemId : string){
            this._actionItemsService.delete(actionItemId).subscribe(
               response => {
                       this._toasterService.pop('info', '', 'The action item has been deleted successfully.');

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

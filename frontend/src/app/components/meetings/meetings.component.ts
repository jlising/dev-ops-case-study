import { environment } from '../../../environments/environment';
import { Component, OnInit, ViewEncapsulation } from '@angular/core';

import { ToasterService } from 'angular2-toaster';
import { AppGlobal } from '../../app.global';

@Component({
  selector: 'app-meetings',
  templateUrl: './meetings.component.html',
  styleUrls: ['./meetings.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class MeetingsComponent implements OnInit {
  public meetings;
  public totalRecords : number;

  public pageParams = {
  						page : environment.pagingOptions.start,
  						size : environment.pagingOptions.size,
  						order : environment.pagingOptions.sortOrder,
  						sort :  environment.pagingOptions.sort,
  						searchString : ''
  		};
  constructor(public _appGlobal : AppGlobal, private _toasterService : ToasterService) { }

  ngOnInit() {
  }

  /**
  	 * Search
 */
  public search(){
      this.pageParams.page = 1;
      //this._getEmployees(this.pageParams.searchString);
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
  		//this._getEmployees(this.pageParams.searchString);
      }

}

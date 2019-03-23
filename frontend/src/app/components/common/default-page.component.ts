/**
 * Default page component
 * @author : Jesus Lising <jess.lising@gmail.com>
 */
import { environment } from '../../../environments/environment';
import {Component, OnInit, ViewEncapsulation } from '@angular/core';
import { Router } from '@angular/router';
import { AppGlobal } from '../../app.global';

@Component({
	selector : 'default-page',
	templateUrl :'./default-page.component.html',
	encapsulation: ViewEncapsulation.None
})
export class DefaultPageComponent   implements OnInit {

   // Inject private classes via constructor
   constructor( public _appGlobal : AppGlobal, private _router : Router ){}

   //Apply definition since we implemented OnInit
   ngOnInit() {
        if(this._appGlobal.userSession == null || this._appGlobal.userSession.user == undefined || this._appGlobal.userSession.user == null){
             this._router.navigate(['/login']);
        }else{
            this._router.navigate(['/profile']);
        }
   }

   /**
    * Redirect to login
    */
   w3Login(){
        this._router.navigate(['/login']);
   }
}

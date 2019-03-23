import { environment } from '../environments/environment';
import { Injectable } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';

import { AuthService } from './components/auth/auth.service';
import { Router } from '@angular/router';
import { ToasterService } from 'angular2-toaster';

@Injectable()
export class AppGlobal {
    public userSession : any = {};
    public appTitle : string = environment.appTitle;
    public appVersion : string = environment.appVersion;
    public copyrightYear : string = environment.copyrightYear;
    // Inject private classes via constructor
    constructor(private _authService : AuthService, private _router : Router, private _toasterService : ToasterService){
        //Initialize userSession. This code is needed to persists the data in the session. You can also do this in Auth guard!
        this.userSession = JSON.parse(localStorage.getItem("userSession"));
    }

     /**
      * Terminate login session
      */
     public logout(){
         this._authService.logout({}).subscribe(
                                response => {
                                    //On success, redirect to accounts page
                                    localStorage.removeItem("userSession");
                                    this.userSession = {};
                                    this._router.navigate(['/login']);
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

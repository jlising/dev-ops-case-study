import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { ToasterService } from 'angular2-toaster';

import { AppGlobal } from '../../app.global';
import { AuthService } from './auth.service';

@Component({
    selector: 'app-auth',
    templateUrl : './auth.component.html',
    encapsulation: ViewEncapsulation.None
})

export class AuthComponent  implements OnInit {
    public pageParams = {
         loginForm : {username : '', password : ''},
         formSubmitted : false
    };

    // Inject private classes via constructor
    constructor ( public _appGlobal : AppGlobal, private _authService : AuthService,  private _router : Router, private _toasterService : ToasterService ){}

    //Apply definition since we implemented OnInit
    ngOnInit() {
        this._appGlobal.userSession = {};
        localStorage.removeItem("userSession");
    }

    /**
     * Login event attached to the login button. In globals in case we put login anywhere else.
     * @param username : String
     * @param password : String
     */
     public login(username : String, password : String){
      if(!this.pageParams.formSubmitted){
           this.pageParams.formSubmitted = true;
               this._authService.login({username : username,
                                        password : password})
                           .subscribe(
                                response => {
                                    // On success, store login session then redirect to accounts page
                                    // Consolidate access roles
                                    var accessRoles = [];

                                    response.accessRoles.forEach(role => {
                                      accessRoles.push(role.name);
                                    });

                                    delete response.accessRoles;
                                    delete response.password;

                                    var sessionObject = { isAuthenticated : true, accessRoles : accessRoles, user : response };
                                    this._appGlobal.userSession = sessionObject;

                                    //Put in local storage to persists
                                    localStorage.setItem("userSession",JSON.stringify(sessionObject));

                                    this._router.navigate(['/profile']);
                                },
                                (err : HttpErrorResponse) => {
                                  this.pageParams.formSubmitted = false;

                                  if(err.status == 401){
                                    this._toasterService.pop('error', '', 'Unable to login. Please enter valid email address and password.');
                                  }else{
                                    this._toasterService.pop('error', '', err.status + ' ' + err.statusText);
                                  }

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

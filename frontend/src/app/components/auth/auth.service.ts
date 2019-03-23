import { environment } from '../../../environments/environment';
import { Injectable } from '@angular/core';
import { Headers} from '@angular/http'; // Http in angular 2 returns observable while in angular 1 it returns promise.
import { HttpClient, HttpParams } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';

@Injectable()
export class AuthService {
    private headers = new Headers({'Content-Type': 'application/json'});
    private url = environment.apiEndpointUrl;  // URL to web api

    // Inject private classes via constructor
    constructor ( private _http: HttpClient ){}

    /**
     * Check session
     */
    ping() : Observable<any> {
        return this._http.get(this.url + "/auth");
    }

    /**
     * Login
     * @param body : any
     */
    login( body : any) : Observable<any> {
         return this._http.post(this.url + '/login', body);
    }

    /**
     * Logout
     */
     logout(body : any) : Observable < any >{
        return this._http.post(this.url + '/logout', body);
     }
}


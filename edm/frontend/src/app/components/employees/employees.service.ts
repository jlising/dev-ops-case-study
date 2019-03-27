import { environment } from '../../../environments/environment';
import { Injectable } from '@angular/core';
import { Headers, URLSearchParams } from '@angular/http'; //Http in angular 2 resturns observable while in angular 1 it returns promise.
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { Employee } from '../common/employee';

@Injectable()
export class EmployeesService {

    private headers = new Headers({'Content-Type': 'application/json'});
    private url = environment.apiEndpointUrl + '/employee';

    // Inject private classes via constructor
    constructor (private _http: HttpClient){}

    /**
     * Get the list of employees
     * @param searchString : string
     */
    getList(searchString : string) : Observable<any> {
        //let params: URLSearchParams = new URLSearchParams();
        searchString = searchString || '';

        return this._http.get(this.url + '?' + searchString);
    }

    /**
     * Get employee by id
     * @param id : string
     */
    getById(id : string) : Observable<any> {
        id = id || '';

        return this._http.get(this.url + "/" + id);
    }

    /**
     * Get the list of employees by delivery manager
     * @param id : string
     * @param searchString : string
     */
    getByDeliveryManager(id : string, searchString : string) : Observable<any> {
        //let params: URLSearchParams = new URLSearchParams();
        searchString = searchString || '';

        return this._http.get(this.url + '/delivery-manager/' + id + '?' + searchString);
    }

    /**
     * Get employee from w3
     * @param id : string
     */
    getByW3Email(email : string) : Observable<any> {
        email = email || '';
        const params = new HttpParams().set("email", email);

        return this._http.get(this.url + "/w3",{ params });
    }

    addFromW3(body : any) : Observable<any> {
        return this._http.post(this.url + "/w3", body);
    }
    /**
     * Update employee
     * @param id : string
     * @param body : any
     */
    update(id : string, body : any) : Observable<any> {

        return this._http.patch(this.url + "/" + id, body);
    }

    /**
     * Add new employee
     * @param body : any
     */
    save( body : any) : Observable<any> {
        return this._http.post(this.url, body);
    }

    /**
     * Delete employee
     * @param id : string
     */
    delete(id : string) : Observable<any> {
          return this._http.delete(this.url + "/" + id);
    }

    /**
     * Tag/Untag employee as lead
     * @param id : string
     * @param lead : boolean
     */
    tagAsTeamLead(id : string, tl : boolean){
        let body : any = { tl : tl };
        return this._http.patch(this.url + "/" + id + "/tag-as-team-lead", body);
    }

    /**
     * Tag/Untag employee as people manager
     * @param id : string
     * @param manager : boolean
     */
    tagAsPeopleManager(id : string, pem : boolean){
        let body : any = { pem : pem };
        return this._http.patch(this.url + "/" + id + "/tag-as-people-manager", body);
    }

    /**
     * Tag/Untag employee as delivery manager
     * @param id : string
     * @param manager : boolean
     */
    tagAsDeliveryManager(id : string, dm : boolean){
        let body : any = { dm : dm };
        return this._http.patch(this.url + "/" + id + "/tag-as-delivery-manager", body);
    }

    getDeliveryManagers(searchString : string) : Observable<any> {
             searchString = searchString || '';

             return this._http.get(this.url + '/delivery-managers?' + searchString);
      }
}

import { environment } from '../../../environments/environment';
import { Injectable } from '@angular/core';
import { Headers, URLSearchParams } from '@angular/http'; //Http in angular 2 resturns observable while in angular 1 it returns promise.
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { ActionItem } from './action-item';

@Injectable()
export class ActionItemsService {

    private headers = new Headers({'Content-Type': 'application/json'});
    private url = environment.apiEndpointUrl + '/action-item';

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
     * Update action item
     * @param id : string
     * @param body : any
     */
    update(id : string, body : any) : Observable<any> {

        return this._http.patch(this.url + "/" + id, body);
    }

    /**
     * Add new action item
     * @param body : any
     */
    save( body : any) : Observable<any> {
        return this._http.post(this.url, body);
    }

    /**
     * Delete action item
     * @param id : string
     */
    delete(id : string) : Observable<any> {
          return this._http.delete(this.url + "/" + id);
    }

    /**
     * Get action item by id
     * @param id : string
     */
    getById(id : string) : Observable<any> {
        id = id || '';

        return this._http.get(this.url + "/" + id);
    }

    assign(id : string, body : any) : Observable<any> {
        return this._http.post(this.url + "/" + id + '/assign', body);
    }

    getAssigned(id : string) : Observable<any> {

        return this._http.get(this.url + '/' + id + '/assigned');
    }
}

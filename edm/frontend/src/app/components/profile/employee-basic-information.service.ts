import { environment } from '../../../environments/environment';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class EmployeeBasicInformationService {
  private url = environment.apiEndpointUrl + '/employee';

  // Inject private classes via constructor
  constructor (private _http: HttpClient){}

  getBasicInformation(employeeId : string) : Observable<any> {
      return this._http.get(this.url + "/" + employeeId);
  }

  getPeopleManagers(searchString : string) : Observable<any> {
       searchString = searchString || '';

       return this._http.get(this.url + '/people-managers?' + searchString);
  }

  getDeliveryManagers(searchString : string) : Observable<any> {
         searchString = searchString || '';

         return this._http.get(this.url + '/delivery-managers?' + searchString);
  }

  getTeamLeads(searchString : string) : Observable<any> {
           searchString = searchString || '';

           return this._http.get(this.url + '/team-leads?' + searchString);
  }

  getEmployees(searchString : string) : Observable<any> {
             searchString = searchString || '';

             return this._http.get(this.url + '?' + searchString);
  }

  update(employeeId : string, body : any ) : Observable <any> {
       return this._http.patch(this.url + '/' + employeeId, body);
  }
}

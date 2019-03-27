import { environment } from '../../../environments/environment';
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class EmployeeContactDetailsService {
  private url = environment.apiEndpointUrl + '/employee';

  // Inject private classes via constructor
  constructor (private _http: HttpClient){}

  getBasicInformation(employeeId : string) : Observable<any> {
      return this._http.get(this.url + "/" + employeeId);
  }

  getContactsByPriority(employeeId : string, priority : string) : Observable<any> {
          const params = new HttpParams().set("priority", priority);

          return this._http.get(this.url + "/" + employeeId + "/contact/priority", { params });
  }

  update( employeeId : string, employeeContactId : string, body : any ) : Observable <any> {
      return this._http.patch(this.url + '/' + employeeId + "/contact/" + employeeContactId, body);
  }
}

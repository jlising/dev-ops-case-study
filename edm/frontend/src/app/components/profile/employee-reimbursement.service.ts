import { environment } from '../../../environments/environment';
import { Injectable } from '@angular/core';
import { Headers } from '@angular/http';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class EmployeeReimbursementService {

   private url = environment.apiEndpointUrl + '/employee';

    // Inject private classes via constructor
    constructor (private _http: HttpClient){}

    getEmployeeReimbursementsByProjectAndPriority(employeeId : string, priority : string) : Observable<any> {
            const params = new HttpParams().set("priority", priority);
            return this._http.get(this.url + "/" + employeeId + "/project/priority/reimbursement", { params });
    }

    update( employeeId: String, employeeReimbursementId : string, body : any ) : Observable <any> {

        return this._http.patch(this.url + '/' + employeeId + "/reimbursement/" + employeeReimbursementId, body);
    }
}

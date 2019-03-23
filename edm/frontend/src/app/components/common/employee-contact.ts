import { Employee } from './employee';
import { Address } from './address';

export class EmployeeContact {
    public id : string;
    public employee : Employee;
    public priority : number;
    public emailAddress : string;

    public homeEmployeeAddress : Address;
    public businessEmployeeAddress : Address;

    public mobileNumber : string;
    public homePhoneNumber : string;

    public dateAdded : number;
    public dateUpdated : number;
    public addedBy : Employee;
    public updatedBy : Employee;

    constructor(){}
}

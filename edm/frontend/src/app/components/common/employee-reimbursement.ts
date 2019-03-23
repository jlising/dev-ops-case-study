import { Employee } from './employee';
import { EmployeeProject } from './employee-project';
export class EmployeeReimbursement {

     public id : string;
     public employee : Employee;

     public broadBand : string;
     public mobileData : string;
     public mobileDevice : string;

     public employeeProject : EmployeeProject;

     public dateAdded : number;
     public dateUpdated : number;
     public addedBy : Employee;
     public updatedBy : Employee;

     constructor(){}
}

import { Employee } from '../common/employee';
import { EmployeeProject } from '../common/employee-project';

export class EmployeeReimbursement {
     public id : string;
     public employee : Employee;

     public broadBand : boolean;
     public mobileData : boolean;
     public mobileDevice : boolean;

     public employeeProject : EmployeeProject;

     public dateAdded : number;
     public dateUpdated : number;
     public addedBy : Employee;
     public updatedBy : Employee;

     constructor(){}
}

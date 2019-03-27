import { EmployeeContact } from './employee-contact';
import { EmployeeProject } from './employee-project';
import { EmployeeReimbursement } from './employee-reimbursement'
export class Employee {
    public id : string;
    public lname : string;
    public mname : string;
    public fname : string;
    public suffix : string;

    public email : string;
    public serial : string;
    public position : string;

    public ibmUID : string;

    public primarySkillSet : string;
    public secondarySkillSet : string;
    public jrss : string;

    public peopleManager : Employee = <Employee>{};
    public tl : boolean = false;
    public dm : boolean = false;
    public pem : boolean = false;

    public employeeContacts : EmployeeContact[] = [];
    public employeeProjects : EmployeeProject[] = [];
    public employeeReimbursements : EmployeeReimbursement[] = [];
    public dateAdded : number;
    public dateUpdated : number;
    public addedBy : Employee;
    public updatedBy : Employee;

    constructor(){}
}

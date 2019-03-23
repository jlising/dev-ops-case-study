import { Employee } from './employee';

export class EmployeeProject {
    public id : string;
    public employee : Employee;

    public teamName : string;
    public teamLead : Employee;
    public deliveryManager : Employee;
    public priority : number;

    public shift : string;
    public onCallSchedule : string;
    public totalOnCallHours : string;

    public projectEmailAddress : string;
    public capped : string;
    public cappedHours : string;

    public bcpRole : string;
    public criticalServiceSupported : string;
    public employeeBackup : Employee;

    public dateAdded : number;
    public dateUpdated : number;
    public addedBy : Employee;
    public updatedBy : Employee;

    constructor(){}
}

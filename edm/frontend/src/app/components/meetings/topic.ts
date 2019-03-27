import { Employee } from '../common/employee';
export class Topic {
    public id : string;

    public type : string;
    public name : string;
    public notes : string;
    public owner : Employee;

    public assignments : Employee[];

    public status : string;
    public dueDate : string;
}

import { Employee } from '../common/employee';

export class ActionItem {
    public id : string;
    public name : string;
    public description : string;
    public references : string;
    public required : boolean;
    public dateDue : Date;

    public dateAdded : number;
    public addedBy : Employee;

    public dateUpdated : number;
    public updatedBy : Employee;
}

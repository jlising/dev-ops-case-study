import { Employee } from '../common/employee';
import { Topic } from './topic';

export class Meeting {
    public id : string;
    public subject : string;
    public location : string;
    public start : string;
    public end : string;
    public facilitator : Employee;

    public attendees : Employee[];

    public description : string;

    public topics : Topic[];
}

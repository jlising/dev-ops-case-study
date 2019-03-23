import { Employee } from './employee';

export class Address {
    public id : string;
    public address1 : string;
    public address2 : string;

    public city : string;
    public country : string;
    public state : string;
    public zip : string;

    public employee : Employee;

    constructor(){}
}

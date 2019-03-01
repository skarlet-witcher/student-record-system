import { IDepartment } from 'app/shared/model/svcModule/department.model';

export interface IFaculty {
    id?: number;
    name?: string;
    departments?: IDepartment[];
}

export class Faculty implements IFaculty {
    constructor(public id?: number, public name?: string, public departments?: IDepartment[]) {}
}

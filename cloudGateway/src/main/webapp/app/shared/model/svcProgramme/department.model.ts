import { IProgramme } from 'app/shared/model/svcProgramme/programme.model';

export interface IDepartment {
    id?: number;
    name?: string;
    programmes?: IProgramme[];
    facultyName?: string;
    facultyId?: number;
}

export class Department implements IDepartment {
    constructor(
        public id?: number,
        public name?: string,
        public programmes?: IProgramme[],
        public facultyName?: string,
        public facultyId?: number
    ) {}
}

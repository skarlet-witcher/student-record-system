export const enum Degree {
    BACHELOR = 'BACHELOR',
    MASTER = 'MASTER',
    DOCTOR = 'DOCTOR'
}

export const enum EnrollStatus {
    TAKING = 'TAKING',
    SUSPEND = 'SUSPEND',
    DONE = 'DONE'
}

export interface IStudentEnroll {
    id?: number;
    enrollYear?: number;
    forProgrammeId?: number;
    forDegree?: Degree;
    status?: EnrollStatus;
    studentStudentNumber?: string;
    studentId?: number;
}

export class StudentEnroll implements IStudentEnroll {
    constructor(
        public id?: number,
        public enrollYear?: number,
        public forProgrammeId?: number,
        public forDegree?: Degree,
        public status?: EnrollStatus,
        public studentStudentNumber?: string,
        public studentId?: number
    ) {}
}

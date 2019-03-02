export const enum ProgressType {
    SEMESTER = 'SEMESTER',
    YEAR = 'YEAR',
    PART = 'PART'
}

export const enum ProgressDecision {
    PASS = 'PASS',
    SUSPENSION = 'SUSPENSION',
    FAIL_CAN_REPEAT = 'FAIL_CAN_REPEAT',
    FAIL_NO_REPEAT = 'FAIL_NO_REPEAT'
}

export interface IStudentProgression {
    id?: number;
    forAcademicYear?: number;
    forAcademicSemester?: number;
    forPartNo?: number;
    qca?: number;
    progressType?: ProgressType;
    progressDecision?: ProgressDecision;
    studentStudentNumber?: string;
    studentId?: number;
}

export class StudentProgression implements IStudentProgression {
    constructor(
        public id?: number,
        public forAcademicYear?: number,
        public forAcademicSemester?: number,
        public forPartNo?: number,
        public qca?: number,
        public progressType?: ProgressType,
        public progressDecision?: ProgressDecision,
        public studentStudentNumber?: string,
        public studentId?: number
    ) {}
}

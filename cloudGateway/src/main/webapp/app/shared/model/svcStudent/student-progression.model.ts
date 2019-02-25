export const enum ProgressDecision {
    PASS = 'PASS',
    SUSPENSION = 'SUSPENSION',
    FAIL_CAN_REPEAT = 'FAIL_CAN_REPEAT',
    FAIL_NO_REPEAT = 'FAIL_NO_REPEAT'
}

export interface IStudentProgression {
    id?: number;
    yearNo?: number;
    semesterNo?: number;
    qca?: number;
    cumulativeQcaForPart?: number;
    progressDecision?: ProgressDecision;
    studentStudentNumber?: string;
    studentId?: number;
}

export class StudentProgression implements IStudentProgression {
    constructor(
        public id?: number,
        public yearNo?: number,
        public semesterNo?: number,
        public qca?: number,
        public cumulativeQcaForPart?: number,
        public progressDecision?: ProgressDecision,
        public studentStudentNumber?: string,
        public studentId?: number
    ) {}
}

export interface IStudentModule {
    id?: number;
    studentId?: number;
    moduleId?: number;
    enrollYear?: number;
    enrollSemester?: number;
}

export class StudentModule implements IStudentModule {
    constructor(
        public id?: number,
        public studentId?: number,
        public moduleId?: number,
        public enrollYear?: number,
        public enrollSemester?: number
    ) {}
}

export interface IStudentModuleSelection {
    id?: number;
    studentId?: number;
    yearNo?: number;
    semesterNo?: number;
    creditHour?: number;
    marks?: number;
    qcs?: number;
    moduleName?: string;
    moduleId?: number;
    studentModuleGradeTypeName?: string;
    studentModuleGradeTypeId?: number;
}

export class StudentModuleSelection implements IStudentModuleSelection {
    constructor(
        public id?: number,
        public studentId?: number,
        public yearNo?: number,
        public semesterNo?: number,
        public creditHour?: number,
        public marks?: number,
        public qcs?: number,
        public moduleName?: string,
        public moduleId?: number,
        public studentModuleGradeTypeName?: string,
        public studentModuleGradeTypeId?: number
    ) {}
}

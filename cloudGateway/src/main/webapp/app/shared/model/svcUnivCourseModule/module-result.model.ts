export interface IModuleResult {
    id?: number;
    grade?: number;
    qca?: number;
    studentId?: number;
    moduleName?: string;
    moduleId?: number;
}

export class ModuleResult implements IModuleResult {
    constructor(
        public id?: number,
        public grade?: number,
        public qca?: number,
        public studentId?: number,
        public moduleName?: string,
        public moduleId?: number
    ) {}
}

export interface IModuleGrade {
    id?: number;
    name?: string;
    description?: string;
    lowMarks?: number;
    qpv?: number;
    isAffectQca?: boolean;
}

export class ModuleGrade implements IModuleGrade {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public lowMarks?: number,
        public qpv?: number,
        public isAffectQca?: boolean
    ) {
        this.isAffectQca = this.isAffectQca || false;
    }
}

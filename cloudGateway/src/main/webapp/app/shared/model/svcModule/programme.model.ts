export interface IProgramme {
    id?: number;
    code?: string;
    name?: string;
    length?: number;
    courseLeader?: string;
    degree?: string;
    departmentName?: string;
    departmentId?: number;
}

export class Programme implements IProgramme {
    constructor(
        public id?: number,
        public code?: string,
        public name?: string,
        public length?: number,
        public courseLeader?: string,
        public degree?: string,
        public departmentName?: string,
        public departmentId?: number
    ) {}
}

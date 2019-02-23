export const enum ProgrammePropType {
    GENERAL = 'GENERAL',
    YEAR = 'YEAR',
    SEMESTER = 'SEMESTER'
}

export interface IProgrammePropDict {
    id?: number;
    forEnrollYear?: number;
    type?: ProgrammePropType;
    forYearNo?: number;
    forSemesterNo?: number;
    key?: string;
    value?: string;
}

export class ProgrammePropDict implements IProgrammePropDict {
    constructor(
        public id?: number,
        public forEnrollYear?: number,
        public type?: ProgrammePropType,
        public forYearNo?: number,
        public forSemesterNo?: number,
        public key?: string,
        public value?: string
    ) {}
}

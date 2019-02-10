export const enum Gender {
    MALE = 'MALE',
    FEMALE = 'FEMALE',
    OTHER = 'OTHER'
}

export interface IStudent {
    id?: number;
    studentNumber?: string;
    firstName?: string;
    lastName?: string;
    gender?: Gender;
    email?: string;
    phone?: string;
    addressLine1?: string;
    addressLine2?: string;
    city?: string;
    country?: string;
    userId?: number;
}

export class Student implements IStudent {
    constructor(
        public id?: number,
        public studentNumber?: string,
        public firstName?: string,
        public lastName?: string,
        public gender?: Gender,
        public email?: string,
        public phone?: string,
        public addressLine1?: string,
        public addressLine2?: string,
        public city?: string,
        public country?: string,
        public userId?: number
    ) {}
}

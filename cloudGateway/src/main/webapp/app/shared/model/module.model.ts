export interface IModule {
    id?: number;
    code?: string;
    name?: string;
    desc?: any;
}

export class Module implements IModule {
    constructor(public id?: number, public code?: string, public name?: string, public desc?: any) {}
}

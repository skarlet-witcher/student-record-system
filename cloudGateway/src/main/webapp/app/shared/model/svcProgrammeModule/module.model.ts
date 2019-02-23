export interface IModule {
    id?: number;
    code?: string;
    name?: string;
    credit?: number;
}

export class Module implements IModule {
    constructor(public id?: number, public code?: string, public name?: string, public credit?: number) {}
}

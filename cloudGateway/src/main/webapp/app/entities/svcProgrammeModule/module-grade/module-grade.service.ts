import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IModuleGrade } from 'app/shared/model/svcProgrammeModule/module-grade.model';

type EntityResponseType = HttpResponse<IModuleGrade>;
type EntityArrayResponseType = HttpResponse<IModuleGrade[]>;

@Injectable({ providedIn: 'root' })
export class ModuleGradeService {
    public resourceUrl = SERVER_API_URL + 'svcprogrammemodule/api/module-grades';

    constructor(protected http: HttpClient) {}

    create(moduleGrade: IModuleGrade): Observable<EntityResponseType> {
        return this.http.post<IModuleGrade>(this.resourceUrl, moduleGrade, { observe: 'response' });
    }

    update(moduleGrade: IModuleGrade): Observable<EntityResponseType> {
        return this.http.put<IModuleGrade>(this.resourceUrl, moduleGrade, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IModuleGrade>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IModuleGrade[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}

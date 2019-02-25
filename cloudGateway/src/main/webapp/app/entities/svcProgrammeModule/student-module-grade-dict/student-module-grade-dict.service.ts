import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IStudentModuleGradeDict } from 'app/shared/model/svcProgrammeModule/student-module-grade-dict.model';

type EntityResponseType = HttpResponse<IStudentModuleGradeDict>;
type EntityArrayResponseType = HttpResponse<IStudentModuleGradeDict[]>;

@Injectable({ providedIn: 'root' })
export class StudentModuleGradeDictService {
    public resourceUrl = SERVER_API_URL + 'svcprogrammemodule/api/student-module-grade-dicts';

    constructor(protected http: HttpClient) {}

    create(studentModuleGradeDict: IStudentModuleGradeDict): Observable<EntityResponseType> {
        return this.http.post<IStudentModuleGradeDict>(this.resourceUrl, studentModuleGradeDict, { observe: 'response' });
    }

    update(studentModuleGradeDict: IStudentModuleGradeDict): Observable<EntityResponseType> {
        return this.http.put<IStudentModuleGradeDict>(this.resourceUrl, studentModuleGradeDict, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IStudentModuleGradeDict>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IStudentModuleGradeDict[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}

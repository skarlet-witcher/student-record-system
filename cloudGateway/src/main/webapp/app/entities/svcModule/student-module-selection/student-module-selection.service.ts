import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IStudentModuleSelection } from 'app/shared/model/svcModule/student-module-selection.model';

type EntityResponseType = HttpResponse<IStudentModuleSelection>;
type EntityArrayResponseType = HttpResponse<IStudentModuleSelection[]>;

@Injectable({ providedIn: 'root' })
export class StudentModuleSelectionService {
    public resourceUrl = SERVER_API_URL + 'svcmodule/api/student-module-selections';

    constructor(protected http: HttpClient) {}

    create(studentModuleSelection: IStudentModuleSelection): Observable<EntityResponseType> {
        return this.http.post<IStudentModuleSelection>(this.resourceUrl, studentModuleSelection, { observe: 'response' });
    }

    update(studentModuleSelection: IStudentModuleSelection): Observable<EntityResponseType> {
        return this.http.put<IStudentModuleSelection>(this.resourceUrl, studentModuleSelection, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IStudentModuleSelection>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IStudentModuleSelection[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}

import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IStudentModule } from 'app/shared/model/student-module.model';

type EntityResponseType = HttpResponse<IStudentModule>;
type EntityArrayResponseType = HttpResponse<IStudentModule[]>;

@Injectable({ providedIn: 'root' })
export class StudentModuleService {
    public resourceUrl = SERVER_API_URL + 'api/student-modules';

    constructor(protected http: HttpClient) {}

    create(studentModule: IStudentModule): Observable<EntityResponseType> {
        return this.http.post<IStudentModule>(this.resourceUrl, studentModule, { observe: 'response' });
    }

    update(studentModule: IStudentModule): Observable<EntityResponseType> {
        return this.http.put<IStudentModule>(this.resourceUrl, studentModule, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IStudentModule>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IStudentModule[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}

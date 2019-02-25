import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IStudentEnroll } from 'app/shared/model/svcStudent/student-enroll.model';

type EntityResponseType = HttpResponse<IStudentEnroll>;
type EntityArrayResponseType = HttpResponse<IStudentEnroll[]>;

@Injectable({ providedIn: 'root' })
export class StudentEnrollService {
    public resourceUrl = SERVER_API_URL + 'svcstudent/api/student-enrolls';

    constructor(protected http: HttpClient) {}

    create(studentEnroll: IStudentEnroll): Observable<EntityResponseType> {
        return this.http.post<IStudentEnroll>(this.resourceUrl, studentEnroll, { observe: 'response' });
    }

    update(studentEnroll: IStudentEnroll): Observable<EntityResponseType> {
        return this.http.put<IStudentEnroll>(this.resourceUrl, studentEnroll, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IStudentEnroll>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IStudentEnroll[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}

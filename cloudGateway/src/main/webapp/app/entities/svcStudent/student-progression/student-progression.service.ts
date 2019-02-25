import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IStudentProgression } from 'app/shared/model/svcStudent/student-progression.model';

type EntityResponseType = HttpResponse<IStudentProgression>;
type EntityArrayResponseType = HttpResponse<IStudentProgression[]>;

@Injectable({ providedIn: 'root' })
export class StudentProgressionService {
    public resourceUrl = SERVER_API_URL + 'svcstudent/api/student-progressions';

    constructor(protected http: HttpClient) {}

    create(studentProgression: IStudentProgression): Observable<EntityResponseType> {
        return this.http.post<IStudentProgression>(this.resourceUrl, studentProgression, { observe: 'response' });
    }

    update(studentProgression: IStudentProgression): Observable<EntityResponseType> {
        return this.http.put<IStudentProgression>(this.resourceUrl, studentProgression, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IStudentProgression>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IStudentProgression[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}

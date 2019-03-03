import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProgramme } from 'app/shared/model/svcProgramme/programme.model';

type EntityResponseType = HttpResponse<IProgramme>;
type EntityArrayResponseType = HttpResponse<IProgramme[]>;

@Injectable({ providedIn: 'root' })
export class ProgrammeService {
    public resourceUrl = SERVER_API_URL + 'svcprogramme/api/programmes';

    constructor(protected http: HttpClient) {}

    create(programme: IProgramme): Observable<EntityResponseType> {
        return this.http.post<IProgramme>(this.resourceUrl, programme, { observe: 'response' });
    }

    update(programme: IProgramme): Observable<EntityResponseType> {
        return this.http.put<IProgramme>(this.resourceUrl, programme, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IProgramme>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IProgramme[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}

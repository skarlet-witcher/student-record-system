import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProgrammePropDict } from 'app/shared/model/svcProgramme/programme-prop-dict.model';

type EntityResponseType = HttpResponse<IProgrammePropDict>;
type EntityArrayResponseType = HttpResponse<IProgrammePropDict[]>;

@Injectable({ providedIn: 'root' })
export class ProgrammePropDictService {
    public resourceUrl = SERVER_API_URL + 'svcprogramme/api/programme-prop';

    constructor(protected http: HttpClient) {}

    create(programmePropDict: IProgrammePropDict): Observable<EntityResponseType> {
        return this.http.post<IProgrammePropDict>(this.resourceUrl, programmePropDict, { observe: 'response' });
    }

    update(programmePropDict: IProgrammePropDict): Observable<EntityResponseType> {
        return this.http.put<IProgrammePropDict>(this.resourceUrl, programmePropDict, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IProgrammePropDict>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IProgrammePropDict[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}

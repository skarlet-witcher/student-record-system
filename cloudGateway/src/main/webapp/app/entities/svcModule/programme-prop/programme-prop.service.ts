import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProgrammeProp } from 'app/shared/model/svcModule/programme-prop.model';

type EntityResponseType = HttpResponse<IProgrammeProp>;
type EntityArrayResponseType = HttpResponse<IProgrammeProp[]>;

@Injectable({ providedIn: 'root' })
export class ProgrammePropService {
    public resourceUrl = SERVER_API_URL + 'svcmodule/api/programme-props';

    constructor(protected http: HttpClient) {}

    create(programmeProp: IProgrammeProp): Observable<EntityResponseType> {
        return this.http.post<IProgrammeProp>(this.resourceUrl, programmeProp, { observe: 'response' });
    }

    update(programmeProp: IProgrammeProp): Observable<EntityResponseType> {
        return this.http.put<IProgrammeProp>(this.resourceUrl, programmeProp, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IProgrammeProp>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IProgrammeProp[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}

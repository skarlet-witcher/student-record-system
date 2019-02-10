import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IModuleResult } from 'app/shared/model/module-result.model';

type EntityResponseType = HttpResponse<IModuleResult>;
type EntityArrayResponseType = HttpResponse<IModuleResult[]>;

@Injectable({ providedIn: 'root' })
export class ModuleResultService {
    public resourceUrl = SERVER_API_URL + 'api/module-results';

    constructor(protected http: HttpClient) {}

    create(moduleResult: IModuleResult): Observable<EntityResponseType> {
        return this.http.post<IModuleResult>(this.resourceUrl, moduleResult, { observe: 'response' });
    }

    update(moduleResult: IModuleResult): Observable<EntityResponseType> {
        return this.http.put<IModuleResult>(this.resourceUrl, moduleResult, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IModuleResult>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IModuleResult[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}

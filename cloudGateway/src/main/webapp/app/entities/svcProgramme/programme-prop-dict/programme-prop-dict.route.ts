import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ProgrammePropDict } from 'app/shared/model/svcProgramme/programme-prop-dict.model';
import { ProgrammePropDictService } from './programme-prop-dict.service';
import { ProgrammePropDictComponent } from './programme-prop-dict.component';
import { ProgrammePropDictDetailComponent } from './programme-prop-dict-detail.component';
import { ProgrammePropDictUpdateComponent } from './programme-prop-dict-update.component';
import { ProgrammePropDictDeletePopupComponent } from './programme-prop-dict-delete-dialog.component';
import { IProgrammePropDict } from 'app/shared/model/svcProgramme/programme-prop-dict.model';

@Injectable({ providedIn: 'root' })
export class ProgrammePropDictResolve implements Resolve<IProgrammePropDict> {
    constructor(private service: ProgrammePropDictService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProgrammePropDict> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ProgrammePropDict>) => response.ok),
                map((programmePropDict: HttpResponse<ProgrammePropDict>) => programmePropDict.body)
            );
        }
        return of(new ProgrammePropDict());
    }
}

export const programmePropDictRoute: Routes = [
    {
        path: '',
        component: ProgrammePropDictComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'cloudGatewayApp.svcProgrammeProgrammePropDict.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ProgrammePropDictDetailComponent,
        resolve: {
            programmePropDict: ProgrammePropDictResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cloudGatewayApp.svcProgrammeProgrammePropDict.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ProgrammePropDictUpdateComponent,
        resolve: {
            programmePropDict: ProgrammePropDictResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cloudGatewayApp.svcProgrammeProgrammePropDict.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ProgrammePropDictUpdateComponent,
        resolve: {
            programmePropDict: ProgrammePropDictResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cloudGatewayApp.svcProgrammeProgrammePropDict.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const programmePropDictPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ProgrammePropDictDeletePopupComponent,
        resolve: {
            programmePropDict: ProgrammePropDictResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cloudGatewayApp.svcProgrammeProgrammePropDict.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

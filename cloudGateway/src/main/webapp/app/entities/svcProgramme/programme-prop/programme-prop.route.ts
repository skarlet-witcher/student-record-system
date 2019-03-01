import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ProgrammeProp } from 'app/shared/model/svcProgramme/programme-prop.model';
import { ProgrammePropService } from './programme-prop.service';
import { ProgrammePropComponent } from './programme-prop.component';
import { ProgrammePropDetailComponent } from './programme-prop-detail.component';
import { ProgrammePropUpdateComponent } from './programme-prop-update.component';
import { ProgrammePropDeletePopupComponent } from './programme-prop-delete-dialog.component';
import { IProgrammeProp } from 'app/shared/model/svcProgramme/programme-prop.model';

@Injectable({ providedIn: 'root' })
export class ProgrammePropResolve implements Resolve<IProgrammeProp> {
    constructor(private service: ProgrammePropService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProgrammeProp> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ProgrammeProp>) => response.ok),
                map((programmeProp: HttpResponse<ProgrammeProp>) => programmeProp.body)
            );
        }
        return of(new ProgrammeProp());
    }
}

export const programmePropRoute: Routes = [
    {
        path: '',
        component: ProgrammePropComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'cloudGatewayApp.svcProgrammeProgrammeProp.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ProgrammePropDetailComponent,
        resolve: {
            programmeProp: ProgrammePropResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cloudGatewayApp.svcProgrammeProgrammeProp.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ProgrammePropUpdateComponent,
        resolve: {
            programmeProp: ProgrammePropResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cloudGatewayApp.svcProgrammeProgrammeProp.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ProgrammePropUpdateComponent,
        resolve: {
            programmeProp: ProgrammePropResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cloudGatewayApp.svcProgrammeProgrammeProp.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const programmePropPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ProgrammePropDeletePopupComponent,
        resolve: {
            programmeProp: ProgrammePropResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cloudGatewayApp.svcProgrammeProgrammeProp.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

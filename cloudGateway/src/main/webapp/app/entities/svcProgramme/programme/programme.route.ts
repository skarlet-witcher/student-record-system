import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Programme } from 'app/shared/model/svcProgramme/programme.model';
import { ProgrammeService } from './programme.service';
import { ProgrammeComponent } from './programme.component';
import { ProgrammeDetailComponent } from './programme-detail.component';
import { ProgrammeUpdateComponent } from './programme-update.component';
import { ProgrammeDeletePopupComponent } from './programme-delete-dialog.component';
import { IProgramme } from 'app/shared/model/svcProgramme/programme.model';

@Injectable({ providedIn: 'root' })
export class ProgrammeResolve implements Resolve<IProgramme> {
    constructor(private service: ProgrammeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProgramme> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Programme>) => response.ok),
                map((programme: HttpResponse<Programme>) => programme.body)
            );
        }
        return of(new Programme());
    }
}

export const programmeRoute: Routes = [
    {
        path: '',
        component: ProgrammeComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'cloudGatewayApp.svcProgrammeProgramme.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ProgrammeDetailComponent,
        resolve: {
            programme: ProgrammeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cloudGatewayApp.svcProgrammeProgramme.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ProgrammeUpdateComponent,
        resolve: {
            programme: ProgrammeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cloudGatewayApp.svcProgrammeProgramme.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ProgrammeUpdateComponent,
        resolve: {
            programme: ProgrammeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cloudGatewayApp.svcProgrammeProgramme.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const programmePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ProgrammeDeletePopupComponent,
        resolve: {
            programme: ProgrammeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cloudGatewayApp.svcProgrammeProgramme.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

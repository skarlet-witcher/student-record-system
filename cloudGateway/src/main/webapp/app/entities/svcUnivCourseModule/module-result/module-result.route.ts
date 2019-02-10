import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ModuleResult } from 'app/shared/model/svcUnivCourseModule/module-result.model';
import { ModuleResultService } from './module-result.service';
import { ModuleResultComponent } from './module-result.component';
import { ModuleResultDetailComponent } from './module-result-detail.component';
import { ModuleResultUpdateComponent } from './module-result-update.component';
import { ModuleResultDeletePopupComponent } from './module-result-delete-dialog.component';
import { IModuleResult } from 'app/shared/model/svcUnivCourseModule/module-result.model';

@Injectable({ providedIn: 'root' })
export class ModuleResultResolve implements Resolve<IModuleResult> {
    constructor(private service: ModuleResultService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IModuleResult> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ModuleResult>) => response.ok),
                map((moduleResult: HttpResponse<ModuleResult>) => moduleResult.body)
            );
        }
        return of(new ModuleResult());
    }
}

export const moduleResultRoute: Routes = [
    {
        path: '',
        component: ModuleResultComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'cloudGatewayApp.svcUnivCourseModuleModuleResult.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ModuleResultDetailComponent,
        resolve: {
            moduleResult: ModuleResultResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cloudGatewayApp.svcUnivCourseModuleModuleResult.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ModuleResultUpdateComponent,
        resolve: {
            moduleResult: ModuleResultResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cloudGatewayApp.svcUnivCourseModuleModuleResult.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ModuleResultUpdateComponent,
        resolve: {
            moduleResult: ModuleResultResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cloudGatewayApp.svcUnivCourseModuleModuleResult.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const moduleResultPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ModuleResultDeletePopupComponent,
        resolve: {
            moduleResult: ModuleResultResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cloudGatewayApp.svcUnivCourseModuleModuleResult.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

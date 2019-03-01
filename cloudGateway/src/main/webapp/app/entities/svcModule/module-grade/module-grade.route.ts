import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ModuleGrade } from 'app/shared/model/svcModule/module-grade.model';
import { ModuleGradeService } from './module-grade.service';
import { ModuleGradeComponent } from './module-grade.component';
import { ModuleGradeDetailComponent } from './module-grade-detail.component';
import { ModuleGradeUpdateComponent } from './module-grade-update.component';
import { ModuleGradeDeletePopupComponent } from './module-grade-delete-dialog.component';
import { IModuleGrade } from 'app/shared/model/svcModule/module-grade.model';

@Injectable({ providedIn: 'root' })
export class ModuleGradeResolve implements Resolve<IModuleGrade> {
    constructor(private service: ModuleGradeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IModuleGrade> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ModuleGrade>) => response.ok),
                map((moduleGrade: HttpResponse<ModuleGrade>) => moduleGrade.body)
            );
        }
        return of(new ModuleGrade());
    }
}

export const moduleGradeRoute: Routes = [
    {
        path: '',
        component: ModuleGradeComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'cloudGatewayApp.svcModuleModuleGrade.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ModuleGradeDetailComponent,
        resolve: {
            moduleGrade: ModuleGradeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cloudGatewayApp.svcModuleModuleGrade.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ModuleGradeUpdateComponent,
        resolve: {
            moduleGrade: ModuleGradeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cloudGatewayApp.svcModuleModuleGrade.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ModuleGradeUpdateComponent,
        resolve: {
            moduleGrade: ModuleGradeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cloudGatewayApp.svcModuleModuleGrade.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const moduleGradePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ModuleGradeDeletePopupComponent,
        resolve: {
            moduleGrade: ModuleGradeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cloudGatewayApp.svcModuleModuleGrade.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StudentModule } from 'app/shared/model/student-module.model';
import { StudentModuleService } from './student-module.service';
import { StudentModuleComponent } from './student-module.component';
import { StudentModuleDetailComponent } from './student-module-detail.component';
import { StudentModuleUpdateComponent } from './student-module-update.component';
import { StudentModuleDeletePopupComponent } from './student-module-delete-dialog.component';
import { IStudentModule } from 'app/shared/model/student-module.model';

@Injectable({ providedIn: 'root' })
export class StudentModuleResolve implements Resolve<IStudentModule> {
    constructor(private service: StudentModuleService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IStudentModule> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<StudentModule>) => response.ok),
                map((studentModule: HttpResponse<StudentModule>) => studentModule.body)
            );
        }
        return of(new StudentModule());
    }
}

export const studentModuleRoute: Routes = [
    {
        path: '',
        component: StudentModuleComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'cloudGatewayApp.studentModule.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: StudentModuleDetailComponent,
        resolve: {
            studentModule: StudentModuleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cloudGatewayApp.studentModule.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: StudentModuleUpdateComponent,
        resolve: {
            studentModule: StudentModuleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cloudGatewayApp.studentModule.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: StudentModuleUpdateComponent,
        resolve: {
            studentModule: StudentModuleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cloudGatewayApp.studentModule.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const studentModulePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: StudentModuleDeletePopupComponent,
        resolve: {
            studentModule: StudentModuleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cloudGatewayApp.studentModule.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

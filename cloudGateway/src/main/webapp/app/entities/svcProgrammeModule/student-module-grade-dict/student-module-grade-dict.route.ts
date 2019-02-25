import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StudentModuleGradeDict } from 'app/shared/model/svcProgrammeModule/student-module-grade-dict.model';
import { StudentModuleGradeDictService } from './student-module-grade-dict.service';
import { StudentModuleGradeDictComponent } from './student-module-grade-dict.component';
import { StudentModuleGradeDictDetailComponent } from './student-module-grade-dict-detail.component';
import { StudentModuleGradeDictUpdateComponent } from './student-module-grade-dict-update.component';
import { StudentModuleGradeDictDeletePopupComponent } from './student-module-grade-dict-delete-dialog.component';
import { IStudentModuleGradeDict } from 'app/shared/model/svcProgrammeModule/student-module-grade-dict.model';

@Injectable({ providedIn: 'root' })
export class StudentModuleGradeDictResolve implements Resolve<IStudentModuleGradeDict> {
    constructor(private service: StudentModuleGradeDictService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IStudentModuleGradeDict> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<StudentModuleGradeDict>) => response.ok),
                map((studentModuleGradeDict: HttpResponse<StudentModuleGradeDict>) => studentModuleGradeDict.body)
            );
        }
        return of(new StudentModuleGradeDict());
    }
}

export const studentModuleGradeDictRoute: Routes = [
    {
        path: '',
        component: StudentModuleGradeDictComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'cloudGatewayApp.svcProgrammeModuleStudentModuleGradeDict.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: StudentModuleGradeDictDetailComponent,
        resolve: {
            studentModuleGradeDict: StudentModuleGradeDictResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cloudGatewayApp.svcProgrammeModuleStudentModuleGradeDict.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: StudentModuleGradeDictUpdateComponent,
        resolve: {
            studentModuleGradeDict: StudentModuleGradeDictResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cloudGatewayApp.svcProgrammeModuleStudentModuleGradeDict.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: StudentModuleGradeDictUpdateComponent,
        resolve: {
            studentModuleGradeDict: StudentModuleGradeDictResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cloudGatewayApp.svcProgrammeModuleStudentModuleGradeDict.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const studentModuleGradeDictPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: StudentModuleGradeDictDeletePopupComponent,
        resolve: {
            studentModuleGradeDict: StudentModuleGradeDictResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cloudGatewayApp.svcProgrammeModuleStudentModuleGradeDict.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

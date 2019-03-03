import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StudentEnroll } from 'app/shared/model/svcStudent/student-enroll.model';
import { StudentEnrollService } from './student-enroll.service';
import { StudentEnrollComponent } from './student-enroll.component';
import { StudentEnrollDetailComponent } from './student-enroll-detail.component';
import { StudentEnrollUpdateComponent } from './student-enroll-update.component';
import { StudentEnrollDeletePopupComponent } from './student-enroll-delete-dialog.component';
import { IStudentEnroll } from 'app/shared/model/svcStudent/student-enroll.model';

@Injectable({ providedIn: 'root' })
export class StudentEnrollResolve implements Resolve<IStudentEnroll> {
    constructor(private service: StudentEnrollService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IStudentEnroll> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<StudentEnroll>) => response.ok),
                map((studentEnroll: HttpResponse<StudentEnroll>) => studentEnroll.body)
            );
        }
        return of(new StudentEnroll());
    }
}

export const studentEnrollRoute: Routes = [
    {
        path: '',
        component: StudentEnrollComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'cloudGatewayApp.svcStudentStudentEnroll.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: StudentEnrollDetailComponent,
        resolve: {
            studentEnroll: StudentEnrollResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cloudGatewayApp.svcStudentStudentEnroll.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: StudentEnrollUpdateComponent,
        resolve: {
            studentEnroll: StudentEnrollResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cloudGatewayApp.svcStudentStudentEnroll.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: StudentEnrollUpdateComponent,
        resolve: {
            studentEnroll: StudentEnrollResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cloudGatewayApp.svcStudentStudentEnroll.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const studentEnrollPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: StudentEnrollDeletePopupComponent,
        resolve: {
            studentEnroll: StudentEnrollResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cloudGatewayApp.svcStudentStudentEnroll.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

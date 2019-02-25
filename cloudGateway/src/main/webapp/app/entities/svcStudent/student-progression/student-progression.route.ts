import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StudentProgression } from 'app/shared/model/svcStudent/student-progression.model';
import { StudentProgressionService } from './student-progression.service';
import { StudentProgressionComponent } from './student-progression.component';
import { StudentProgressionDetailComponent } from './student-progression-detail.component';
import { StudentProgressionUpdateComponent } from './student-progression-update.component';
import { StudentProgressionDeletePopupComponent } from './student-progression-delete-dialog.component';
import { IStudentProgression } from 'app/shared/model/svcStudent/student-progression.model';

@Injectable({ providedIn: 'root' })
export class StudentProgressionResolve implements Resolve<IStudentProgression> {
    constructor(private service: StudentProgressionService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IStudentProgression> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<StudentProgression>) => response.ok),
                map((studentProgression: HttpResponse<StudentProgression>) => studentProgression.body)
            );
        }
        return of(new StudentProgression());
    }
}

export const studentProgressionRoute: Routes = [
    {
        path: '',
        component: StudentProgressionComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'cloudGatewayApp.svcStudentStudentProgression.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: StudentProgressionDetailComponent,
        resolve: {
            studentProgression: StudentProgressionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cloudGatewayApp.svcStudentStudentProgression.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: StudentProgressionUpdateComponent,
        resolve: {
            studentProgression: StudentProgressionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cloudGatewayApp.svcStudentStudentProgression.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: StudentProgressionUpdateComponent,
        resolve: {
            studentProgression: StudentProgressionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cloudGatewayApp.svcStudentStudentProgression.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const studentProgressionPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: StudentProgressionDeletePopupComponent,
        resolve: {
            studentProgression: StudentProgressionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cloudGatewayApp.svcStudentStudentProgression.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

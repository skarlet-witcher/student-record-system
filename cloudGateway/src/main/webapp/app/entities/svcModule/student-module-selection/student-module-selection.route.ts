import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StudentModuleSelection } from 'app/shared/model/svcModule/student-module-selection.model';
import { StudentModuleSelectionService } from './student-module-selection.service';
import { StudentModuleSelectionComponent } from './student-module-selection.component';
import { StudentModuleSelectionDetailComponent } from './student-module-selection-detail.component';
import { StudentModuleSelectionUpdateComponent } from './student-module-selection-update.component';
import { StudentModuleSelectionDeletePopupComponent } from './student-module-selection-delete-dialog.component';
import { IStudentModuleSelection } from 'app/shared/model/svcModule/student-module-selection.model';

@Injectable({ providedIn: 'root' })
export class StudentModuleSelectionResolve implements Resolve<IStudentModuleSelection> {
    constructor(private service: StudentModuleSelectionService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IStudentModuleSelection> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<StudentModuleSelection>) => response.ok),
                map((studentModuleSelection: HttpResponse<StudentModuleSelection>) => studentModuleSelection.body)
            );
        }
        return of(new StudentModuleSelection());
    }
}

export const studentModuleSelectionRoute: Routes = [
    {
        path: '',
        component: StudentModuleSelectionComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'cloudGatewayApp.svcModuleStudentModuleSelection.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: StudentModuleSelectionDetailComponent,
        resolve: {
            studentModuleSelection: StudentModuleSelectionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cloudGatewayApp.svcModuleStudentModuleSelection.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: StudentModuleSelectionUpdateComponent,
        resolve: {
            studentModuleSelection: StudentModuleSelectionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cloudGatewayApp.svcModuleStudentModuleSelection.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: StudentModuleSelectionUpdateComponent,
        resolve: {
            studentModuleSelection: StudentModuleSelectionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cloudGatewayApp.svcModuleStudentModuleSelection.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const studentModuleSelectionPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: StudentModuleSelectionDeletePopupComponent,
        resolve: {
            studentModuleSelection: StudentModuleSelectionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cloudGatewayApp.svcModuleStudentModuleSelection.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

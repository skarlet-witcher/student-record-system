import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IDepartment } from 'app/shared/model/svcModule/department.model';
import { DepartmentService } from './department.service';
import { IFaculty } from 'app/shared/model/svcModule/faculty.model';
import { FacultyService } from 'app/entities/svcModule/faculty';

@Component({
    selector: 'jhi-department-update',
    templateUrl: './department-update.component.html'
})
export class DepartmentUpdateComponent implements OnInit {
    department: IDepartment;
    isSaving: boolean;

    faculties: IFaculty[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected departmentService: DepartmentService,
        protected facultyService: FacultyService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ department }) => {
            this.department = department;
        });
        this.facultyService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IFaculty[]>) => mayBeOk.ok),
                map((response: HttpResponse<IFaculty[]>) => response.body)
            )
            .subscribe((res: IFaculty[]) => (this.faculties = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.department.id !== undefined) {
            this.subscribeToSaveResponse(this.departmentService.update(this.department));
        } else {
            this.subscribeToSaveResponse(this.departmentService.create(this.department));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IDepartment>>) {
        result.subscribe((res: HttpResponse<IDepartment>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackFacultyById(index: number, item: IFaculty) {
        return item.id;
    }
}

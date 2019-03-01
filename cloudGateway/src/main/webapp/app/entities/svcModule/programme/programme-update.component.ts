import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IProgramme } from 'app/shared/model/svcModule/programme.model';
import { ProgrammeService } from './programme.service';
import { IDepartment } from 'app/shared/model/svcModule/department.model';
import { DepartmentService } from 'app/entities/svcModule/department';

@Component({
    selector: 'jhi-programme-update',
    templateUrl: './programme-update.component.html'
})
export class ProgrammeUpdateComponent implements OnInit {
    programme: IProgramme;
    isSaving: boolean;

    departments: IDepartment[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected programmeService: ProgrammeService,
        protected departmentService: DepartmentService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ programme }) => {
            this.programme = programme;
        });
        this.departmentService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IDepartment[]>) => mayBeOk.ok),
                map((response: HttpResponse<IDepartment[]>) => response.body)
            )
            .subscribe((res: IDepartment[]) => (this.departments = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.programme.id !== undefined) {
            this.subscribeToSaveResponse(this.programmeService.update(this.programme));
        } else {
            this.subscribeToSaveResponse(this.programmeService.create(this.programme));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IProgramme>>) {
        result.subscribe((res: HttpResponse<IProgramme>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackDepartmentById(index: number, item: IDepartment) {
        return item.id;
    }
}

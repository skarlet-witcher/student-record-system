import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IModuleGrade } from 'app/shared/model/svcModule/module-grade.model';
import { ModuleGradeService } from './module-grade.service';

@Component({
    selector: 'jhi-module-grade-update',
    templateUrl: './module-grade-update.component.html'
})
export class ModuleGradeUpdateComponent implements OnInit {
    moduleGrade: IModuleGrade;
    isSaving: boolean;

    constructor(protected moduleGradeService: ModuleGradeService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ moduleGrade }) => {
            this.moduleGrade = moduleGrade;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.moduleGrade.id !== undefined) {
            this.subscribeToSaveResponse(this.moduleGradeService.update(this.moduleGrade));
        } else {
            this.subscribeToSaveResponse(this.moduleGradeService.create(this.moduleGrade));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IModuleGrade>>) {
        result.subscribe((res: HttpResponse<IModuleGrade>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}

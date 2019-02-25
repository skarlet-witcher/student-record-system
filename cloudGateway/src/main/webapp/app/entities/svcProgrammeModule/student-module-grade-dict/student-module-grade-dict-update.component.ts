import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IStudentModuleGradeDict } from 'app/shared/model/svcProgrammeModule/student-module-grade-dict.model';
import { StudentModuleGradeDictService } from './student-module-grade-dict.service';

@Component({
    selector: 'jhi-student-module-grade-dict-update',
    templateUrl: './student-module-grade-dict-update.component.html'
})
export class StudentModuleGradeDictUpdateComponent implements OnInit {
    studentModuleGradeDict: IStudentModuleGradeDict;
    isSaving: boolean;

    constructor(protected studentModuleGradeDictService: StudentModuleGradeDictService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ studentModuleGradeDict }) => {
            this.studentModuleGradeDict = studentModuleGradeDict;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.studentModuleGradeDict.id !== undefined) {
            this.subscribeToSaveResponse(this.studentModuleGradeDictService.update(this.studentModuleGradeDict));
        } else {
            this.subscribeToSaveResponse(this.studentModuleGradeDictService.create(this.studentModuleGradeDict));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IStudentModuleGradeDict>>) {
        result.subscribe(
            (res: HttpResponse<IStudentModuleGradeDict>) => this.onSaveSuccess(),
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}

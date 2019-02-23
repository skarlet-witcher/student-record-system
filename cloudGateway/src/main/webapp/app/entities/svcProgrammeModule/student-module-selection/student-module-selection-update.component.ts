import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IStudentModuleSelection } from 'app/shared/model/svcProgrammeModule/student-module-selection.model';
import { StudentModuleSelectionService } from './student-module-selection.service';
import { IModule } from 'app/shared/model/svcProgrammeModule/module.model';
import { ModuleService } from 'app/entities/svcProgrammeModule/module';
import { IStudentModuleGradeDict } from 'app/shared/model/svcProgrammeModule/student-module-grade-dict.model';
import { StudentModuleGradeDictService } from 'app/entities/svcProgrammeModule/student-module-grade-dict';

@Component({
    selector: 'jhi-student-module-selection-update',
    templateUrl: './student-module-selection-update.component.html'
})
export class StudentModuleSelectionUpdateComponent implements OnInit {
    studentModuleSelection: IStudentModuleSelection;
    isSaving: boolean;

    modules: IModule[];

    studentmodulegradedicts: IStudentModuleGradeDict[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected studentModuleSelectionService: StudentModuleSelectionService,
        protected moduleService: ModuleService,
        protected studentModuleGradeDictService: StudentModuleGradeDictService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ studentModuleSelection }) => {
            this.studentModuleSelection = studentModuleSelection;
        });
        this.moduleService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IModule[]>) => mayBeOk.ok),
                map((response: HttpResponse<IModule[]>) => response.body)
            )
            .subscribe((res: IModule[]) => (this.modules = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.studentModuleGradeDictService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IStudentModuleGradeDict[]>) => mayBeOk.ok),
                map((response: HttpResponse<IStudentModuleGradeDict[]>) => response.body)
            )
            .subscribe(
                (res: IStudentModuleGradeDict[]) => (this.studentmodulegradedicts = res),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.studentModuleSelection.id !== undefined) {
            this.subscribeToSaveResponse(this.studentModuleSelectionService.update(this.studentModuleSelection));
        } else {
            this.subscribeToSaveResponse(this.studentModuleSelectionService.create(this.studentModuleSelection));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IStudentModuleSelection>>) {
        result.subscribe(
            (res: HttpResponse<IStudentModuleSelection>) => this.onSaveSuccess(),
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

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackModuleById(index: number, item: IModule) {
        return item.id;
    }

    trackStudentModuleGradeDictById(index: number, item: IStudentModuleGradeDict) {
        return item.id;
    }
}

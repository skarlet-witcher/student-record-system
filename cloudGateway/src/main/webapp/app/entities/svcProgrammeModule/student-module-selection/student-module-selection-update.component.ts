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
import { IModuleGrade } from 'app/shared/model/svcProgrammeModule/module-grade.model';
import { ModuleGradeService } from 'app/entities/svcProgrammeModule/module-grade';

@Component({
    selector: 'jhi-student-module-selection-update',
    templateUrl: './student-module-selection-update.component.html'
})
export class StudentModuleSelectionUpdateComponent implements OnInit {
    studentModuleSelection: IStudentModuleSelection;
    isSaving: boolean;

    modules: IModule[];

    modulegrades: IModuleGrade[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected studentModuleSelectionService: StudentModuleSelectionService,
        protected moduleService: ModuleService,
        protected moduleGradeService: ModuleGradeService,
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
        this.moduleGradeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IModuleGrade[]>) => mayBeOk.ok),
                map((response: HttpResponse<IModuleGrade[]>) => response.body)
            )
            .subscribe((res: IModuleGrade[]) => (this.modulegrades = res), (res: HttpErrorResponse) => this.onError(res.message));
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

    trackModuleGradeById(index: number, item: IModuleGrade) {
        return item.id;
    }
}

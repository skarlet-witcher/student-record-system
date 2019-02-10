import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IStudentModule } from 'app/shared/model/student-module.model';
import { StudentModuleService } from './student-module.service';

@Component({
    selector: 'jhi-student-module-update',
    templateUrl: './student-module-update.component.html'
})
export class StudentModuleUpdateComponent implements OnInit {
    studentModule: IStudentModule;
    isSaving: boolean;

    constructor(protected studentModuleService: StudentModuleService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ studentModule }) => {
            this.studentModule = studentModule;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.studentModule.id !== undefined) {
            this.subscribeToSaveResponse(this.studentModuleService.update(this.studentModule));
        } else {
            this.subscribeToSaveResponse(this.studentModuleService.create(this.studentModule));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IStudentModule>>) {
        result.subscribe((res: HttpResponse<IStudentModule>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}

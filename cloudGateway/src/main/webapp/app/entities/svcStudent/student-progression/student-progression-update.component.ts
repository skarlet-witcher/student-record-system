import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IStudentProgression } from 'app/shared/model/svcStudent/student-progression.model';
import { StudentProgressionService } from './student-progression.service';
import { IStudent } from 'app/shared/model/svcStudent/student.model';
import { StudentService } from 'app/entities/svcStudent/student';

@Component({
    selector: 'jhi-student-progression-update',
    templateUrl: './student-progression-update.component.html'
})
export class StudentProgressionUpdateComponent implements OnInit {
    studentProgression: IStudentProgression;
    isSaving: boolean;

    students: IStudent[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected studentProgressionService: StudentProgressionService,
        protected studentService: StudentService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ studentProgression }) => {
            this.studentProgression = studentProgression;
        });
        this.studentService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IStudent[]>) => mayBeOk.ok),
                map((response: HttpResponse<IStudent[]>) => response.body)
            )
            .subscribe((res: IStudent[]) => (this.students = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.studentProgression.id !== undefined) {
            this.subscribeToSaveResponse(this.studentProgressionService.update(this.studentProgression));
        } else {
            this.subscribeToSaveResponse(this.studentProgressionService.create(this.studentProgression));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IStudentProgression>>) {
        result.subscribe((res: HttpResponse<IStudentProgression>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackStudentById(index: number, item: IStudent) {
        return item.id;
    }
}

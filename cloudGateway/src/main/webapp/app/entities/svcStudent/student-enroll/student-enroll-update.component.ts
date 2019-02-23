import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IStudentEnroll } from 'app/shared/model/svcStudent/student-enroll.model';
import { StudentEnrollService } from './student-enroll.service';
import { IStudent } from 'app/shared/model/svcStudent/student.model';
import { StudentService } from 'app/entities/svcStudent/student';

@Component({
    selector: 'jhi-student-enroll-update',
    templateUrl: './student-enroll-update.component.html'
})
export class StudentEnrollUpdateComponent implements OnInit {
    studentEnroll: IStudentEnroll;
    isSaving: boolean;

    students: IStudent[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected studentEnrollService: StudentEnrollService,
        protected studentService: StudentService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ studentEnroll }) => {
            this.studentEnroll = studentEnroll;
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
        if (this.studentEnroll.id !== undefined) {
            this.subscribeToSaveResponse(this.studentEnrollService.update(this.studentEnroll));
        } else {
            this.subscribeToSaveResponse(this.studentEnrollService.create(this.studentEnroll));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IStudentEnroll>>) {
        result.subscribe((res: HttpResponse<IStudentEnroll>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

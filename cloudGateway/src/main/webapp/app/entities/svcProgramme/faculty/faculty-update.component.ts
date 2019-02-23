import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IFaculty } from 'app/shared/model/svcProgramme/faculty.model';
import { FacultyService } from './faculty.service';

@Component({
    selector: 'jhi-faculty-update',
    templateUrl: './faculty-update.component.html'
})
export class FacultyUpdateComponent implements OnInit {
    faculty: IFaculty;
    isSaving: boolean;

    constructor(protected facultyService: FacultyService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ faculty }) => {
            this.faculty = faculty;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.faculty.id !== undefined) {
            this.subscribeToSaveResponse(this.facultyService.update(this.faculty));
        } else {
            this.subscribeToSaveResponse(this.facultyService.create(this.faculty));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IFaculty>>) {
        result.subscribe((res: HttpResponse<IFaculty>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}

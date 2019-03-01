import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IProgrammeProp } from 'app/shared/model/svcProgrammeModule/programme-prop.model';
import { ProgrammePropService } from './programme-prop.service';

@Component({
    selector: 'jhi-programme-prop-update',
    templateUrl: './programme-prop-update.component.html'
})
export class ProgrammePropUpdateComponent implements OnInit {
    programmeProp: IProgrammeProp;
    isSaving: boolean;

    constructor(protected programmePropService: ProgrammePropService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ programmeProp }) => {
            this.programmeProp = programmeProp;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.programmeProp.id !== undefined) {
            this.subscribeToSaveResponse(this.programmePropService.update(this.programmeProp));
        } else {
            this.subscribeToSaveResponse(this.programmePropService.create(this.programmeProp));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IProgrammeProp>>) {
        result.subscribe((res: HttpResponse<IProgrammeProp>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}

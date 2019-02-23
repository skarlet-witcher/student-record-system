import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IProgrammePropDict } from 'app/shared/model/svcProgramme/programme-prop-dict.model';
import { ProgrammePropDictService } from './programme-prop-dict.service';

@Component({
    selector: 'jhi-programme-prop-dict-update',
    templateUrl: './programme-prop-dict-update.component.html'
})
export class ProgrammePropDictUpdateComponent implements OnInit {
    programmePropDict: IProgrammePropDict;
    isSaving: boolean;

    constructor(protected programmePropDictService: ProgrammePropDictService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ programmePropDict }) => {
            this.programmePropDict = programmePropDict;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.programmePropDict.id !== undefined) {
            this.subscribeToSaveResponse(this.programmePropDictService.update(this.programmePropDict));
        } else {
            this.subscribeToSaveResponse(this.programmePropDictService.create(this.programmePropDict));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IProgrammePropDict>>) {
        result.subscribe((res: HttpResponse<IProgrammePropDict>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}

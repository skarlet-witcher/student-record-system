import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IModuleResult } from 'app/shared/model/module-result.model';
import { ModuleResultService } from './module-result.service';
import { IModule } from 'app/shared/model/module.model';
import { ModuleService } from 'app/entities/module';

@Component({
    selector: 'jhi-module-result-update',
    templateUrl: './module-result-update.component.html'
})
export class ModuleResultUpdateComponent implements OnInit {
    moduleResult: IModuleResult;
    isSaving: boolean;

    modules: IModule[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected moduleResultService: ModuleResultService,
        protected moduleService: ModuleService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ moduleResult }) => {
            this.moduleResult = moduleResult;
        });
        this.moduleService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IModule[]>) => mayBeOk.ok),
                map((response: HttpResponse<IModule[]>) => response.body)
            )
            .subscribe((res: IModule[]) => (this.modules = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.moduleResult.id !== undefined) {
            this.subscribeToSaveResponse(this.moduleResultService.update(this.moduleResult));
        } else {
            this.subscribeToSaveResponse(this.moduleResultService.create(this.moduleResult));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IModuleResult>>) {
        result.subscribe((res: HttpResponse<IModuleResult>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
}

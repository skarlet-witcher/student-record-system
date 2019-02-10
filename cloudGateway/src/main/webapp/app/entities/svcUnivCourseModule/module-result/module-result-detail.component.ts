import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IModuleResult } from 'app/shared/model/svcUnivCourseModule/module-result.model';

@Component({
    selector: 'jhi-module-result-detail',
    templateUrl: './module-result-detail.component.html'
})
export class ModuleResultDetailComponent implements OnInit {
    moduleResult: IModuleResult;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ moduleResult }) => {
            this.moduleResult = moduleResult;
        });
    }

    previousState() {
        window.history.back();
    }
}

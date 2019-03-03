import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IModuleGrade } from 'app/shared/model/svcModule/module-grade.model';

@Component({
    selector: 'jhi-module-grade-detail',
    templateUrl: './module-grade-detail.component.html'
})
export class ModuleGradeDetailComponent implements OnInit {
    moduleGrade: IModuleGrade;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ moduleGrade }) => {
            this.moduleGrade = moduleGrade;
        });
    }

    previousState() {
        window.history.back();
    }
}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStudentModule } from 'app/shared/model/student-module.model';

@Component({
    selector: 'jhi-student-module-detail',
    templateUrl: './student-module-detail.component.html'
})
export class StudentModuleDetailComponent implements OnInit {
    studentModule: IStudentModule;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ studentModule }) => {
            this.studentModule = studentModule;
        });
    }

    previousState() {
        window.history.back();
    }
}

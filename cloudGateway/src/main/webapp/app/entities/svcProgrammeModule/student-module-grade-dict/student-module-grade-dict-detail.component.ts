import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStudentModuleGradeDict } from 'app/shared/model/svcProgrammeModule/student-module-grade-dict.model';

@Component({
    selector: 'jhi-student-module-grade-dict-detail',
    templateUrl: './student-module-grade-dict-detail.component.html'
})
export class StudentModuleGradeDictDetailComponent implements OnInit {
    studentModuleGradeDict: IStudentModuleGradeDict;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ studentModuleGradeDict }) => {
            this.studentModuleGradeDict = studentModuleGradeDict;
        });
    }

    previousState() {
        window.history.back();
    }
}

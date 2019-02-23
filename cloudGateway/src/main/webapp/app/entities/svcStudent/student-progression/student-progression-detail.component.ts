import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStudentProgression } from 'app/shared/model/svcStudent/student-progression.model';

@Component({
    selector: 'jhi-student-progression-detail',
    templateUrl: './student-progression-detail.component.html'
})
export class StudentProgressionDetailComponent implements OnInit {
    studentProgression: IStudentProgression;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ studentProgression }) => {
            this.studentProgression = studentProgression;
        });
    }

    previousState() {
        window.history.back();
    }
}

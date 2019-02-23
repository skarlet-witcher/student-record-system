import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStudentEnroll } from 'app/shared/model/svcStudent/student-enroll.model';

@Component({
    selector: 'jhi-student-enroll-detail',
    templateUrl: './student-enroll-detail.component.html'
})
export class StudentEnrollDetailComponent implements OnInit {
    studentEnroll: IStudentEnroll;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ studentEnroll }) => {
            this.studentEnroll = studentEnroll;
        });
    }

    previousState() {
        window.history.back();
    }
}

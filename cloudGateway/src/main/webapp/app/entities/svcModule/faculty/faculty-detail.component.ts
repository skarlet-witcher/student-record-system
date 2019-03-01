import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFaculty } from 'app/shared/model/svcModule/faculty.model';

@Component({
    selector: 'jhi-faculty-detail',
    templateUrl: './faculty-detail.component.html'
})
export class FacultyDetailComponent implements OnInit {
    faculty: IFaculty;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ faculty }) => {
            this.faculty = faculty;
        });
    }

    previousState() {
        window.history.back();
    }
}

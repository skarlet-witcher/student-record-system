import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStudentModuleSelection } from 'app/shared/model/svcModule/student-module-selection.model';

@Component({
    selector: 'jhi-student-module-selection-detail',
    templateUrl: './student-module-selection-detail.component.html'
})
export class StudentModuleSelectionDetailComponent implements OnInit {
    studentModuleSelection: IStudentModuleSelection;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ studentModuleSelection }) => {
            this.studentModuleSelection = studentModuleSelection;
        });
    }

    previousState() {
        window.history.back();
    }
}

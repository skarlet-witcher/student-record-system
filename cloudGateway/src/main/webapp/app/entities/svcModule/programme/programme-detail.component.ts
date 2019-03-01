import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProgramme } from 'app/shared/model/svcModule/programme.model';

@Component({
    selector: 'jhi-programme-detail',
    templateUrl: './programme-detail.component.html'
})
export class ProgrammeDetailComponent implements OnInit {
    programme: IProgramme;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ programme }) => {
            this.programme = programme;
        });
    }

    previousState() {
        window.history.back();
    }
}

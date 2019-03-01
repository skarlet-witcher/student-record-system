import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProgrammeProp } from 'app/shared/model/svcModule/programme-prop.model';

@Component({
    selector: 'jhi-programme-prop-detail',
    templateUrl: './programme-prop-detail.component.html'
})
export class ProgrammePropDetailComponent implements OnInit {
    programmeProp: IProgrammeProp;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ programmeProp }) => {
            this.programmeProp = programmeProp;
        });
    }

    previousState() {
        window.history.back();
    }
}

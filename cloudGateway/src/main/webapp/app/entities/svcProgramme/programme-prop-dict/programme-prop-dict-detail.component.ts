import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProgrammePropDict } from 'app/shared/model/svcProgramme/programme-prop-dict.model';

@Component({
    selector: 'jhi-programme-prop-dict-detail',
    templateUrl: './programme-prop-dict-detail.component.html'
})
export class ProgrammePropDictDetailComponent implements OnInit {
    programmePropDict: IProgrammePropDict;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ programmePropDict }) => {
            this.programmePropDict = programmePropDict;
        });
    }

    previousState() {
        window.history.back();
    }
}

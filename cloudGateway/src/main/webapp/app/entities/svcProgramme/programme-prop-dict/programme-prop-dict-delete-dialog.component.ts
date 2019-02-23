import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProgrammePropDict } from 'app/shared/model/svcProgramme/programme-prop-dict.model';
import { ProgrammePropDictService } from './programme-prop-dict.service';

@Component({
    selector: 'jhi-programme-prop-dict-delete-dialog',
    templateUrl: './programme-prop-dict-delete-dialog.component.html'
})
export class ProgrammePropDictDeleteDialogComponent {
    programmePropDict: IProgrammePropDict;

    constructor(
        protected programmePropDictService: ProgrammePropDictService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.programmePropDictService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'programmePropDictListModification',
                content: 'Deleted an programmePropDict'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-programme-prop-dict-delete-popup',
    template: ''
})
export class ProgrammePropDictDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ programmePropDict }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ProgrammePropDictDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.programmePropDict = programmePropDict;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/programme-prop-dict', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/programme-prop-dict', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}

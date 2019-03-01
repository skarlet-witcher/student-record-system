import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProgrammeProp } from 'app/shared/model/svcModule/programme-prop.model';
import { ProgrammePropService } from './programme-prop.service';

@Component({
    selector: 'jhi-programme-prop-delete-dialog',
    templateUrl: './programme-prop-delete-dialog.component.html'
})
export class ProgrammePropDeleteDialogComponent {
    programmeProp: IProgrammeProp;

    constructor(
        protected programmePropService: ProgrammePropService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.programmePropService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'programmePropListModification',
                content: 'Deleted an programmeProp'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-programme-prop-delete-popup',
    template: ''
})
export class ProgrammePropDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ programmeProp }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ProgrammePropDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.programmeProp = programmeProp;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/programme-prop', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/programme-prop', { outlets: { popup: null } }]);
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

import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProgramme } from 'app/shared/model/svcProgrammeModule/programme.model';
import { ProgrammeService } from './programme.service';

@Component({
    selector: 'jhi-programme-delete-dialog',
    templateUrl: './programme-delete-dialog.component.html'
})
export class ProgrammeDeleteDialogComponent {
    programme: IProgramme;

    constructor(
        protected programmeService: ProgrammeService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.programmeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'programmeListModification',
                content: 'Deleted an programme'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-programme-delete-popup',
    template: ''
})
export class ProgrammeDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ programme }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ProgrammeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.programme = programme;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/programme', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/programme', { outlets: { popup: null } }]);
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

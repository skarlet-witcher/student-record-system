import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IModuleResult } from 'app/shared/model/module-result.model';
import { ModuleResultService } from './module-result.service';

@Component({
    selector: 'jhi-module-result-delete-dialog',
    templateUrl: './module-result-delete-dialog.component.html'
})
export class ModuleResultDeleteDialogComponent {
    moduleResult: IModuleResult;

    constructor(
        protected moduleResultService: ModuleResultService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.moduleResultService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'moduleResultListModification',
                content: 'Deleted an moduleResult'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-module-result-delete-popup',
    template: ''
})
export class ModuleResultDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ moduleResult }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ModuleResultDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.moduleResult = moduleResult;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/module-result', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/module-result', { outlets: { popup: null } }]);
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

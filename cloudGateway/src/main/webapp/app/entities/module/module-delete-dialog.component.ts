import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IModule } from 'app/shared/model/module.model';
import { ModuleService } from './module.service';

@Component({
    selector: 'jhi-module-delete-dialog',
    templateUrl: './module-delete-dialog.component.html'
})
export class ModuleDeleteDialogComponent {
    module: IModule;

    constructor(protected moduleService: ModuleService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.moduleService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'moduleListModification',
                content: 'Deleted an module'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-module-delete-popup',
    template: ''
})
export class ModuleDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ module }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ModuleDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.module = module;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/module', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/module', { outlets: { popup: null } }]);
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

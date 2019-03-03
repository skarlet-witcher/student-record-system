import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStudentModuleSelection } from 'app/shared/model/svcModule/student-module-selection.model';
import { StudentModuleSelectionService } from './student-module-selection.service';

@Component({
    selector: 'jhi-student-module-selection-delete-dialog',
    templateUrl: './student-module-selection-delete-dialog.component.html'
})
export class StudentModuleSelectionDeleteDialogComponent {
    studentModuleSelection: IStudentModuleSelection;

    constructor(
        protected studentModuleSelectionService: StudentModuleSelectionService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.studentModuleSelectionService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'studentModuleSelectionListModification',
                content: 'Deleted an studentModuleSelection'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-student-module-selection-delete-popup',
    template: ''
})
export class StudentModuleSelectionDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ studentModuleSelection }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(StudentModuleSelectionDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.studentModuleSelection = studentModuleSelection;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/student-module-selection', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/student-module-selection', { outlets: { popup: null } }]);
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

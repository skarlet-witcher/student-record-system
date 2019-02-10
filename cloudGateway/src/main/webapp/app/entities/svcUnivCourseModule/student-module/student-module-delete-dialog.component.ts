import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStudentModule } from 'app/shared/model/svcUnivCourseModule/student-module.model';
import { StudentModuleService } from './student-module.service';

@Component({
    selector: 'jhi-student-module-delete-dialog',
    templateUrl: './student-module-delete-dialog.component.html'
})
export class StudentModuleDeleteDialogComponent {
    studentModule: IStudentModule;

    constructor(
        protected studentModuleService: StudentModuleService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.studentModuleService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'studentModuleListModification',
                content: 'Deleted an studentModule'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-student-module-delete-popup',
    template: ''
})
export class StudentModuleDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ studentModule }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(StudentModuleDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.studentModule = studentModule;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/student-module', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/student-module', { outlets: { popup: null } }]);
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

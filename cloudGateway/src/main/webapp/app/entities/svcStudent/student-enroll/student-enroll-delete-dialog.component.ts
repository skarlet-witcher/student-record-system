import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStudentEnroll } from 'app/shared/model/svcStudent/student-enroll.model';
import { StudentEnrollService } from './student-enroll.service';

@Component({
    selector: 'jhi-student-enroll-delete-dialog',
    templateUrl: './student-enroll-delete-dialog.component.html'
})
export class StudentEnrollDeleteDialogComponent {
    studentEnroll: IStudentEnroll;

    constructor(
        protected studentEnrollService: StudentEnrollService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.studentEnrollService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'studentEnrollListModification',
                content: 'Deleted an studentEnroll'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-student-enroll-delete-popup',
    template: ''
})
export class StudentEnrollDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ studentEnroll }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(StudentEnrollDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.studentEnroll = studentEnroll;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/student-enroll', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/student-enroll', { outlets: { popup: null } }]);
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

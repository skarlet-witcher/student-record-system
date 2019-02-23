import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStudentProgression } from 'app/shared/model/svcStudent/student-progression.model';
import { StudentProgressionService } from './student-progression.service';

@Component({
    selector: 'jhi-student-progression-delete-dialog',
    templateUrl: './student-progression-delete-dialog.component.html'
})
export class StudentProgressionDeleteDialogComponent {
    studentProgression: IStudentProgression;

    constructor(
        protected studentProgressionService: StudentProgressionService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.studentProgressionService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'studentProgressionListModification',
                content: 'Deleted an studentProgression'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-student-progression-delete-popup',
    template: ''
})
export class StudentProgressionDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ studentProgression }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(StudentProgressionDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.studentProgression = studentProgression;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/student-progression', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/student-progression', { outlets: { popup: null } }]);
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

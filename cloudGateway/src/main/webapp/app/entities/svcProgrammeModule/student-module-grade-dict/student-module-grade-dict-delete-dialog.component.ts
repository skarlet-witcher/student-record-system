import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStudentModuleGradeDict } from 'app/shared/model/svcProgrammeModule/student-module-grade-dict.model';
import { StudentModuleGradeDictService } from './student-module-grade-dict.service';

@Component({
    selector: 'jhi-student-module-grade-dict-delete-dialog',
    templateUrl: './student-module-grade-dict-delete-dialog.component.html'
})
export class StudentModuleGradeDictDeleteDialogComponent {
    studentModuleGradeDict: IStudentModuleGradeDict;

    constructor(
        protected studentModuleGradeDictService: StudentModuleGradeDictService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.studentModuleGradeDictService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'studentModuleGradeDictListModification',
                content: 'Deleted an studentModuleGradeDict'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-student-module-grade-dict-delete-popup',
    template: ''
})
export class StudentModuleGradeDictDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ studentModuleGradeDict }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(StudentModuleGradeDictDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.studentModuleGradeDict = studentModuleGradeDict;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/student-module-grade-dict', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/student-module-grade-dict', { outlets: { popup: null } }]);
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

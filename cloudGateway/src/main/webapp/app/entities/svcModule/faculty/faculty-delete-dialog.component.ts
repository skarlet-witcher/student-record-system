import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFaculty } from 'app/shared/model/svcModule/faculty.model';
import { FacultyService } from './faculty.service';

@Component({
    selector: 'jhi-faculty-delete-dialog',
    templateUrl: './faculty-delete-dialog.component.html'
})
export class FacultyDeleteDialogComponent {
    faculty: IFaculty;

    constructor(protected facultyService: FacultyService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.facultyService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'facultyListModification',
                content: 'Deleted an faculty'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-faculty-delete-popup',
    template: ''
})
export class FacultyDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ faculty }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(FacultyDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.faculty = faculty;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/faculty', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/faculty', { outlets: { popup: null } }]);
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

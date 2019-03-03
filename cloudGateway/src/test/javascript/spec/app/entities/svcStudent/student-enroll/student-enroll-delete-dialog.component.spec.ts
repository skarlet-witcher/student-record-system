/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CloudGatewayTestModule } from '../../../../test.module';
import { StudentEnrollDeleteDialogComponent } from 'app/entities/svcStudent/student-enroll/student-enroll-delete-dialog.component';
import { StudentEnrollService } from 'app/entities/svcStudent/student-enroll/student-enroll.service';

describe('Component Tests', () => {
    describe('StudentEnroll Management Delete Component', () => {
        let comp: StudentEnrollDeleteDialogComponent;
        let fixture: ComponentFixture<StudentEnrollDeleteDialogComponent>;
        let service: StudentEnrollService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CloudGatewayTestModule],
                declarations: [StudentEnrollDeleteDialogComponent]
            })
                .overrideTemplate(StudentEnrollDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(StudentEnrollDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StudentEnrollService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});

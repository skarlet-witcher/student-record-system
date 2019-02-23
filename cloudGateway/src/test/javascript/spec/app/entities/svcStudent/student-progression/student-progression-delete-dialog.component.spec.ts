/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CloudGatewayTestModule } from '../../../../test.module';
import { StudentProgressionDeleteDialogComponent } from 'app/entities/svcStudent/student-progression/student-progression-delete-dialog.component';
import { StudentProgressionService } from 'app/entities/svcStudent/student-progression/student-progression.service';

describe('Component Tests', () => {
    describe('StudentProgression Management Delete Component', () => {
        let comp: StudentProgressionDeleteDialogComponent;
        let fixture: ComponentFixture<StudentProgressionDeleteDialogComponent>;
        let service: StudentProgressionService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CloudGatewayTestModule],
                declarations: [StudentProgressionDeleteDialogComponent]
            })
                .overrideTemplate(StudentProgressionDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(StudentProgressionDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StudentProgressionService);
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

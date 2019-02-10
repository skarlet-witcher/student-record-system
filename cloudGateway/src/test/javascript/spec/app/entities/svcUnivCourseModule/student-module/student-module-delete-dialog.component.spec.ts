/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CloudGatewayTestModule } from '../../../../test.module';
import { StudentModuleDeleteDialogComponent } from 'app/entities/svcUnivCourseModule/student-module/student-module-delete-dialog.component';
import { StudentModuleService } from 'app/entities/svcUnivCourseModule/student-module/student-module.service';

describe('Component Tests', () => {
    describe('StudentModule Management Delete Component', () => {
        let comp: StudentModuleDeleteDialogComponent;
        let fixture: ComponentFixture<StudentModuleDeleteDialogComponent>;
        let service: StudentModuleService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CloudGatewayTestModule],
                declarations: [StudentModuleDeleteDialogComponent]
            })
                .overrideTemplate(StudentModuleDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(StudentModuleDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StudentModuleService);
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

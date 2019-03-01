/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CloudGatewayTestModule } from '../../../../test.module';
import { StudentModuleSelectionDeleteDialogComponent } from 'app/entities/svcModule/student-module-selection/student-module-selection-delete-dialog.component';
import { StudentModuleSelectionService } from 'app/entities/svcModule/student-module-selection/student-module-selection.service';

describe('Component Tests', () => {
    describe('StudentModuleSelection Management Delete Component', () => {
        let comp: StudentModuleSelectionDeleteDialogComponent;
        let fixture: ComponentFixture<StudentModuleSelectionDeleteDialogComponent>;
        let service: StudentModuleSelectionService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CloudGatewayTestModule],
                declarations: [StudentModuleSelectionDeleteDialogComponent]
            })
                .overrideTemplate(StudentModuleSelectionDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(StudentModuleSelectionDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StudentModuleSelectionService);
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

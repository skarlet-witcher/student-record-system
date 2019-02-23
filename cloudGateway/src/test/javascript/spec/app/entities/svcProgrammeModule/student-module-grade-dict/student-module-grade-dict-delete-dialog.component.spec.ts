/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CloudGatewayTestModule } from '../../../../test.module';
import { StudentModuleGradeDictDeleteDialogComponent } from 'app/entities/svcProgrammeModule/student-module-grade-dict/student-module-grade-dict-delete-dialog.component';
import { StudentModuleGradeDictService } from 'app/entities/svcProgrammeModule/student-module-grade-dict/student-module-grade-dict.service';

describe('Component Tests', () => {
    describe('StudentModuleGradeDict Management Delete Component', () => {
        let comp: StudentModuleGradeDictDeleteDialogComponent;
        let fixture: ComponentFixture<StudentModuleGradeDictDeleteDialogComponent>;
        let service: StudentModuleGradeDictService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CloudGatewayTestModule],
                declarations: [StudentModuleGradeDictDeleteDialogComponent]
            })
                .overrideTemplate(StudentModuleGradeDictDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(StudentModuleGradeDictDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StudentModuleGradeDictService);
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

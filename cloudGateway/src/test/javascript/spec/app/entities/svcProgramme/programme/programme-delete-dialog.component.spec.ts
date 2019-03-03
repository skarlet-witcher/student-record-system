/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CloudGatewayTestModule } from '../../../../test.module';
import { ProgrammeDeleteDialogComponent } from 'app/entities/svcProgramme/programme/programme-delete-dialog.component';
import { ProgrammeService } from 'app/entities/svcProgramme/programme/programme.service';

describe('Component Tests', () => {
    describe('Programme Management Delete Component', () => {
        let comp: ProgrammeDeleteDialogComponent;
        let fixture: ComponentFixture<ProgrammeDeleteDialogComponent>;
        let service: ProgrammeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CloudGatewayTestModule],
                declarations: [ProgrammeDeleteDialogComponent]
            })
                .overrideTemplate(ProgrammeDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ProgrammeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProgrammeService);
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

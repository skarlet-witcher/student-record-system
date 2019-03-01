/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CloudGatewayTestModule } from '../../../../test.module';
import { ProgrammePropDeleteDialogComponent } from 'app/entities/svcProgramme/programme-prop/programme-prop-delete-dialog.component';
import { ProgrammePropService } from 'app/entities/svcProgramme/programme-prop/programme-prop.service';

describe('Component Tests', () => {
    describe('ProgrammeProp Management Delete Component', () => {
        let comp: ProgrammePropDeleteDialogComponent;
        let fixture: ComponentFixture<ProgrammePropDeleteDialogComponent>;
        let service: ProgrammePropService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CloudGatewayTestModule],
                declarations: [ProgrammePropDeleteDialogComponent]
            })
                .overrideTemplate(ProgrammePropDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ProgrammePropDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProgrammePropService);
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

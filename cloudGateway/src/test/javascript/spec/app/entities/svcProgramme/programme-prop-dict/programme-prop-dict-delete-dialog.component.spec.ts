/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CloudGatewayTestModule } from '../../../../test.module';
import { ProgrammePropDictDeleteDialogComponent } from 'app/entities/svcProgramme/programme-prop-dict/programme-prop-dict-delete-dialog.component';
import { ProgrammePropDictService } from 'app/entities/svcProgramme/programme-prop-dict/programme-prop-dict.service';

describe('Component Tests', () => {
    describe('ProgrammePropDict Management Delete Component', () => {
        let comp: ProgrammePropDictDeleteDialogComponent;
        let fixture: ComponentFixture<ProgrammePropDictDeleteDialogComponent>;
        let service: ProgrammePropDictService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CloudGatewayTestModule],
                declarations: [ProgrammePropDictDeleteDialogComponent]
            })
                .overrideTemplate(ProgrammePropDictDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ProgrammePropDictDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProgrammePropDictService);
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

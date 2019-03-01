/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CloudGatewayTestModule } from '../../../../test.module';
import { ProgrammePropUpdateComponent } from 'app/entities/svcModule/programme-prop/programme-prop-update.component';
import { ProgrammePropService } from 'app/entities/svcModule/programme-prop/programme-prop.service';
import { ProgrammeProp } from 'app/shared/model/svcModule/programme-prop.model';

describe('Component Tests', () => {
    describe('ProgrammeProp Management Update Component', () => {
        let comp: ProgrammePropUpdateComponent;
        let fixture: ComponentFixture<ProgrammePropUpdateComponent>;
        let service: ProgrammePropService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CloudGatewayTestModule],
                declarations: [ProgrammePropUpdateComponent]
            })
                .overrideTemplate(ProgrammePropUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ProgrammePropUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProgrammePropService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new ProgrammeProp(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.programmeProp = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new ProgrammeProp();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.programmeProp = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});

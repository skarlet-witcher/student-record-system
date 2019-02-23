/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CloudGatewayTestModule } from '../../../../test.module';
import { ProgrammePropDictUpdateComponent } from 'app/entities/svcProgramme/programme-prop-dict/programme-prop-dict-update.component';
import { ProgrammePropDictService } from 'app/entities/svcProgramme/programme-prop-dict/programme-prop-dict.service';
import { ProgrammePropDict } from 'app/shared/model/svcProgramme/programme-prop-dict.model';

describe('Component Tests', () => {
    describe('ProgrammePropDict Management Update Component', () => {
        let comp: ProgrammePropDictUpdateComponent;
        let fixture: ComponentFixture<ProgrammePropDictUpdateComponent>;
        let service: ProgrammePropDictService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CloudGatewayTestModule],
                declarations: [ProgrammePropDictUpdateComponent]
            })
                .overrideTemplate(ProgrammePropDictUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ProgrammePropDictUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProgrammePropDictService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new ProgrammePropDict(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.programmePropDict = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new ProgrammePropDict();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.programmePropDict = entity;
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

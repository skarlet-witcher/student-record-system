/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CloudGatewayTestModule } from '../../../../test.module';
import { ProgrammeUpdateComponent } from 'app/entities/svcProgramme/programme/programme-update.component';
import { ProgrammeService } from 'app/entities/svcProgramme/programme/programme.service';
import { Programme } from 'app/shared/model/svcProgramme/programme.model';

describe('Component Tests', () => {
    describe('Programme Management Update Component', () => {
        let comp: ProgrammeUpdateComponent;
        let fixture: ComponentFixture<ProgrammeUpdateComponent>;
        let service: ProgrammeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CloudGatewayTestModule],
                declarations: [ProgrammeUpdateComponent]
            })
                .overrideTemplate(ProgrammeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ProgrammeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProgrammeService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Programme(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.programme = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Programme();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.programme = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});

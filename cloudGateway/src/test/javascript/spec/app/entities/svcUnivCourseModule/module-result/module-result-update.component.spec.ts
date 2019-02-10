/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CloudGatewayTestModule } from '../../../../test.module';
import { ModuleResultUpdateComponent } from 'app/entities/svcUnivCourseModule/module-result/module-result-update.component';
import { ModuleResultService } from 'app/entities/svcUnivCourseModule/module-result/module-result.service';
import { ModuleResult } from 'app/shared/model/svcUnivCourseModule/module-result.model';

describe('Component Tests', () => {
    describe('ModuleResult Management Update Component', () => {
        let comp: ModuleResultUpdateComponent;
        let fixture: ComponentFixture<ModuleResultUpdateComponent>;
        let service: ModuleResultService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CloudGatewayTestModule],
                declarations: [ModuleResultUpdateComponent]
            })
                .overrideTemplate(ModuleResultUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ModuleResultUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ModuleResultService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new ModuleResult(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.moduleResult = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new ModuleResult();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.moduleResult = entity;
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

/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CloudGatewayTestModule } from '../../../../test.module';
import { ModuleGradeUpdateComponent } from 'app/entities/svcModule/module-grade/module-grade-update.component';
import { ModuleGradeService } from 'app/entities/svcModule/module-grade/module-grade.service';
import { ModuleGrade } from 'app/shared/model/svcModule/module-grade.model';

describe('Component Tests', () => {
    describe('ModuleGrade Management Update Component', () => {
        let comp: ModuleGradeUpdateComponent;
        let fixture: ComponentFixture<ModuleGradeUpdateComponent>;
        let service: ModuleGradeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CloudGatewayTestModule],
                declarations: [ModuleGradeUpdateComponent]
            })
                .overrideTemplate(ModuleGradeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ModuleGradeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ModuleGradeService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new ModuleGrade(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.moduleGrade = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new ModuleGrade();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.moduleGrade = entity;
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

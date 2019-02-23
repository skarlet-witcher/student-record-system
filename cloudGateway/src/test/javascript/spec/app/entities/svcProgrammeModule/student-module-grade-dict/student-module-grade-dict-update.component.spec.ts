/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CloudGatewayTestModule } from '../../../../test.module';
import { StudentModuleGradeDictUpdateComponent } from 'app/entities/svcProgrammeModule/student-module-grade-dict/student-module-grade-dict-update.component';
import { StudentModuleGradeDictService } from 'app/entities/svcProgrammeModule/student-module-grade-dict/student-module-grade-dict.service';
import { StudentModuleGradeDict } from 'app/shared/model/svcProgrammeModule/student-module-grade-dict.model';

describe('Component Tests', () => {
    describe('StudentModuleGradeDict Management Update Component', () => {
        let comp: StudentModuleGradeDictUpdateComponent;
        let fixture: ComponentFixture<StudentModuleGradeDictUpdateComponent>;
        let service: StudentModuleGradeDictService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CloudGatewayTestModule],
                declarations: [StudentModuleGradeDictUpdateComponent]
            })
                .overrideTemplate(StudentModuleGradeDictUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(StudentModuleGradeDictUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StudentModuleGradeDictService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new StudentModuleGradeDict(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.studentModuleGradeDict = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new StudentModuleGradeDict();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.studentModuleGradeDict = entity;
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

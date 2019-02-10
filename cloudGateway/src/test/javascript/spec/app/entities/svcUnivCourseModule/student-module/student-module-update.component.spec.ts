/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CloudGatewayTestModule } from '../../../../test.module';
import { StudentModuleUpdateComponent } from 'app/entities/svcUnivCourseModule/student-module/student-module-update.component';
import { StudentModuleService } from 'app/entities/svcUnivCourseModule/student-module/student-module.service';
import { StudentModule } from 'app/shared/model/svcUnivCourseModule/student-module.model';

describe('Component Tests', () => {
    describe('StudentModule Management Update Component', () => {
        let comp: StudentModuleUpdateComponent;
        let fixture: ComponentFixture<StudentModuleUpdateComponent>;
        let service: StudentModuleService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CloudGatewayTestModule],
                declarations: [StudentModuleUpdateComponent]
            })
                .overrideTemplate(StudentModuleUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(StudentModuleUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StudentModuleService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new StudentModule(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.studentModule = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new StudentModule();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.studentModule = entity;
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

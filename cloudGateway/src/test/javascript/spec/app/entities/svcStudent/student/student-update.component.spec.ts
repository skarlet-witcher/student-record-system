/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CloudGatewayTestModule } from '../../../../test.module';
import { StudentUpdateComponent } from 'app/entities/svcStudent/student/student-update.component';
import { StudentService } from 'app/entities/svcStudent/student/student.service';
import { Student } from 'app/shared/model/svcStudent/student.model';

describe('Component Tests', () => {
    describe('Student Management Update Component', () => {
        let comp: StudentUpdateComponent;
        let fixture: ComponentFixture<StudentUpdateComponent>;
        let service: StudentService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CloudGatewayTestModule],
                declarations: [StudentUpdateComponent]
            })
                .overrideTemplate(StudentUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(StudentUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StudentService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Student(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.student = entity;
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
                    const entity = new Student();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.student = entity;
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

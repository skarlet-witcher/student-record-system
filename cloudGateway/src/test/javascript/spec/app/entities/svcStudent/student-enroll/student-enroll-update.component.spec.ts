/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CloudGatewayTestModule } from '../../../../test.module';
import { StudentEnrollUpdateComponent } from 'app/entities/svcStudent/student-enroll/student-enroll-update.component';
import { StudentEnrollService } from 'app/entities/svcStudent/student-enroll/student-enroll.service';
import { StudentEnroll } from 'app/shared/model/svcStudent/student-enroll.model';

describe('Component Tests', () => {
    describe('StudentEnroll Management Update Component', () => {
        let comp: StudentEnrollUpdateComponent;
        let fixture: ComponentFixture<StudentEnrollUpdateComponent>;
        let service: StudentEnrollService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CloudGatewayTestModule],
                declarations: [StudentEnrollUpdateComponent]
            })
                .overrideTemplate(StudentEnrollUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(StudentEnrollUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StudentEnrollService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new StudentEnroll(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.studentEnroll = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new StudentEnroll();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.studentEnroll = entity;
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

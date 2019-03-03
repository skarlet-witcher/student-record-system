/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CloudGatewayTestModule } from '../../../../test.module';
import { StudentProgressionUpdateComponent } from 'app/entities/svcStudent/student-progression/student-progression-update.component';
import { StudentProgressionService } from 'app/entities/svcStudent/student-progression/student-progression.service';
import { StudentProgression } from 'app/shared/model/svcStudent/student-progression.model';

describe('Component Tests', () => {
    describe('StudentProgression Management Update Component', () => {
        let comp: StudentProgressionUpdateComponent;
        let fixture: ComponentFixture<StudentProgressionUpdateComponent>;
        let service: StudentProgressionService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CloudGatewayTestModule],
                declarations: [StudentProgressionUpdateComponent]
            })
                .overrideTemplate(StudentProgressionUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(StudentProgressionUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StudentProgressionService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new StudentProgression(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.studentProgression = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new StudentProgression();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.studentProgression = entity;
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

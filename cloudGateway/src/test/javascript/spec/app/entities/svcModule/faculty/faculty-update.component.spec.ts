/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CloudGatewayTestModule } from '../../../../test.module';
import { FacultyUpdateComponent } from 'app/entities/svcModule/faculty/faculty-update.component';
import { FacultyService } from 'app/entities/svcModule/faculty/faculty.service';
import { Faculty } from 'app/shared/model/svcModule/faculty.model';

describe('Component Tests', () => {
    describe('Faculty Management Update Component', () => {
        let comp: FacultyUpdateComponent;
        let fixture: ComponentFixture<FacultyUpdateComponent>;
        let service: FacultyService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CloudGatewayTestModule],
                declarations: [FacultyUpdateComponent]
            })
                .overrideTemplate(FacultyUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(FacultyUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FacultyService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Faculty(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.faculty = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Faculty();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.faculty = entity;
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

/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CloudGatewayTestModule } from '../../../../test.module';
import { StudentModuleSelectionUpdateComponent } from 'app/entities/svcModule/student-module-selection/student-module-selection-update.component';
import { StudentModuleSelectionService } from 'app/entities/svcModule/student-module-selection/student-module-selection.service';
import { StudentModuleSelection } from 'app/shared/model/svcModule/student-module-selection.model';

describe('Component Tests', () => {
    describe('StudentModuleSelection Management Update Component', () => {
        let comp: StudentModuleSelectionUpdateComponent;
        let fixture: ComponentFixture<StudentModuleSelectionUpdateComponent>;
        let service: StudentModuleSelectionService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CloudGatewayTestModule],
                declarations: [StudentModuleSelectionUpdateComponent]
            })
                .overrideTemplate(StudentModuleSelectionUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(StudentModuleSelectionUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StudentModuleSelectionService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new StudentModuleSelection(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.studentModuleSelection = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new StudentModuleSelection();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.studentModuleSelection = entity;
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

/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CloudGatewayTestModule } from '../../../../test.module';
import { StudentModuleSelectionDetailComponent } from 'app/entities/svcProgrammeModule/student-module-selection/student-module-selection-detail.component';
import { StudentModuleSelection } from 'app/shared/model/svcProgrammeModule/student-module-selection.model';

describe('Component Tests', () => {
    describe('StudentModuleSelection Management Detail Component', () => {
        let comp: StudentModuleSelectionDetailComponent;
        let fixture: ComponentFixture<StudentModuleSelectionDetailComponent>;
        const route = ({ data: of({ studentModuleSelection: new StudentModuleSelection(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CloudGatewayTestModule],
                declarations: [StudentModuleSelectionDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(StudentModuleSelectionDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(StudentModuleSelectionDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.studentModuleSelection).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

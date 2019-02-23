/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CloudGatewayTestModule } from '../../../../test.module';
import { StudentModuleGradeDictDetailComponent } from 'app/entities/svcProgrammeModule/student-module-grade-dict/student-module-grade-dict-detail.component';
import { StudentModuleGradeDict } from 'app/shared/model/svcProgrammeModule/student-module-grade-dict.model';

describe('Component Tests', () => {
    describe('StudentModuleGradeDict Management Detail Component', () => {
        let comp: StudentModuleGradeDictDetailComponent;
        let fixture: ComponentFixture<StudentModuleGradeDictDetailComponent>;
        const route = ({ data: of({ studentModuleGradeDict: new StudentModuleGradeDict(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CloudGatewayTestModule],
                declarations: [StudentModuleGradeDictDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(StudentModuleGradeDictDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(StudentModuleGradeDictDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.studentModuleGradeDict).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

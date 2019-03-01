/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CloudGatewayTestModule } from '../../../../test.module';
import { ModuleGradeDetailComponent } from 'app/entities/svcProgrammeModule/module-grade/module-grade-detail.component';
import { ModuleGrade } from 'app/shared/model/svcProgrammeModule/module-grade.model';

describe('Component Tests', () => {
    describe('ModuleGrade Management Detail Component', () => {
        let comp: ModuleGradeDetailComponent;
        let fixture: ComponentFixture<ModuleGradeDetailComponent>;
        const route = ({ data: of({ moduleGrade: new ModuleGrade(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CloudGatewayTestModule],
                declarations: [ModuleGradeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ModuleGradeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ModuleGradeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.moduleGrade).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

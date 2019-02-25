/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CloudGatewayTestModule } from '../../../../test.module';
import { StudentProgressionDetailComponent } from 'app/entities/svcStudent/student-progression/student-progression-detail.component';
import { StudentProgression } from 'app/shared/model/svcStudent/student-progression.model';

describe('Component Tests', () => {
    describe('StudentProgression Management Detail Component', () => {
        let comp: StudentProgressionDetailComponent;
        let fixture: ComponentFixture<StudentProgressionDetailComponent>;
        const route = ({ data: of({ studentProgression: new StudentProgression(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CloudGatewayTestModule],
                declarations: [StudentProgressionDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(StudentProgressionDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(StudentProgressionDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.studentProgression).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CloudGatewayTestModule } from '../../../../test.module';
import { StudentEnrollDetailComponent } from 'app/entities/svcStudent/student-enroll/student-enroll-detail.component';
import { StudentEnroll } from 'app/shared/model/svcStudent/student-enroll.model';

describe('Component Tests', () => {
    describe('StudentEnroll Management Detail Component', () => {
        let comp: StudentEnrollDetailComponent;
        let fixture: ComponentFixture<StudentEnrollDetailComponent>;
        const route = ({ data: of({ studentEnroll: new StudentEnroll(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CloudGatewayTestModule],
                declarations: [StudentEnrollDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(StudentEnrollDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(StudentEnrollDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.studentEnroll).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CloudGatewayTestModule } from '../../../test.module';
import { StudentModuleDetailComponent } from 'app/entities/student-module/student-module-detail.component';
import { StudentModule } from 'app/shared/model/student-module.model';

describe('Component Tests', () => {
    describe('StudentModule Management Detail Component', () => {
        let comp: StudentModuleDetailComponent;
        let fixture: ComponentFixture<StudentModuleDetailComponent>;
        const route = ({ data: of({ studentModule: new StudentModule(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CloudGatewayTestModule],
                declarations: [StudentModuleDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(StudentModuleDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(StudentModuleDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.studentModule).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

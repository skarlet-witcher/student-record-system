/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CloudGatewayTestModule } from '../../../../test.module';
import { FacultyDetailComponent } from 'app/entities/svcModule/faculty/faculty-detail.component';
import { Faculty } from 'app/shared/model/svcModule/faculty.model';

describe('Component Tests', () => {
    describe('Faculty Management Detail Component', () => {
        let comp: FacultyDetailComponent;
        let fixture: ComponentFixture<FacultyDetailComponent>;
        const route = ({ data: of({ faculty: new Faculty(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CloudGatewayTestModule],
                declarations: [FacultyDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(FacultyDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(FacultyDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.faculty).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CloudGatewayTestModule } from '../../../../test.module';
import { ProgrammeDetailComponent } from 'app/entities/svcProgrammeModule/programme/programme-detail.component';
import { Programme } from 'app/shared/model/svcProgrammeModule/programme.model';

describe('Component Tests', () => {
    describe('Programme Management Detail Component', () => {
        let comp: ProgrammeDetailComponent;
        let fixture: ComponentFixture<ProgrammeDetailComponent>;
        const route = ({ data: of({ programme: new Programme(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CloudGatewayTestModule],
                declarations: [ProgrammeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ProgrammeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ProgrammeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.programme).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

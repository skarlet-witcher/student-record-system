/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CloudGatewayTestModule } from '../../../../test.module';
import { ProgrammePropDetailComponent } from 'app/entities/svcProgrammeModule/programme-prop/programme-prop-detail.component';
import { ProgrammeProp } from 'app/shared/model/svcProgrammeModule/programme-prop.model';

describe('Component Tests', () => {
    describe('ProgrammeProp Management Detail Component', () => {
        let comp: ProgrammePropDetailComponent;
        let fixture: ComponentFixture<ProgrammePropDetailComponent>;
        const route = ({ data: of({ programmeProp: new ProgrammeProp(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CloudGatewayTestModule],
                declarations: [ProgrammePropDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ProgrammePropDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ProgrammePropDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.programmeProp).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CloudGatewayTestModule } from '../../../../test.module';
import { ProgrammePropDictDetailComponent } from 'app/entities/svcProgramme/programme-prop-dict/programme-prop-dict-detail.component';
import { ProgrammePropDict } from 'app/shared/model/svcProgramme/programme-prop-dict.model';

describe('Component Tests', () => {
    describe('ProgrammePropDict Management Detail Component', () => {
        let comp: ProgrammePropDictDetailComponent;
        let fixture: ComponentFixture<ProgrammePropDictDetailComponent>;
        const route = ({ data: of({ programmePropDict: new ProgrammePropDict(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CloudGatewayTestModule],
                declarations: [ProgrammePropDictDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ProgrammePropDictDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ProgrammePropDictDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.programmePropDict).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

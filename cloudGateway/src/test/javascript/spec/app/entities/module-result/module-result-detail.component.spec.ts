/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CloudGatewayTestModule } from '../../../test.module';
import { ModuleResultDetailComponent } from 'app/entities/module-result/module-result-detail.component';
import { ModuleResult } from 'app/shared/model/module-result.model';

describe('Component Tests', () => {
    describe('ModuleResult Management Detail Component', () => {
        let comp: ModuleResultDetailComponent;
        let fixture: ComponentFixture<ModuleResultDetailComponent>;
        const route = ({ data: of({ moduleResult: new ModuleResult(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CloudGatewayTestModule],
                declarations: [ModuleResultDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ModuleResultDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ModuleResultDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.moduleResult).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

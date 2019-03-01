import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CloudGatewaySharedModule } from 'app/shared';
import {
    ProgrammePropComponent,
    ProgrammePropDetailComponent,
    ProgrammePropUpdateComponent,
    ProgrammePropDeletePopupComponent,
    ProgrammePropDeleteDialogComponent,
    programmePropRoute,
    programmePropPopupRoute
} from './';

const ENTITY_STATES = [...programmePropRoute, ...programmePropPopupRoute];

@NgModule({
    imports: [CloudGatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ProgrammePropComponent,
        ProgrammePropDetailComponent,
        ProgrammePropUpdateComponent,
        ProgrammePropDeleteDialogComponent,
        ProgrammePropDeletePopupComponent
    ],
    entryComponents: [
        ProgrammePropComponent,
        ProgrammePropUpdateComponent,
        ProgrammePropDeleteDialogComponent,
        ProgrammePropDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SvcProgrammeProgrammePropModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}

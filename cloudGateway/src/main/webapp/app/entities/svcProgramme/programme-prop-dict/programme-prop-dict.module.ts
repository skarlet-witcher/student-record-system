import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CloudGatewaySharedModule } from 'app/shared';
import {
    ProgrammePropDictComponent,
    ProgrammePropDictDetailComponent,
    ProgrammePropDictUpdateComponent,
    ProgrammePropDictDeletePopupComponent,
    ProgrammePropDictDeleteDialogComponent,
    programmePropDictRoute,
    programmePropDictPopupRoute
} from './';

const ENTITY_STATES = [...programmePropDictRoute, ...programmePropDictPopupRoute];

@NgModule({
    imports: [CloudGatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ProgrammePropDictComponent,
        ProgrammePropDictDetailComponent,
        ProgrammePropDictUpdateComponent,
        ProgrammePropDictDeleteDialogComponent,
        ProgrammePropDictDeletePopupComponent
    ],
    entryComponents: [
        ProgrammePropDictComponent,
        ProgrammePropDictUpdateComponent,
        ProgrammePropDictDeleteDialogComponent,
        ProgrammePropDictDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SvcProgrammeProgrammePropDictModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}

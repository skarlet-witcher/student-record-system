import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CloudGatewaySharedModule } from 'app/shared';
import {
    ProgrammeComponent,
    ProgrammeDetailComponent,
    ProgrammeUpdateComponent,
    ProgrammeDeletePopupComponent,
    ProgrammeDeleteDialogComponent,
    programmeRoute,
    programmePopupRoute
} from './';

const ENTITY_STATES = [...programmeRoute, ...programmePopupRoute];

@NgModule({
    imports: [CloudGatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ProgrammeComponent,
        ProgrammeDetailComponent,
        ProgrammeUpdateComponent,
        ProgrammeDeleteDialogComponent,
        ProgrammeDeletePopupComponent
    ],
    entryComponents: [ProgrammeComponent, ProgrammeUpdateComponent, ProgrammeDeleteDialogComponent, ProgrammeDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SvcProgrammeProgrammeModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}

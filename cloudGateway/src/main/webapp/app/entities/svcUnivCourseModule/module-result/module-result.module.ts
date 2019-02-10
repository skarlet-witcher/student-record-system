import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CloudGatewaySharedModule } from 'app/shared';
import {
    ModuleResultComponent,
    ModuleResultDetailComponent,
    ModuleResultUpdateComponent,
    ModuleResultDeletePopupComponent,
    ModuleResultDeleteDialogComponent,
    moduleResultRoute,
    moduleResultPopupRoute
} from './';

const ENTITY_STATES = [...moduleResultRoute, ...moduleResultPopupRoute];

@NgModule({
    imports: [CloudGatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ModuleResultComponent,
        ModuleResultDetailComponent,
        ModuleResultUpdateComponent,
        ModuleResultDeleteDialogComponent,
        ModuleResultDeletePopupComponent
    ],
    entryComponents: [
        ModuleResultComponent,
        ModuleResultUpdateComponent,
        ModuleResultDeleteDialogComponent,
        ModuleResultDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SvcUnivCourseModuleModuleResultModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}

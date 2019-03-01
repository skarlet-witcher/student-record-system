import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CloudGatewaySharedModule } from 'app/shared';
import {
    ModuleGradeComponent,
    ModuleGradeDetailComponent,
    ModuleGradeUpdateComponent,
    ModuleGradeDeletePopupComponent,
    ModuleGradeDeleteDialogComponent,
    moduleGradeRoute,
    moduleGradePopupRoute
} from './';

const ENTITY_STATES = [...moduleGradeRoute, ...moduleGradePopupRoute];

@NgModule({
    imports: [CloudGatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ModuleGradeComponent,
        ModuleGradeDetailComponent,
        ModuleGradeUpdateComponent,
        ModuleGradeDeleteDialogComponent,
        ModuleGradeDeletePopupComponent
    ],
    entryComponents: [ModuleGradeComponent, ModuleGradeUpdateComponent, ModuleGradeDeleteDialogComponent, ModuleGradeDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SvcProgrammeModuleModuleGradeModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}

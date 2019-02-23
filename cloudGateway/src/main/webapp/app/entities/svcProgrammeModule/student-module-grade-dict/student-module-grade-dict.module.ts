import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CloudGatewaySharedModule } from 'app/shared';
import {
    StudentModuleGradeDictComponent,
    StudentModuleGradeDictDetailComponent,
    StudentModuleGradeDictUpdateComponent,
    StudentModuleGradeDictDeletePopupComponent,
    StudentModuleGradeDictDeleteDialogComponent,
    studentModuleGradeDictRoute,
    studentModuleGradeDictPopupRoute
} from './';

const ENTITY_STATES = [...studentModuleGradeDictRoute, ...studentModuleGradeDictPopupRoute];

@NgModule({
    imports: [CloudGatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        StudentModuleGradeDictComponent,
        StudentModuleGradeDictDetailComponent,
        StudentModuleGradeDictUpdateComponent,
        StudentModuleGradeDictDeleteDialogComponent,
        StudentModuleGradeDictDeletePopupComponent
    ],
    entryComponents: [
        StudentModuleGradeDictComponent,
        StudentModuleGradeDictUpdateComponent,
        StudentModuleGradeDictDeleteDialogComponent,
        StudentModuleGradeDictDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SvcProgrammeModuleStudentModuleGradeDictModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}

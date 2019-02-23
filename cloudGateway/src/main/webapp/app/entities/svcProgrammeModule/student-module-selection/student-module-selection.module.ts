import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CloudGatewaySharedModule } from 'app/shared';
import {
    StudentModuleSelectionComponent,
    StudentModuleSelectionDetailComponent,
    StudentModuleSelectionUpdateComponent,
    StudentModuleSelectionDeletePopupComponent,
    StudentModuleSelectionDeleteDialogComponent,
    studentModuleSelectionRoute,
    studentModuleSelectionPopupRoute
} from './';

const ENTITY_STATES = [...studentModuleSelectionRoute, ...studentModuleSelectionPopupRoute];

@NgModule({
    imports: [CloudGatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        StudentModuleSelectionComponent,
        StudentModuleSelectionDetailComponent,
        StudentModuleSelectionUpdateComponent,
        StudentModuleSelectionDeleteDialogComponent,
        StudentModuleSelectionDeletePopupComponent
    ],
    entryComponents: [
        StudentModuleSelectionComponent,
        StudentModuleSelectionUpdateComponent,
        StudentModuleSelectionDeleteDialogComponent,
        StudentModuleSelectionDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SvcProgrammeModuleStudentModuleSelectionModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}

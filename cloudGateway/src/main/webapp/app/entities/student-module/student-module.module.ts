import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CloudGatewaySharedModule } from 'app/shared';
import {
    StudentModuleComponent,
    StudentModuleDetailComponent,
    StudentModuleUpdateComponent,
    StudentModuleDeletePopupComponent,
    StudentModuleDeleteDialogComponent,
    studentModuleRoute,
    studentModulePopupRoute
} from './';

const ENTITY_STATES = [...studentModuleRoute, ...studentModulePopupRoute];

@NgModule({
    imports: [CloudGatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        StudentModuleComponent,
        StudentModuleDetailComponent,
        StudentModuleUpdateComponent,
        StudentModuleDeleteDialogComponent,
        StudentModuleDeletePopupComponent
    ],
    entryComponents: [
        StudentModuleComponent,
        StudentModuleUpdateComponent,
        StudentModuleDeleteDialogComponent,
        StudentModuleDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CloudGatewayStudentModuleModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}

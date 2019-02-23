import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CloudGatewaySharedModule } from 'app/shared';
import {
    StudentEnrollComponent,
    StudentEnrollDetailComponent,
    StudentEnrollUpdateComponent,
    StudentEnrollDeletePopupComponent,
    StudentEnrollDeleteDialogComponent,
    studentEnrollRoute,
    studentEnrollPopupRoute
} from './';

const ENTITY_STATES = [...studentEnrollRoute, ...studentEnrollPopupRoute];

@NgModule({
    imports: [CloudGatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        StudentEnrollComponent,
        StudentEnrollDetailComponent,
        StudentEnrollUpdateComponent,
        StudentEnrollDeleteDialogComponent,
        StudentEnrollDeletePopupComponent
    ],
    entryComponents: [
        StudentEnrollComponent,
        StudentEnrollUpdateComponent,
        StudentEnrollDeleteDialogComponent,
        StudentEnrollDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SvcStudentStudentEnrollModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}

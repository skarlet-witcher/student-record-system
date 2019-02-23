import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CloudGatewaySharedModule } from 'app/shared';
import {
    StudentProgressionComponent,
    StudentProgressionDetailComponent,
    StudentProgressionUpdateComponent,
    StudentProgressionDeletePopupComponent,
    StudentProgressionDeleteDialogComponent,
    studentProgressionRoute,
    studentProgressionPopupRoute
} from './';

const ENTITY_STATES = [...studentProgressionRoute, ...studentProgressionPopupRoute];

@NgModule({
    imports: [CloudGatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        StudentProgressionComponent,
        StudentProgressionDetailComponent,
        StudentProgressionUpdateComponent,
        StudentProgressionDeleteDialogComponent,
        StudentProgressionDeletePopupComponent
    ],
    entryComponents: [
        StudentProgressionComponent,
        StudentProgressionUpdateComponent,
        StudentProgressionDeleteDialogComponent,
        StudentProgressionDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SvcStudentStudentProgressionModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}

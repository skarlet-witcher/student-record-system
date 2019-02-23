import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CloudGatewaySharedModule } from 'app/shared';
import {
    FacultyComponent,
    FacultyDetailComponent,
    FacultyUpdateComponent,
    FacultyDeletePopupComponent,
    FacultyDeleteDialogComponent,
    facultyRoute,
    facultyPopupRoute
} from './';

const ENTITY_STATES = [...facultyRoute, ...facultyPopupRoute];

@NgModule({
    imports: [CloudGatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        FacultyComponent,
        FacultyDetailComponent,
        FacultyUpdateComponent,
        FacultyDeleteDialogComponent,
        FacultyDeletePopupComponent
    ],
    entryComponents: [FacultyComponent, FacultyUpdateComponent, FacultyDeleteDialogComponent, FacultyDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SvcProgrammeFacultyModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}

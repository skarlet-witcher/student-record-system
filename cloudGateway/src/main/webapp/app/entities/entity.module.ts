import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'student',
                loadChildren: './svcStudent/student/student.module#SvcStudentStudentModule'
            },
            {
                path: 'module',
                loadChildren: './svcUnivCourseModule/module/module.module#SvcUnivCourseModuleModuleModule'
            },
            {
                path: 'student-module',
                loadChildren: './svcUnivCourseModule/student-module/student-module.module#SvcUnivCourseModuleStudentModuleModule'
            },
            {
                path: 'module-result',
                loadChildren: './svcUnivCourseModule/module-result/module-result.module#SvcUnivCourseModuleModuleResultModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CloudGatewayEntityModule {}

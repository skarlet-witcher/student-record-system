import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'student',
                loadChildren: './student/student.module#CloudGatewayStudentModule'
            },
            {
                path: 'module',
                loadChildren: './module/module.module#CloudGatewayModuleModule'
            },
            {
                path: 'student-module',
                loadChildren: './student-module/student-module.module#CloudGatewayStudentModuleModule'
            },
            {
                path: 'module-result',
                loadChildren: './module-result/module-result.module#CloudGatewayModuleResultModule'
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

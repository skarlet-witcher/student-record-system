import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'faculty',
                loadChildren: './svcModule/faculty/faculty.module#SvcModuleFacultyModule'
            },
            {
                path: 'department',
                loadChildren: './svcModule/department/department.module#SvcModuleDepartmentModule'
            },
            {
                path: 'programme',
                loadChildren: './svcModule/programme/programme.module#SvcModuleProgrammeModule'
            },
            {
                path: 'programme-prop-dict',
                loadChildren: './svcProgramme/programme-prop-dict/programme-prop-dict.module#SvcProgrammeProgrammePropDictModule'
            },
            {
                path: 'module',
                loadChildren: './svcModule/module/module.module#SvcModuleModuleModule'
            },
            {
                path: 'student-module-selection',
                loadChildren: './svcModule/student-module-selection/student-module-selection.module#SvcModuleStudentModuleSelectionModule'
            },
            {
                path: 'student-module-grade-dict',
                loadChildren:
                    './svcProgrammeModule/student-module-grade-dict/student-module-grade-dict.module#SvcProgrammeModuleStudentModuleGradeDictModule'
            },
            {
                path: 'student',
                loadChildren: './svcStudent/student/student.module#SvcStudentStudentModule'
            },
            {
                path: 'student-enroll',
                loadChildren: './svcStudent/student-enroll/student-enroll.module#SvcStudentStudentEnrollModule'
            },
            {
                path: 'student-progression',
                loadChildren: './svcStudent/student-progression/student-progression.module#SvcStudentStudentProgressionModule'
            },
            {
                path: 'programme-prop',
                loadChildren: './svcModule/programme-prop/programme-prop.module#SvcModuleProgrammePropModule'
            },
            {
                path: 'module-grade',
                loadChildren: './svcModule/module-grade/module-grade.module#SvcModuleModuleGradeModule'
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

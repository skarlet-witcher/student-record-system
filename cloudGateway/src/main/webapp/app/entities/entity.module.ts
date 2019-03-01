import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'faculty',
                loadChildren: './svcProgramme/faculty/faculty.module#SvcProgrammeFacultyModule'
            },
            {
                path: 'department',
                loadChildren: './svcProgramme/department/department.module#SvcProgrammeDepartmentModule'
            },
            {
                path: 'programme',
                loadChildren: './svcProgramme/programme/programme.module#SvcProgrammeProgrammeModule'
            },
            {
                path: 'programme-prop-dict',
                loadChildren: './svcProgramme/programme-prop-dict/programme-prop-dict.module#SvcProgrammeProgrammePropDictModule'
            },
            {
                path: 'module',
                loadChildren: './svcProgrammeModule/module/module.module#SvcProgrammeModuleModuleModule'
            },
            {
                path: 'student-module-selection',
                loadChildren:
                    './svcProgrammeModule/student-module-selection/student-module-selection.module#SvcProgrammeModuleStudentModuleSelectionModule'
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
                loadChildren: './svcProgramme/programme-prop/programme-prop.module#SvcProgrammeProgrammePropModule'
            },
            {
                path: 'module-grade',
                loadChildren: './svcProgrammeModule/module-grade/module-grade.module#SvcProgrammeModuleModuleGradeModule'
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

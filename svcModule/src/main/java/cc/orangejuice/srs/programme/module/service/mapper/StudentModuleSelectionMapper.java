package cc.orangejuice.srs.programme.module.service.mapper;

import cc.orangejuice.srs.programme.module.domain.*;
import cc.orangejuice.srs.programme.module.service.dto.StudentModuleSelectionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity StudentModuleSelection and its DTO StudentModuleSelectionDTO.
 */
@Mapper(componentModel = "spring", uses = {ModuleMapper.class, ModuleGradeMapper.class})
public interface StudentModuleSelectionMapper extends EntityMapper<StudentModuleSelectionDTO, StudentModuleSelection> {

    @Mapping(source = "module.id", target = "moduleId")
    @Mapping(source = "module.name", target = "moduleName")
    @Mapping(source = "studentModuleGradeType.id", target = "studentModuleGradeTypeId")
    @Mapping(source = "studentModuleGradeType.name", target = "studentModuleGradeTypeName")
    StudentModuleSelectionDTO toDto(StudentModuleSelection studentModuleSelection);

    @Mapping(source = "moduleId", target = "module")
    @Mapping(source = "studentModuleGradeTypeId", target = "studentModuleGradeType")
    StudentModuleSelection toEntity(StudentModuleSelectionDTO studentModuleSelectionDTO);

    default StudentModuleSelection fromId(Long id) {
        if (id == null) {
            return null;
        }
        StudentModuleSelection studentModuleSelection = new StudentModuleSelection();
        studentModuleSelection.setId(id);
        return studentModuleSelection;
    }
}

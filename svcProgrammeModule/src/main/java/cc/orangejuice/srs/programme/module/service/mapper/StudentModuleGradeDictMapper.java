package cc.orangejuice.srs.programme.module.service.mapper;

import cc.orangejuice.srs.programme.module.domain.*;
import cc.orangejuice.srs.programme.module.service.dto.StudentModuleGradeDictDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity StudentModuleGradeDict and its DTO StudentModuleGradeDictDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface StudentModuleGradeDictMapper extends EntityMapper<StudentModuleGradeDictDTO, StudentModuleGradeDict> {



    default StudentModuleGradeDict fromId(Long id) {
        if (id == null) {
            return null;
        }
        StudentModuleGradeDict studentModuleGradeDict = new StudentModuleGradeDict();
        studentModuleGradeDict.setId(id);
        return studentModuleGradeDict;
    }
}

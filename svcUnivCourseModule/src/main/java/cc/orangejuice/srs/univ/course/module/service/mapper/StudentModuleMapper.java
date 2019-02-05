package cc.orangejuice.srs.univ.course.module.service.mapper;

import cc.orangejuice.srs.univ.course.module.domain.*;
import cc.orangejuice.srs.univ.course.module.service.dto.StudentModuleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity StudentModule and its DTO StudentModuleDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface StudentModuleMapper extends EntityMapper<StudentModuleDTO, StudentModule> {



    default StudentModule fromId(Long id) {
        if (id == null) {
            return null;
        }
        StudentModule studentModule = new StudentModule();
        studentModule.setId(id);
        return studentModule;
    }
}

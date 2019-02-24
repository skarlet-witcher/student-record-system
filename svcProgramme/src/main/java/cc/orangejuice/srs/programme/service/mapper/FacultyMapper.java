package cc.orangejuice.srs.programme.service.mapper;

import cc.orangejuice.srs.programme.domain.*;
import cc.orangejuice.srs.programme.service.dto.FacultyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Faculty and its DTO FacultyDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FacultyMapper extends EntityMapper<FacultyDTO, Faculty> {


    @Mapping(target = "departments", ignore = true)
    Faculty toEntity(FacultyDTO facultyDTO);

    default Faculty fromId(Long id) {
        if (id == null) {
            return null;
        }
        Faculty faculty = new Faculty();
        faculty.setId(id);
        return faculty;
    }
}

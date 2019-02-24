package cc.orangejuice.srs.programme.service.mapper;

import cc.orangejuice.srs.programme.domain.*;
import cc.orangejuice.srs.programme.service.dto.DepartmentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Department and its DTO DepartmentDTO.
 */
@Mapper(componentModel = "spring", uses = {FacultyMapper.class})
public interface DepartmentMapper extends EntityMapper<DepartmentDTO, Department> {

    @Mapping(source = "faculty.id", target = "facultyId")
    @Mapping(source = "faculty.name", target = "facultyName")
    DepartmentDTO toDto(Department department);

    @Mapping(target = "programmes", ignore = true)
    @Mapping(source = "facultyId", target = "faculty")
    Department toEntity(DepartmentDTO departmentDTO);

    default Department fromId(Long id) {
        if (id == null) {
            return null;
        }
        Department department = new Department();
        department.setId(id);
        return department;
    }
}

package cc.orangejuice.srs.student.service.mapper;

import cc.orangejuice.srs.student.domain.*;
import cc.orangejuice.srs.student.service.dto.StudentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Student and its DTO StudentDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface StudentMapper extends EntityMapper<StudentDTO, Student> {


    @Mapping(target = "studentEnrolls", ignore = true)
    @Mapping(target = "studentProgressions", ignore = true)
    Student toEntity(StudentDTO studentDTO);

    default Student fromId(Long id) {
        if (id == null) {
            return null;
        }
        Student student = new Student();
        student.setId(id);
        return student;
    }
}

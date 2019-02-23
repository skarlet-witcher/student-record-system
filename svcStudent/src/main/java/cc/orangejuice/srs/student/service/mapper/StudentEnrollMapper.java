package cc.orangejuice.srs.student.service.mapper;

import cc.orangejuice.srs.student.domain.*;
import cc.orangejuice.srs.student.service.dto.StudentEnrollDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity StudentEnroll and its DTO StudentEnrollDTO.
 */
@Mapper(componentModel = "spring", uses = {StudentMapper.class})
public interface StudentEnrollMapper extends EntityMapper<StudentEnrollDTO, StudentEnroll> {

    @Mapping(source = "student.id", target = "studentId")
    @Mapping(source = "student.studentNumber", target = "studentStudentNumber")
    StudentEnrollDTO toDto(StudentEnroll studentEnroll);

    @Mapping(source = "studentId", target = "student")
    StudentEnroll toEntity(StudentEnrollDTO studentEnrollDTO);

    default StudentEnroll fromId(Long id) {
        if (id == null) {
            return null;
        }
        StudentEnroll studentEnroll = new StudentEnroll();
        studentEnroll.setId(id);
        return studentEnroll;
    }
}

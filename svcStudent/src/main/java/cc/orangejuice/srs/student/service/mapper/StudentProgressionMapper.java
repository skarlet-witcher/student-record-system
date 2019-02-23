package cc.orangejuice.srs.student.service.mapper;

import cc.orangejuice.srs.student.domain.*;
import cc.orangejuice.srs.student.service.dto.StudentProgressionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity StudentProgression and its DTO StudentProgressionDTO.
 */
@Mapper(componentModel = "spring", uses = {StudentMapper.class})
public interface StudentProgressionMapper extends EntityMapper<StudentProgressionDTO, StudentProgression> {

    @Mapping(source = "student.id", target = "studentId")
    @Mapping(source = "student.studentNumber", target = "studentStudentNumber")
    StudentProgressionDTO toDto(StudentProgression studentProgression);

    @Mapping(source = "studentId", target = "student")
    StudentProgression toEntity(StudentProgressionDTO studentProgressionDTO);

    default StudentProgression fromId(Long id) {
        if (id == null) {
            return null;
        }
        StudentProgression studentProgression = new StudentProgression();
        studentProgression.setId(id);
        return studentProgression;
    }
}

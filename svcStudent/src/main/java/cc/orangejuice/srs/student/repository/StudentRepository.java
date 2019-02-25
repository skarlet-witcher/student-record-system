package cc.orangejuice.srs.student.repository;

import cc.orangejuice.srs.student.domain.Student;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the Student entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query(value = "Select student From Student student Where student.studentNumber=:studentNumber")
    Optional<Student> findOneStudentByStudentNumber(@Param("studentNumber") String studentNumber);
}

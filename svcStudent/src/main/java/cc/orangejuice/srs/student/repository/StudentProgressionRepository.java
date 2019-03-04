package cc.orangejuice.srs.student.repository;

import cc.orangejuice.srs.student.domain.Student;
import cc.orangejuice.srs.student.domain.StudentProgression;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the StudentProgression entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StudentProgressionRepository extends JpaRepository<StudentProgression, Long> {
    List<StudentProgression> findAllByStudent(@Param("student") Student student);

}

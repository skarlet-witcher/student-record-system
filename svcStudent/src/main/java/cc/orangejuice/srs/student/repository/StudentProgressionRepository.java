package cc.orangejuice.srs.student.repository;

import cc.orangejuice.srs.student.domain.StudentProgression;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the StudentProgression entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StudentProgressionRepository extends JpaRepository<StudentProgression, Long> {

}

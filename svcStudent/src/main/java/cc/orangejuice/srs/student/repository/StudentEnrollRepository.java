package cc.orangejuice.srs.student.repository;

import cc.orangejuice.srs.student.domain.StudentEnroll;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the StudentEnroll entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StudentEnrollRepository extends JpaRepository<StudentEnroll, Long> {
    
}

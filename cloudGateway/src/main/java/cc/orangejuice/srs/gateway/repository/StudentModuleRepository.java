package cc.orangejuice.srs.gateway.repository;

import cc.orangejuice.srs.gateway.domain.StudentModule;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the StudentModule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StudentModuleRepository extends JpaRepository<StudentModule, Long> {

}

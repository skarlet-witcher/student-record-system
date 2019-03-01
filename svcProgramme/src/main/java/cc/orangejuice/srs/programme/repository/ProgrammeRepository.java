package cc.orangejuice.srs.programme.repository;

import cc.orangejuice.srs.programme.domain.Programme;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Programme entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProgrammeRepository extends JpaRepository<Programme, Long> {

}

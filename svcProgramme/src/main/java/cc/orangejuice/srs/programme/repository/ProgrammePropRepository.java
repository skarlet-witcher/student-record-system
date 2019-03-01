package cc.orangejuice.srs.programme.repository;

import cc.orangejuice.srs.programme.domain.ProgrammeProp;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProgrammeProp entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProgrammePropRepository extends JpaRepository<ProgrammeProp, Long> {

}

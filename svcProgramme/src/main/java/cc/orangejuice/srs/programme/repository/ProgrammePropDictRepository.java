package cc.orangejuice.srs.programme.repository;

import cc.orangejuice.srs.programme.domain.ProgrammePropDict;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProgrammePropDict entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProgrammePropDictRepository extends JpaRepository<ProgrammePropDict, Long> {

}

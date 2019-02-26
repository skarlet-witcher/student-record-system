package cc.orangejuice.srs.programme.repository;

import cc.orangejuice.srs.programme.domain.Programme;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the Programme entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProgrammeRepository extends JpaRepository<Programme, Long> {

    @Query(value = "SELECT programme FROM Programme programme WHERE programme.code=:programmeCode")
    Optional<Programme> findOneProgrammeByProgrammeCode(@Param("programmeCode") String programmeCode);
}

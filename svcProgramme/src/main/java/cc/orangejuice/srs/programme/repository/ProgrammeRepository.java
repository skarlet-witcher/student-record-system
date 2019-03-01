package cc.orangejuice.srs.programme.repository;

import cc.orangejuice.srs.programme.domain.Programme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the Programme entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProgrammeRepository extends JpaRepository<Programme, Long> {

    Optional<Programme> findOneByCodeIgnoreCase(@Param("code") String programmeCode);

}

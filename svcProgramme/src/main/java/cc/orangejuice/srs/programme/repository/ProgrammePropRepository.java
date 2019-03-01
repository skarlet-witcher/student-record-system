package cc.orangejuice.srs.programme.repository;

import cc.orangejuice.srs.programme.domain.ProgrammeProp;
import cc.orangejuice.srs.programme.domain.enumeration.ProgrammePropType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the ProgrammeProp entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProgrammePropRepository extends JpaRepository<ProgrammeProp, Long> {

    Optional<ProgrammeProp> findOneByTypeAndForEnrollYearAndKey(
        @Param("type") ProgrammePropType type,
        @Param("forEnrollYear") Integer forEnrollYear,
        @Param("key") String key);

    Optional<ProgrammeProp> findOneByTypeAndForEnrollYearAndForSemesterNoAndKey(
        @Param("type") ProgrammePropType type,
        @Param("forEnrollYear") Integer forEnrollYear,
        @Param("forSemesterNo") Integer forSemesterNo,
        @Param("key") String key);

    Optional<ProgrammeProp> findOneByTypeAndForEnrollYearAndForYearNoAndKey(
        @Param("type") ProgrammePropType type,
        @Param("forEnrollYear") Integer forEnrollYear,
        @Param("forYearNo") Integer forYearNo,
        @Param("key") String key);
}

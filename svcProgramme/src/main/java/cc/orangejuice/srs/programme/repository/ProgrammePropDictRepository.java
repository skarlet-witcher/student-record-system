package cc.orangejuice.srs.programme.repository;

import cc.orangejuice.srs.programme.domain.ProgrammePropDict;
import cc.orangejuice.srs.programme.domain.enumeration.ProgrammePropType;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the ProgrammePropDict entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProgrammePropDictRepository extends JpaRepository<ProgrammePropDict, Long> {

    Optional<ProgrammePropDict> findOneByTypeAndForEnrollYearAndKey(
        @Param("type") ProgrammePropType type,
        @Param("forEnrollYear") Integer forEnrollYear,
        @Param("key") String key);

    Optional<ProgrammePropDict> findOneByTypeAndForEnrollYearAndForSemesterNoAndKey(
        @Param("type") ProgrammePropType type,
        @Param("forEnrollYear") Integer forEnrollYear,
        @Param("forSemesterNo") Integer forSemesterNo,
        @Param("key") String key);

    Optional<ProgrammePropDict> findOneByTypeAndForEnrollYearAndForYearNoAndKey(
        @Param("type") ProgrammePropType type,
        @Param("forEnrollYear") Integer forEnrollYear,
        @Param("forYearNo") Integer forYearNo,
        @Param("key") String key);

}

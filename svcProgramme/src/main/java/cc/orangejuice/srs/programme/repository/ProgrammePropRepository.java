package cc.orangejuice.srs.programme.repository;

import cc.orangejuice.srs.programme.domain.ProgrammeProp;
import cc.orangejuice.srs.programme.domain.enumeration.ProgrammePropType;
import cc.orangejuice.srs.programme.service.dto.ProgrammePropDTO;
import org.checkerframework.checker.nullness.qual.PolyRaw;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the ProgrammeProp entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProgrammePropRepository extends JpaRepository<ProgrammeProp, Long> {
    List<ProgrammeProp> findOneByTypeAndForEnrollYearAndForYearNoAndKey(
        @Param("type") ProgrammePropType type,
        @Param("forEnrollYear") Integer forEnrollYear,
        @Param("forYearNo") Integer forYearNo,
        @Param("key") String key);

    List<ProgrammeProp> findOneByTypeAndForEnrollYearAndForSemesterNoAndKey(
        @Param("type") ProgrammePropType type,
        @Param("forEnrollYear") Integer forEnrollYear,
        @Param("forSemesterNo") Integer forSemesterNo,
        @Param("key") String key);

    List<ProgrammeProp> findAllByTypeAndForEnrollYearAndKey(
        @Param("type") ProgrammePropType type,
        @Param("forEnrollYear") Integer forEnrollYear,
        @Param("key") String key);

    List<ProgrammeProp> findByForEnrollYearAndKeyAndValue(
        @Param("forEnrollYear") Integer forEnrollYear,
        @Param("key") String key,
        @Param("value") String value);


}

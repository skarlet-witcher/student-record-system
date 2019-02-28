package cc.orangejuice.srs.programme.service;

import cc.orangejuice.srs.programme.domain.ProgrammePropDict;
import cc.orangejuice.srs.programme.domain.enumeration.ProgrammePropType;
import cc.orangejuice.srs.programme.repository.ProgrammePropDictRepository;
import cc.orangejuice.srs.programme.service.dto.ProgrammePropDictDTO;
import cc.orangejuice.srs.programme.service.mapper.ProgrammePropDictMapper;
import com.netflix.discovery.converters.Auto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.Optional;

/**
 * Service Implementation for managing ProgrammePropDict.
 */
@Service
@Transactional
public class ProgrammePropDictService {

    private final Logger log = LoggerFactory.getLogger(ProgrammePropDictService.class);

    private final ProgrammePropDictRepository programmePropDictRepository;

    private final ProgrammePropDictMapper programmePropDictMapper;

    public ProgrammePropDictService(ProgrammePropDictRepository programmePropDictRepository, ProgrammePropDictMapper programmePropDictMapper) {
        this.programmePropDictRepository = programmePropDictRepository;
        this.programmePropDictMapper = programmePropDictMapper;
    }

    /**
     * Save a programmePropDict.
     *
     * @param programmePropDictDTO the entity to save
     * @return the persisted entity
     */
    public ProgrammePropDictDTO save(ProgrammePropDictDTO programmePropDictDTO) {
        log.debug("Request to save ProgrammePropDict : {}", programmePropDictDTO);
        ProgrammePropDict programmePropDict = programmePropDictMapper.toEntity(programmePropDictDTO);
        programmePropDict = programmePropDictRepository.save(programmePropDict);
        return programmePropDictMapper.toDto(programmePropDict);
    }

    /**
     * Get all the programmePropDicts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ProgrammePropDictDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProgrammePropDicts");
        return programmePropDictRepository.findAll(pageable)
            .map(programmePropDictMapper::toDto);
    }


    /**
     * Get one programmePropDict by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ProgrammePropDictDTO> findOne(Long id) {
        log.debug("Request to get ProgrammePropDict : {}", id);
        return programmePropDictRepository.findById(id)
            .map(programmePropDictMapper::toDto);
    }

    /**
     * Delete the programmePropDict by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ProgrammePropDict : {}", id);
        programmePropDictRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Optional<ProgrammePropDictDTO> findOneForGeneral(ProgrammePropType type, Integer forEnrollYear, String key) {
        log.debug("Request to get ProgrammeProDict Where type is {}, forEnrollYear is {} and key is {}", type, forEnrollYear, key);
        return programmePropDictRepository.findOneByTypeAndForEnrollYearAndKey(type, forEnrollYear, key)
            .map(programmePropDictMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<ProgrammePropDictDTO> findOneForSemester(ProgrammePropType type, Integer forEnrollYear, Integer forSemesterNo, String key) {
        log.debug("Request to get ProgrammeProDict  Where type is {}, forEnrollYear is {}, forSemesterNO is {} and key is {}", type, forEnrollYear, forSemesterNo, key);
        return programmePropDictRepository.findOneByTypeAndForEnrollYearAndForSemesterNoAndKey(
            type, forEnrollYear, forSemesterNo, key).map(programmePropDictMapper::toDto);
    }

    public Optional<ProgrammePropDictDTO> findOneForYear(ProgrammePropType type, Integer forEnrollYear, Integer forYearNo, String key) {
        log.debug("Request to get ProgrammeProDict  Where type is {}, forEnrollYear is {}, forYearNo is {} and key is {}", type, forEnrollYear, forYearNo, key);
        return programmePropDictRepository.findOneByTypeAndForEnrollYearAndForYearNoAndKey(type, forEnrollYear, forYearNo, key)
            .map(programmePropDictMapper::toDto);

    }
}

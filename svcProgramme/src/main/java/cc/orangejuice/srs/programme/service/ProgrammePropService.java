package cc.orangejuice.srs.programme.service;

import cc.orangejuice.srs.programme.domain.ProgrammeProp;
import cc.orangejuice.srs.programme.domain.enumeration.ProgrammePropType;
import cc.orangejuice.srs.programme.repository.ProgrammePropRepository;
import cc.orangejuice.srs.programme.service.dto.ProgrammePropDTO;
import cc.orangejuice.srs.programme.service.mapper.ProgrammePropMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing ProgrammeProp.
 */
@Service
@Transactional
public class ProgrammePropService {

    private final Logger log = LoggerFactory.getLogger(ProgrammePropService.class);

    private final ProgrammePropRepository programmePropRepository;

    private final ProgrammePropMapper programmePropMapper;

    public ProgrammePropService(ProgrammePropRepository programmePropRepository, ProgrammePropMapper programmePropMapper) {
        this.programmePropRepository = programmePropRepository;
        this.programmePropMapper = programmePropMapper;
    }

    /**
     * Save a programmeProp.
     *
     * @param programmePropDTO the entity to save
     * @return the persisted entity
     */
    public ProgrammePropDTO save(ProgrammePropDTO programmePropDTO) {
        log.debug("Request to save ProgrammeProp : {}", programmePropDTO);
        ProgrammeProp programmeProp = programmePropMapper.toEntity(programmePropDTO);
        programmeProp = programmePropRepository.save(programmeProp);
        return programmePropMapper.toDto(programmeProp);
    }

    /**
     * Get all the programmeProps.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ProgrammePropDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProgrammeProps");
        return programmePropRepository.findAll(pageable)
            .map(programmePropMapper::toDto);
    }


    /**
     * Get one programmeProp by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ProgrammePropDTO> findOne(Long id) {
        log.debug("Request to get ProgrammeProp : {}", id);
        return programmePropRepository.findById(id)
            .map(programmePropMapper::toDto);
    }

    /**
     * Delete the programmeProp by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ProgrammeProp : {}", id);
        programmePropRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<ProgrammePropDTO> findOneByYear(ProgrammePropType type, Integer forEnrollYear, Integer forYearNo, String key) {
        log.debug("Request to get ProgrammeProp by Type: {}, forEnrollYear: {}, forYearNo: {} and key {}",
            type, forEnrollYear, forYearNo, key);
        if(forYearNo == null) {
            return  programmePropMapper.toDto(programmePropRepository.findAllByTypeAndForEnrollYearAndKey(type, forEnrollYear, key));
        }
        return programmePropMapper.toDto(programmePropRepository.findOneByTypeAndForEnrollYearAndForYearNoAndKey(type, forEnrollYear, forYearNo, key));
    }

    public List<ProgrammePropDTO> findOneBySemester(ProgrammePropType type, Integer forEnrollYear, Integer forSemesterNo, String key) {
        log.debug("Request to get ProgrammeProp by Type: {}, forEnrollYear: {}, forSemesterNo: {} and key {}",
            type, forEnrollYear, forSemesterNo, key);
        if(forSemesterNo == null) {
            return  programmePropMapper.toDto(programmePropRepository.findAllByTypeAndForEnrollYearAndKey(type, forSemesterNo, key));
        }
        return programmePropMapper.toDto(programmePropRepository.findOneByTypeAndForEnrollYearAndForSemesterNoAndKey(type, forEnrollYear, forSemesterNo, key));
    }



}

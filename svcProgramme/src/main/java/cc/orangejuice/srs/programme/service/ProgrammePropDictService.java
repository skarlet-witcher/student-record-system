package cc.orangejuice.srs.programme.service;

import cc.orangejuice.srs.programme.domain.ProgrammePropDict;
import cc.orangejuice.srs.programme.repository.ProgrammePropDictRepository;
import cc.orangejuice.srs.programme.service.dto.ProgrammePropDictDTO;
import cc.orangejuice.srs.programme.service.mapper.ProgrammePropDictMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        log.debug("Request to delete ProgrammePropDict : {}", id);        programmePropDictRepository.deleteById(id);
    }
}

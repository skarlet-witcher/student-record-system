package cc.orangejuice.srs.programme.service;

import cc.orangejuice.srs.programme.domain.Programme;
import cc.orangejuice.srs.programme.repository.ProgrammeRepository;
import cc.orangejuice.srs.programme.service.dto.ProgrammeDTO;
import cc.orangejuice.srs.programme.service.mapper.ProgrammeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Programme.
 */
@Service
@Transactional
public class ProgrammeService {

    private final Logger log = LoggerFactory.getLogger(ProgrammeService.class);

    private final ProgrammeRepository programmeRepository;

    private final ProgrammeMapper programmeMapper;

    public ProgrammeService(ProgrammeRepository programmeRepository, ProgrammeMapper programmeMapper) {
        this.programmeRepository = programmeRepository;
        this.programmeMapper = programmeMapper;
    }

    /**
     * Save a programme.
     *
     * @param programmeDTO the entity to save
     * @return the persisted entity
     */
    public ProgrammeDTO save(ProgrammeDTO programmeDTO) {
        log.debug("Request to save Programme : {}", programmeDTO);
        Programme programme = programmeMapper.toEntity(programmeDTO);
        programme = programmeRepository.save(programme);
        return programmeMapper.toDto(programme);
    }

    /**
     * Get all the programmes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ProgrammeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Programmes");
        return programmeRepository.findAll(pageable)
            .map(programmeMapper::toDto);
    }


    /**
     * Get one programme by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ProgrammeDTO> findOne(Long id) {
        log.debug("Request to get Programme : {}", id);
        return programmeRepository.findById(id)
            .map(programmeMapper::toDto);
    }

    /**
     * Delete the programme by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Programme : {}", id);
        programmeRepository.deleteById(id);
    }
}

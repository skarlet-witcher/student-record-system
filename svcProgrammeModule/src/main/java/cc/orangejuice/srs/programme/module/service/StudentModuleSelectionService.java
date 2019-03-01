package cc.orangejuice.srs.programme.module.service;

import cc.orangejuice.srs.programme.module.domain.StudentModuleSelection;
import cc.orangejuice.srs.programme.module.repository.StudentModuleSelectionRepository;
import cc.orangejuice.srs.programme.module.service.dto.StudentModuleSelectionDTO;
import cc.orangejuice.srs.programme.module.service.mapper.StudentModuleSelectionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing StudentModuleSelection.
 */
@Service
@Transactional
public class StudentModuleSelectionService {

    private final Logger log = LoggerFactory.getLogger(StudentModuleSelectionService.class);

    private final StudentModuleSelectionRepository studentModuleSelectionRepository;

    private final StudentModuleSelectionMapper studentModuleSelectionMapper;


    public StudentModuleSelectionService(StudentModuleSelectionRepository studentModuleSelectionRepository, StudentModuleSelectionMapper studentModuleSelectionMapper) {
        this.studentModuleSelectionRepository = studentModuleSelectionRepository;
        this.studentModuleSelectionMapper = studentModuleSelectionMapper;
    }

    /**
     * Save a studentModuleSelection.
     *
     * @param studentModuleSelectionDTO the entity to save
     * @return the persisted entity
     */
    public StudentModuleSelectionDTO save(StudentModuleSelectionDTO studentModuleSelectionDTO) {
        log.debug("Request to save StudentModuleSelection : {}", studentModuleSelectionDTO);
        StudentModuleSelection studentModuleSelection = studentModuleSelectionMapper.toEntity(studentModuleSelectionDTO);
        studentModuleSelection = studentModuleSelectionRepository.save(studentModuleSelection);
        return studentModuleSelectionMapper.toDto(studentModuleSelection);
    }

    /**
     * Get all the studentModuleSelections.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<StudentModuleSelectionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all StudentModuleSelections");
        return studentModuleSelectionRepository.findAll(pageable)
            .map(studentModuleSelectionMapper::toDto);
    }


    /**
     * Get one studentModuleSelection by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<StudentModuleSelectionDTO> findOne(Long id) {
        log.debug("Request to get StudentModuleSelection : {}", id);
        return studentModuleSelectionRepository.findById(id)
            .map(studentModuleSelectionMapper::toDto);
    }

    /**
     * Delete the studentModuleSelection by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete StudentModuleSelection : {}", id);
        studentModuleSelectionRepository.deleteById(id);
    }


    public void updateMarkBySelectionIdAndMark(Long selectionId, Double mark) {
        log.debug("Request to update id: {} StudentModuleSelections with mark {}", selectionId, mark);
        studentModuleSelectionRepository.updateByIdAndMark(selectionId, mark);
    }
    // todo business logic for calculating QCA

    // sub-goal get credit
    private Double getCredit(Long selectionId) {
        log.debug("Request to get credit from a module in the selection {}", selectionId);
        Optional<StudentModuleSelection> studentModuleSelection = studentModuleSelectionRepository.findById(selectionId);
        return studentModuleSelection.get().getModule().getCredit();
    }
    /*
    // sub-goal getFactor
    private Double getFactor() {
        log.debug("Request to get factor from ");
        // get factor by many parameters. enrollYear remaining
    }
    */
}

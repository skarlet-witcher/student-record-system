package cc.orangejuice.srs.univ.course.module.service;

import cc.orangejuice.srs.univ.course.module.domain.StudentModule;
import cc.orangejuice.srs.univ.course.module.repository.StudentModuleRepository;
import cc.orangejuice.srs.univ.course.module.service.dto.StudentModuleDTO;
import cc.orangejuice.srs.univ.course.module.service.mapper.StudentModuleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing StudentModule.
 */
@Service
@Transactional
public class StudentModuleService {

    private final Logger log = LoggerFactory.getLogger(StudentModuleService.class);

    private final StudentModuleRepository studentModuleRepository;

    private final StudentModuleMapper studentModuleMapper;

    public StudentModuleService(StudentModuleRepository studentModuleRepository, StudentModuleMapper studentModuleMapper) {
        this.studentModuleRepository = studentModuleRepository;
        this.studentModuleMapper = studentModuleMapper;
    }

    /**
     * Save a studentModule.
     *
     * @param studentModuleDTO the entity to save
     * @return the persisted entity
     */
    public StudentModuleDTO save(StudentModuleDTO studentModuleDTO) {
        log.debug("Request to save StudentModule : {}", studentModuleDTO);
        StudentModule studentModule = studentModuleMapper.toEntity(studentModuleDTO);
        studentModule = studentModuleRepository.save(studentModule);
        return studentModuleMapper.toDto(studentModule);
    }

    /**
     * Get all the studentModules.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<StudentModuleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all StudentModules");
        return studentModuleRepository.findAll(pageable)
            .map(studentModuleMapper::toDto);
    }


    /**
     * Get one studentModule by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<StudentModuleDTO> findOne(Long id) {
        log.debug("Request to get StudentModule : {}", id);
        return studentModuleRepository.findById(id)
            .map(studentModuleMapper::toDto);
    }

    /**
     * Delete the studentModule by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete StudentModule : {}", id);        studentModuleRepository.deleteById(id);
    }
}

package cc.orangejuice.srs.programme.module.service;

import cc.orangejuice.srs.programme.module.domain.StudentModuleGradeDict;
import cc.orangejuice.srs.programme.module.repository.StudentModuleGradeDictRepository;
import cc.orangejuice.srs.programme.module.service.dto.StudentModuleGradeDictDTO;
import cc.orangejuice.srs.programme.module.service.mapper.StudentModuleGradeDictMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing StudentModuleGradeDict.
 */
@Service
@Transactional
public class StudentModuleGradeDictService {

    private final Logger log = LoggerFactory.getLogger(StudentModuleGradeDictService.class);

    private final StudentModuleGradeDictRepository studentModuleGradeDictRepository;

    private final StudentModuleGradeDictMapper studentModuleGradeDictMapper;

    public StudentModuleGradeDictService(StudentModuleGradeDictRepository studentModuleGradeDictRepository, StudentModuleGradeDictMapper studentModuleGradeDictMapper) {
        this.studentModuleGradeDictRepository = studentModuleGradeDictRepository;
        this.studentModuleGradeDictMapper = studentModuleGradeDictMapper;
    }

    /**
     * Save a studentModuleGradeDict.
     *
     * @param studentModuleGradeDictDTO the entity to save
     * @return the persisted entity
     */
    public StudentModuleGradeDictDTO save(StudentModuleGradeDictDTO studentModuleGradeDictDTO) {
        log.debug("Request to save StudentModuleGradeDict : {}", studentModuleGradeDictDTO);
        StudentModuleGradeDict studentModuleGradeDict = studentModuleGradeDictMapper.toEntity(studentModuleGradeDictDTO);
        studentModuleGradeDict = studentModuleGradeDictRepository.save(studentModuleGradeDict);
        return studentModuleGradeDictMapper.toDto(studentModuleGradeDict);
    }

    /**
     * Get all the studentModuleGradeDicts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<StudentModuleGradeDictDTO> findAll(Pageable pageable) {
        log.debug("Request to get all StudentModuleGradeDicts");
        return studentModuleGradeDictRepository.findAll(pageable)
            .map(studentModuleGradeDictMapper::toDto);
    }


    /**
     * Get one studentModuleGradeDict by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<StudentModuleGradeDictDTO> findOne(Long id) {
        log.debug("Request to get StudentModuleGradeDict : {}", id);
        return studentModuleGradeDictRepository.findById(id)
            .map(studentModuleGradeDictMapper::toDto);
    }

    /**
     * Delete the studentModuleGradeDict by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete StudentModuleGradeDict : {}", id);        studentModuleGradeDictRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Optional<StudentModuleGradeDictDTO> findOneGradeTypeByGradeName(String gradeName) {
        log.debug("Request to get {} StudentModuleGradeDict ", gradeName);
        return studentModuleGradeDictRepository.findOneGradeTypeByName(gradeName)
            .map(studentModuleGradeDictMapper::toDto);
    }
}

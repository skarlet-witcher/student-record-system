package cc.orangejuice.srs.student.service;

import cc.orangejuice.srs.student.domain.StudentEnroll;
import cc.orangejuice.srs.student.repository.StudentEnrollRepository;
import cc.orangejuice.srs.student.service.dto.StudentEnrollDTO;
import cc.orangejuice.srs.student.service.mapper.StudentEnrollMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing StudentEnroll.
 */
@Service
@Transactional
public class StudentEnrollService {

    private final Logger log = LoggerFactory.getLogger(StudentEnrollService.class);

    private final StudentEnrollRepository studentEnrollRepository;

    private final StudentEnrollMapper studentEnrollMapper;

    public StudentEnrollService(StudentEnrollRepository studentEnrollRepository, StudentEnrollMapper studentEnrollMapper) {
        this.studentEnrollRepository = studentEnrollRepository;
        this.studentEnrollMapper = studentEnrollMapper;
    }

    /**
     * Save a studentEnroll.
     *
     * @param studentEnrollDTO the entity to save
     * @return the persisted entity
     */
    public StudentEnrollDTO save(StudentEnrollDTO studentEnrollDTO) {
        log.debug("Request to save StudentEnroll : {}", studentEnrollDTO);
        StudentEnroll studentEnroll = studentEnrollMapper.toEntity(studentEnrollDTO);
        studentEnroll = studentEnrollRepository.save(studentEnroll);
        return studentEnrollMapper.toDto(studentEnroll);
    }

    /**
     * Get all the studentEnrolls.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<StudentEnrollDTO> findAll(Pageable pageable) {
        log.debug("Request to get all StudentEnrolls");
        return studentEnrollRepository.findAll(pageable)
            .map(studentEnrollMapper::toDto);
    }


    /**
     * Get one studentEnroll by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<StudentEnrollDTO> findOne(Long id) {
        log.debug("Request to get StudentEnroll : {}", id);
        return studentEnrollRepository.findById(id)
            .map(studentEnrollMapper::toDto);
    }

    /**
     * Delete the studentEnroll by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete StudentEnroll : {}", id);
        studentEnrollRepository.deleteById(id);
    }


    /**
     *  get student enroll detail by student id
     *  one of the steps for gathering data for transcript
     * @param studentId student id (not student number)
     *
     */
    public StudentEnrollDTO getOneByStudentId(Long studentId) {
        log.debug("Request to get student enroll by student id: {}", studentId);
        List<StudentEnroll> studentEnrolls = studentEnrollRepository.findAll();
        StudentEnrollDTO studentEnrollDTO = new StudentEnrollDTO();
        for(StudentEnroll studentEnroll : studentEnrolls) {
            if(studentEnroll.getStudent().getId() == studentId) {
                studentEnrollDTO = studentEnrollMapper.toDto(studentEnroll);
            }
        }
        log.debug("Finish get the student enroll detail with the student number of ", studentEnrollDTO.getStudentStudentNumber());
        return studentEnrollDTO;
    }
}

package cc.orangejuice.srs.student.service;

import cc.orangejuice.srs.student.domain.Student;
import cc.orangejuice.srs.student.repository.StudentRepository;
import cc.orangejuice.srs.student.service.dto.StudentDTO;
import cc.orangejuice.srs.student.service.mapper.StudentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Student.
 */
@Service
@Transactional
public class StudentService {

    private final Logger log = LoggerFactory.getLogger(StudentService.class);

    private final StudentRepository studentRepository;

    private final StudentMapper studentMapper;

    public StudentService(StudentRepository studentRepository, StudentMapper studentMapper) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }

    /**
     * Save a student.
     *
     * @param studentDTO the entity to save
     * @return the persisted entity
     */
    public StudentDTO save(StudentDTO studentDTO) {
        log.debug("Request to save Student : {}", studentDTO);
        Student student = studentMapper.toEntity(studentDTO);
        student = studentRepository.save(student);
        return studentMapper.toDto(student);
    }

    /**
     * Get all the students.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<StudentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Students");
        return studentRepository.findAll(pageable)
            .map(studentMapper::toDto);
    }


    /**
     * Get one student by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<StudentDTO> findOne(Long id) {
        log.debug("Request to get Student : {}", id);
        return studentRepository.findById(id)
            .map(studentMapper::toDto);
    }

    /**
     * Delete the student by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Student : {}", id);        studentRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Optional<StudentDTO> findOneStudentByStudentNumber(String studentNumber) {
        log.debug("Request to get {} Student", studentNumber);
        return studentRepository.findOneStudentByStudentNumber(studentNumber).map(studentMapper::toDto);
    }
}

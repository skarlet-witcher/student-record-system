package cc.orangejuice.srs.student.service;

import cc.orangejuice.srs.student.domain.StudentProgression;
import cc.orangejuice.srs.student.domain.enumeration.ProgressDecision;
import cc.orangejuice.srs.student.repository.StudentProgressionRepository;
import cc.orangejuice.srs.student.service.dto.StudentProgressionDTO;
import cc.orangejuice.srs.student.service.mapper.StudentProgressionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing StudentProgression.
 */
@Service
@Transactional
public class StudentProgressionService {

    private final Logger log = LoggerFactory.getLogger(StudentProgressionService.class);

    private final StudentProgressionRepository studentProgressionRepository;

    private final StudentProgressionMapper studentProgressionMapper;

    public StudentProgressionService(StudentProgressionRepository studentProgressionRepository, StudentProgressionMapper studentProgressionMapper) {
        this.studentProgressionRepository = studentProgressionRepository;
        this.studentProgressionMapper = studentProgressionMapper;
    }

    /**
     * Save a studentProgression.
     *
     * @param studentProgressionDTO the entity to save
     * @return the persisted entity
     */
    public StudentProgressionDTO save(StudentProgressionDTO studentProgressionDTO) {
        log.debug("Request to save StudentProgression : {}", studentProgressionDTO);
        StudentProgression studentProgression = studentProgressionMapper.toEntity(studentProgressionDTO);
        studentProgression = studentProgressionRepository.save(studentProgression);
        return studentProgressionMapper.toDto(studentProgression);
    }

    /**
     * Get all the studentProgressions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<StudentProgressionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all StudentProgressions");
        return studentProgressionRepository.findAll(pageable)
            .map(studentProgressionMapper::toDto);
    }


    /**
     * Get one studentProgression by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<StudentProgressionDTO> findOne(Long id) {
        log.debug("Request to get StudentProgression : {}", id);
        return studentProgressionRepository.findById(id)
            .map(studentProgressionMapper::toDto);
    }

    /**
     * Delete the studentProgression by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete StudentProgression : {}", id);        studentProgressionRepository.deleteById(id);
    }


    public void firstDecision() {
        log.debug("Begin making first decision of transiting state from NO_STATE to PASS/SUSPENSION");

        List<StudentProgression> studentProgressionRepositoryAll = studentProgressionRepository.findAll();
        for (StudentProgression studentProgression: studentProgressionRepositoryAll) {
            if(studentProgression.getYearNo() == 1 && studentProgression.getSemesterNo() == 2){
                if(studentProgression.getQca() >= 2){
                    studentProgression.setProgressDecision(ProgressDecision.PASS);
                }
                else {
                    //need to get QCS to calculate QCA
//                    if(swap > 2.0 ) -> fail can repeat
//                    if(swap < 2.0) -> fail no repeat
                }

            }
        }

    }

    public void secondDesion() {
        log.debug("Begin making second decision from FAIL_CAN_REPEAT to PASS or FAIL_NO_REPEAT");
        List<StudentProgression> studentProgressionRepositoryAll = studentProgressionRepository.findAll();
        for (StudentProgression studentProgression: studentProgressionRepositoryAll) {
            if(studentProgression.getProgressDecision().equals(ProgressDecision.FAIL_CAN_REPEAT)){

            }
        }

    }


}

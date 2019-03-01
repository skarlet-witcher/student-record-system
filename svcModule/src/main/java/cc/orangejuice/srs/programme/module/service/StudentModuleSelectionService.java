package cc.orangejuice.srs.programme.module.service;

import cc.orangejuice.srs.programme.domain.enumeration.ProgrammePropType;
import cc.orangejuice.srs.programme.module.client.ProgrammeFeignClient;
import cc.orangejuice.srs.programme.module.domain.ModuleGrade;
import cc.orangejuice.srs.programme.module.domain.StudentModuleSelection;
import cc.orangejuice.srs.programme.module.repository.StudentModuleSelectionRepository;
import cc.orangejuice.srs.programme.module.service.dto.ModuleGradeDTO;
import cc.orangejuice.srs.programme.module.service.dto.StudentModuleSelectionDTO;
import cc.orangejuice.srs.programme.module.service.mapper.StudentModuleSelectionMapper;
import cc.orangejuice.srs.programme.service.dto.ProgrammePropDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    private final ProgrammeFeignClient programmeFeignClient;

    private final ModuleGradeService moduleGradeService;


    public StudentModuleSelectionService(StudentModuleSelectionRepository studentModuleSelectionRepository,
                                         StudentModuleSelectionMapper studentModuleSelectionMapper,
                                         ProgrammeFeignClient programmeFeignClient,
                                         ModuleGradeService moduleGradeService) {
        this.studentModuleSelectionRepository = studentModuleSelectionRepository;
        this.studentModuleSelectionMapper = studentModuleSelectionMapper;
        this.programmeFeignClient = programmeFeignClient;
        this.moduleGradeService = moduleGradeService;
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
        Double creditHour;
        studentModuleSelectionRepository.updateByIdAndMark(selectionId, mark);
        creditHour = getCreditHour(selectionId);
        updateQCS(selectionId, mark, creditHour);
    }
    // todo business logic for calculating QCA

    // sub-goal get credit
    private Double getCreditHour(Long selectionId) {
        log.debug("Request to get credit from a module in the selection {}", selectionId);
        Optional<StudentModuleSelection> studentModuleSelection = studentModuleSelectionRepository.findById(selectionId);
        return studentModuleSelection.get().getModule().getCredit() * getFactor(studentModuleSelection);
    }

    // sub-goal getFactor
    private Double getFactor(Optional<StudentModuleSelection> studentModuleSelection) {
        log.debug("Request to get factor from academicYear: {}, academicSemester: {}, yearNumber: {}, SemesterNumber: {}",
            studentModuleSelection.get().getAcademicYear(),
            studentModuleSelection.get().getAcademicSemester(),
            studentModuleSelection.get().getYearNo(),
            studentModuleSelection.get().getSemesterNo());

        Optional<ProgrammePropDTO> programmePropDTO = programmeFeignClient.getProgrammeProp(ProgrammePropType.SEMESTER,
            studentModuleSelection.get().getAcademicYear(),
            studentModuleSelection.get().getYearNo(),
            studentModuleSelection.get().getSemesterNo(),
            "factor");
        return Double.parseDouble(programmePropDTO.get().getValue());

    }

    // update QCS
    private void updateQCS(Long selectionId, Double mark, Double creditHour) {
        log.debug("Request to get QPV and GradeName by Mark: {}", mark);

        // get QPV and GradeName
        Double qcs;
        Double qpv= 0.00;
        String gradeName="";

        // todo what to input in the param in findAll(Pageable pageable) ?
        List<ModuleGrade> moduleGradeList = moduleGradeService.getAllModuleGradewithQcaAffected();

        for(ModuleGrade moduleGrade : moduleGradeList) {
            if(mark >= moduleGrade.getLowMarks()) {
                qpv = moduleGrade.getQpv();
                gradeName = moduleGrade.getName();
                return;
            }
        }
        qcs = qpv * creditHour;
        studentModuleSelectionRepository.updateByIdAndStudentModuleGradeTypeAndQcs(selectionId, gradeName, qcs);
    }


}

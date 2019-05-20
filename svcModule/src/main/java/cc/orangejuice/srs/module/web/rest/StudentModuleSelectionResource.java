package cc.orangejuice.srs.module.web.rest;

import cc.orangejuice.srs.module.service.StudentModuleSelectionService;
import cc.orangejuice.srs.module.service.dto.StudentModuleSelectionDTO;
import cc.orangejuice.srs.module.web.rest.errors.BadRequestAlertException;
import cc.orangejuice.srs.module.web.rest.util.HeaderUtil;
import cc.orangejuice.srs.module.web.rest.util.PaginationUtil;
import com.itextpdf.text.DocumentException;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing StudentModuleSelection.
 */
@RestController
@RequestMapping("/api")
public class StudentModuleSelectionResource {

    private final Logger log = LoggerFactory.getLogger(StudentModuleSelectionResource.class);

    private static final String ENTITY_NAME = "svcModuleStudentModuleSelection";

    private final StudentModuleSelectionService studentModuleSelectionService;

    public StudentModuleSelectionResource(StudentModuleSelectionService studentModuleSelectionService) {
        this.studentModuleSelectionService = studentModuleSelectionService;
    }

    /**
     * POST  /student-module-selections : Create a new studentModuleSelection.
     *
     * @param studentModuleSelectionDTO the studentModuleSelectionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new studentModuleSelectionDTO, or with status 400 (Bad Request) if the studentModuleSelection has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/student-module-selections")
    public ResponseEntity<StudentModuleSelectionDTO> createStudentModuleSelection(@Valid @RequestBody StudentModuleSelectionDTO studentModuleSelectionDTO) throws URISyntaxException {
        log.debug("REST request to save StudentModuleSelection : {}", studentModuleSelectionDTO);
        if (studentModuleSelectionDTO.getId() != null) {
            throw new BadRequestAlertException("A new studentModuleSelection cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StudentModuleSelectionDTO result = studentModuleSelectionService.save(studentModuleSelectionDTO);
        return ResponseEntity.created(new URI("/api/student-module-selections/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /student-module-selections : Updates an existing studentModuleSelection.
     *
     * @param studentModuleSelectionDTO the studentModuleSelectionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated studentModuleSelectionDTO,
     * or with status 400 (Bad Request) if the studentModuleSelectionDTO is not valid,
     * or with status 500 (Internal Server Error) if the studentModuleSelectionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/student-module-selections")
    public ResponseEntity<StudentModuleSelectionDTO> updateStudentModuleSelection(@Valid @RequestBody StudentModuleSelectionDTO studentModuleSelectionDTO) throws URISyntaxException {
        log.debug("REST request to update StudentModuleSelection : {}", studentModuleSelectionDTO);
        if (studentModuleSelectionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StudentModuleSelectionDTO result = studentModuleSelectionService.save(studentModuleSelectionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, studentModuleSelectionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /student-module-selections : get all the studentModuleSelections.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of studentModuleSelections in body
     */
    @GetMapping("/student-module-selections")
    public ResponseEntity<List<StudentModuleSelectionDTO>> getAllStudentModuleSelections(Pageable pageable) {
        log.debug("REST request to get a page of StudentModuleSelections");
        Page<StudentModuleSelectionDTO> page = studentModuleSelectionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/student-module-selections");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /student-module-selections/:id : get the "id" studentModuleSelection.
     *
     * @param id the id of the studentModuleSelectionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the studentModuleSelectionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/student-module-selections/{id}")
    public ResponseEntity<StudentModuleSelectionDTO> getStudentModuleSelection(@PathVariable Long id) {
        log.debug("REST request to get StudentModuleSelection : {}", id);
        Optional<StudentModuleSelectionDTO> studentModuleSelectionDTO = studentModuleSelectionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(studentModuleSelectionDTO);
    }

    /**
     * DELETE  /student-module-selections/:id : delete the "id" studentModuleSelection.
     *
     * @param id the id of the studentModuleSelectionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/student-module-selections/{id}")
    public ResponseEntity<Void> deleteStudentModuleSelection(@PathVariable Long id) {
        log.debug("REST request to delete StudentModuleSelection : {}", id);
        studentModuleSelectionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @PutMapping(value = "/student-module-selections/submit-mark", params={"selectionId", "mark"})
    public ResponseEntity<StudentModuleSelectionDTO> updateMarks(
        @RequestParam("selectionId") Long selectionId,
        @RequestParam("mark") Double mark) {

        log.debug("REST request to update id: {} StudentModuleSelections with mark {}", selectionId, mark);
        studentModuleSelectionService.updateMarkBySelectionIdAndMark(selectionId, mark);
        // check if one semester is finished
        studentModuleSelectionService.checkIfSemesterIsEnd(selectionId);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, selectionId.toString())).build();

    }

    @PutMapping(value = "/student-module-selections/clear-all-marks")
    public ResponseEntity<StudentModuleSelectionDTO> clearMarks() {

        log.debug("REST request to clear all marks");
        studentModuleSelectionService.clearAllMarks();
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, "data cleared")).build();

    }

    @GetMapping(value = "/student-module-selections/query")
    public List<StudentModuleSelectionDTO> getStudentModuleSelections(
        @RequestParam("studentId") Long studentId,
        @RequestParam("academicYear") Integer academicYear,
        @RequestParam("yearNo") Integer yearNo) {
        log.debug("REST request to get studentModuleSelection for the student {} in academicYear : {}, yearNo: {}",
            studentId, academicYear, yearNo);
        List<StudentModuleSelectionDTO> studentModuleSelectionDTO = studentModuleSelectionService.findAllStudentSelectionsByYear(studentId, academicYear, yearNo);
        return studentModuleSelectionDTO;
    }

    @GetMapping(value = "/student-module-selections/get-transcript")
    public ResponseEntity<Void> getTransctipt(
        @RequestParam("studentId") Long studentId,
        @RequestParam("academicYear") Integer academicYear,
        @RequestParam("academicSemester") Integer academicSemester) throws FileNotFoundException, DocumentException {

        log.debug("REST request to get transcript for the student {} in academicYear: {}, academicSemester: {} ",
            studentId, academicYear, academicYear, academicSemester);
        studentModuleSelectionService.getTranscript(studentId, academicYear, academicSemester);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, "transcript generated")).build();
    }




}

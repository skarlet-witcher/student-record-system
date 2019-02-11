package cc.orangejuice.srs.univ.course.module.web.rest;
import cc.orangejuice.srs.univ.course.module.domain.StudentModule;
import cc.orangejuice.srs.univ.course.module.service.StudentModuleService;
import cc.orangejuice.srs.univ.course.module.web.rest.errors.BadRequestAlertException;
import cc.orangejuice.srs.univ.course.module.web.rest.util.HeaderUtil;
import cc.orangejuice.srs.univ.course.module.web.rest.util.PaginationUtil;
import cc.orangejuice.srs.univ.course.module.service.dto.StudentModuleDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing StudentModule.
 */
@RestController
@RequestMapping("/api")
public class StudentModuleResource {

    private final Logger log = LoggerFactory.getLogger(StudentModuleResource.class);

    private static final String ENTITY_NAME = "svcUnivCourseModuleStudentModule";

    private final StudentModuleService studentModuleService;

    public StudentModuleResource(StudentModuleService studentModuleService) {
        this.studentModuleService = studentModuleService;
    }

    /**
     * POST  /student-modules : Create a new studentModule.
     *
     * @param studentModuleDTO the studentModuleDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new studentModuleDTO, or with status 400 (Bad Request) if the studentModule has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/student-modules")
    public ResponseEntity<StudentModuleDTO> createStudentModule(@Valid @RequestBody StudentModuleDTO studentModuleDTO) throws URISyntaxException {
        log.debug("REST request to save StudentModule : {}", studentModuleDTO);
        if (studentModuleDTO.getId() != null) {
            throw new BadRequestAlertException("A new studentModule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StudentModuleDTO result = studentModuleService.save(studentModuleDTO);
        return ResponseEntity.created(new URI("/api/student-modules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /student-modules : Updates an existing studentModule.
     *
     * @param studentModuleDTO the studentModuleDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated studentModuleDTO,
     * or with status 400 (Bad Request) if the studentModuleDTO is not valid,
     * or with status 500 (Internal Server Error) if the studentModuleDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/student-modules")
    public ResponseEntity<StudentModuleDTO> updateStudentModule(@Valid @RequestBody StudentModuleDTO studentModuleDTO) throws URISyntaxException {
        log.debug("REST request to update StudentModule : {}", studentModuleDTO);
        if (studentModuleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StudentModuleDTO result = studentModuleService.save(studentModuleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, studentModuleDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /student-modules : get all the studentModules.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of studentModules in body
     */
    @GetMapping("/student-modules")
    public ResponseEntity<List<StudentModuleDTO>> getAllStudentModules(Pageable pageable) {
        log.debug("REST request to get a page of StudentModules");
        Page<StudentModuleDTO> page = studentModuleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/student-modules");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /student-modules/:id : get the "id" studentModule.
     *
     * @param id the id of the studentModuleDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the studentModuleDTO, or with status 404 (Not Found)
     */
    @GetMapping("/student-modules/{id}")
    public ResponseEntity<StudentModuleDTO> getStudentModule(@PathVariable Long id) {
        log.debug("REST request to get StudentModule : {}", id);
        Optional<StudentModuleDTO> studentModuleDTO = studentModuleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(studentModuleDTO);
    }

    /**
     * DELETE  /student-modules/:id : delete the "id" studentModule.
     *
     * @param id the id of the studentModuleDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/student-modules/{id}")
    public ResponseEntity<Void> deleteStudentModule(@PathVariable Long id) {
        log.debug("REST request to delete StudentModule : {}", id);
        studentModuleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/student-modules/{studentId}/{enrollYear}/{enrollSemester}")
    public List<StudentModule> getStudentRegisteredModules(@PathVariable Long studentId, @PathVariable("enrollYear") Integer enrollYear, @PathVariable("enrollSemester") Integer enrollSemester) {
        log.debug("REST request to get {} StudentModules ", studentId);
        return studentModuleService.getStudentRegisteredModules(studentId, enrollYear, enrollSemester);
    }
}

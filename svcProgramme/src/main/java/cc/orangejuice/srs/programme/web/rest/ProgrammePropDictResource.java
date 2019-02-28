package cc.orangejuice.srs.programme.web.rest;
import cc.orangejuice.srs.programme.domain.enumeration.ProgrammePropType;
import cc.orangejuice.srs.programme.service.ProgrammePropDictService;
import cc.orangejuice.srs.programme.web.rest.errors.BadRequestAlertException;
import cc.orangejuice.srs.programme.web.rest.util.HeaderUtil;
import cc.orangejuice.srs.programme.web.rest.util.PaginationUtil;
import cc.orangejuice.srs.programme.service.dto.ProgrammePropDictDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ProgrammePropDict.
 */
@RestController
@RequestMapping("/api")
public class ProgrammePropDictResource {

    private final Logger log = LoggerFactory.getLogger(ProgrammePropDictResource.class);

    private static final String ENTITY_NAME = "svcProgrammeProgrammePropDict";

    private final ProgrammePropDictService programmePropDictService;

    public ProgrammePropDictResource(ProgrammePropDictService programmePropDictService) {
        this.programmePropDictService = programmePropDictService;
    }

    /**
     * POST  /programme-prop-dicts : Create a new programmePropDict.
     *
     * @param programmePropDictDTO the programmePropDictDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new programmePropDictDTO, or with status 400 (Bad Request) if the programmePropDict has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/programme-prop-dicts")
    public ResponseEntity<ProgrammePropDictDTO> createProgrammePropDict(@Valid @RequestBody ProgrammePropDictDTO programmePropDictDTO) throws URISyntaxException {
        log.debug("REST request to save ProgrammePropDict : {}", programmePropDictDTO);
        if (programmePropDictDTO.getId() != null) {
            throw new BadRequestAlertException("A new programmePropDict cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProgrammePropDictDTO result = programmePropDictService.save(programmePropDictDTO);
        return ResponseEntity.created(new URI("/api/programme-prop-dicts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /programme-prop-dicts : Updates an existing programmePropDict.
     *
     * @param programmePropDictDTO the programmePropDictDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated programmePropDictDTO,
     * or with status 400 (Bad Request) if the programmePropDictDTO is not valid,
     * or with status 500 (Internal Server Error) if the programmePropDictDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/programme-prop-dicts")
    public ResponseEntity<ProgrammePropDictDTO> updateProgrammePropDict(@Valid @RequestBody ProgrammePropDictDTO programmePropDictDTO) throws URISyntaxException {
        log.debug("REST request to update ProgrammePropDict : {}", programmePropDictDTO);
        if (programmePropDictDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProgrammePropDictDTO result = programmePropDictService.save(programmePropDictDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, programmePropDictDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /programme-prop-dicts : get all the programmePropDicts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of programmePropDicts in body
     */
    @GetMapping("/programme-prop-dicts")
    public ResponseEntity<List<ProgrammePropDictDTO>> getAllProgrammePropDicts(Pageable pageable) {
        log.debug("REST request to get a page of ProgrammePropDicts");
        Page<ProgrammePropDictDTO> page = programmePropDictService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/programme-prop-dicts");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /programme-prop-dicts/:id : get the "id" programmePropDict.
     *
     * @param id the id of the programmePropDictDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the programmePropDictDTO, or with status 404 (Not Found)
     */
    @GetMapping("/programme-prop-dicts/{id}")
    public ResponseEntity<ProgrammePropDictDTO> getProgrammePropDict(@PathVariable Long id) {
        log.debug("REST request to get ProgrammePropDict : {}", id);
        Optional<ProgrammePropDictDTO> programmePropDictDTO = programmePropDictService.findOne(id);
        return ResponseUtil.wrapOrNotFound(programmePropDictDTO);
    }

    /**
     * DELETE  /programme-prop-dicts/:id : delete the "id" programmePropDict.
     *
     * @param id the id of the programmePropDictDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/programme-prop-dicts/{id}")
    public ResponseEntity<Void> deleteProgrammePropDict(@PathVariable Long id) {
        log.debug("REST request to delete ProgrammePropDict : {}", id);
        programmePropDictService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping(value = "/programme-prop-dicts/", params = {"forEnrollYear", "type", "forSemesterNo", "key"})
    public ResponseEntity<ProgrammePropDictDTO> getProgrammePropDetailByEnrollYear(
        @RequestParam(value = "type", defaultValue = "GENERAL") ProgrammePropType type,
        @RequestParam(value = "forEnrollYear", required = false) Integer forEnrollYear,
        @RequestParam(value = "forYearNo", required = false) Integer forYearNo,
        @RequestParam(value = "forSemesterNo", required = false) Integer forSemesterNo,
        @RequestParam("key") String key) {
        log.debug("REST request to get year {} ,{}, {} and {} for ProgrammePropDict", forEnrollYear, type, forSemesterNo, key);
        Optional<ProgrammePropDictDTO> programmePropDictDTO;
        if(type == ProgrammePropType.GENERAL) {
            programmePropDictDTO = programmePropDictService.findOneForGeneral(type, forEnrollYear, key);
        } else if (type == ProgrammePropType.SEMESTER) {
            programmePropDictDTO = programmePropDictService.findOneForSemester(type, forEnrollYear, forSemesterNo, key);
        } else {
            programmePropDictDTO = programmePropDictService.findOneForYear(type, forEnrollYear, forYearNo, key);
        }
        return ResponseUtil.wrapOrNotFound(programmePropDictDTO);
    }
}

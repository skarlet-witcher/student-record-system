package cc.orangejuice.srs.programme.web.rest;

import cc.orangejuice.srs.programme.SvcProgrammeApp;

import cc.orangejuice.srs.programme.config.SecurityBeanOverrideConfiguration;

import cc.orangejuice.srs.programme.domain.Programme;
import cc.orangejuice.srs.programme.domain.Department;
import cc.orangejuice.srs.programme.repository.ProgrammeRepository;
import cc.orangejuice.srs.programme.service.ProgrammeService;
import cc.orangejuice.srs.programme.service.dto.ProgrammeDTO;
import cc.orangejuice.srs.programme.service.mapper.ProgrammeMapper;
import cc.orangejuice.srs.programme.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static cc.orangejuice.srs.programme.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ProgrammeResource REST controller.
 *
 * @see ProgrammeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, SvcProgrammeApp.class})
public class ProgrammeResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_LENGTH = 1;
    private static final Integer UPDATED_LENGTH = 2;

    private static final String DEFAULT_COURSE_LEADER = "AAAAAAAAAA";
    private static final String UPDATED_COURSE_LEADER = "BBBBBBBBBB";

    private static final String DEFAULT_DEGREE = "AAAAAAAAAA";
    private static final String UPDATED_DEGREE = "BBBBBBBBBB";

    @Autowired
    private ProgrammeRepository programmeRepository;

    @Autowired
    private ProgrammeMapper programmeMapper;

    @Autowired
    private ProgrammeService programmeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restProgrammeMockMvc;

    private Programme programme;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProgrammeResource programmeResource = new ProgrammeResource(programmeService);
        this.restProgrammeMockMvc = MockMvcBuilders.standaloneSetup(programmeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Programme createEntity(EntityManager em) {
        Programme programme = new Programme()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .length(DEFAULT_LENGTH)
            .courseLeader(DEFAULT_COURSE_LEADER)
            .degree(DEFAULT_DEGREE);
        // Add required entity
        Department department = DepartmentResourceIntTest.createEntity(em);
        em.persist(department);
        em.flush();
        programme.setDepartment(department);
        return programme;
    }

    @Before
    public void initTest() {
        programme = createEntity(em);
    }

    @Test
    @Transactional
    public void createProgramme() throws Exception {
        int databaseSizeBeforeCreate = programmeRepository.findAll().size();

        // Create the Programme
        ProgrammeDTO programmeDTO = programmeMapper.toDto(programme);
        restProgrammeMockMvc.perform(post("/api/programmes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(programmeDTO)))
            .andExpect(status().isCreated());

        // Validate the Programme in the database
        List<Programme> programmeList = programmeRepository.findAll();
        assertThat(programmeList).hasSize(databaseSizeBeforeCreate + 1);
        Programme testProgramme = programmeList.get(programmeList.size() - 1);
        assertThat(testProgramme.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testProgramme.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProgramme.getLength()).isEqualTo(DEFAULT_LENGTH);
        assertThat(testProgramme.getCourseLeader()).isEqualTo(DEFAULT_COURSE_LEADER);
        assertThat(testProgramme.getDegree()).isEqualTo(DEFAULT_DEGREE);
    }

    @Test
    @Transactional
    public void createProgrammeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = programmeRepository.findAll().size();

        // Create the Programme with an existing ID
        programme.setId(1L);
        ProgrammeDTO programmeDTO = programmeMapper.toDto(programme);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProgrammeMockMvc.perform(post("/api/programmes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(programmeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Programme in the database
        List<Programme> programmeList = programmeRepository.findAll();
        assertThat(programmeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = programmeRepository.findAll().size();
        // set the field null
        programme.setCode(null);

        // Create the Programme, which fails.
        ProgrammeDTO programmeDTO = programmeMapper.toDto(programme);

        restProgrammeMockMvc.perform(post("/api/programmes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(programmeDTO)))
            .andExpect(status().isBadRequest());

        List<Programme> programmeList = programmeRepository.findAll();
        assertThat(programmeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = programmeRepository.findAll().size();
        // set the field null
        programme.setName(null);

        // Create the Programme, which fails.
        ProgrammeDTO programmeDTO = programmeMapper.toDto(programme);

        restProgrammeMockMvc.perform(post("/api/programmes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(programmeDTO)))
            .andExpect(status().isBadRequest());

        List<Programme> programmeList = programmeRepository.findAll();
        assertThat(programmeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLengthIsRequired() throws Exception {
        int databaseSizeBeforeTest = programmeRepository.findAll().size();
        // set the field null
        programme.setLength(null);

        // Create the Programme, which fails.
        ProgrammeDTO programmeDTO = programmeMapper.toDto(programme);

        restProgrammeMockMvc.perform(post("/api/programmes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(programmeDTO)))
            .andExpect(status().isBadRequest());

        List<Programme> programmeList = programmeRepository.findAll();
        assertThat(programmeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCourseLeaderIsRequired() throws Exception {
        int databaseSizeBeforeTest = programmeRepository.findAll().size();
        // set the field null
        programme.setCourseLeader(null);

        // Create the Programme, which fails.
        ProgrammeDTO programmeDTO = programmeMapper.toDto(programme);

        restProgrammeMockMvc.perform(post("/api/programmes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(programmeDTO)))
            .andExpect(status().isBadRequest());

        List<Programme> programmeList = programmeRepository.findAll();
        assertThat(programmeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDegreeIsRequired() throws Exception {
        int databaseSizeBeforeTest = programmeRepository.findAll().size();
        // set the field null
        programme.setDegree(null);

        // Create the Programme, which fails.
        ProgrammeDTO programmeDTO = programmeMapper.toDto(programme);

        restProgrammeMockMvc.perform(post("/api/programmes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(programmeDTO)))
            .andExpect(status().isBadRequest());

        List<Programme> programmeList = programmeRepository.findAll();
        assertThat(programmeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProgrammes() throws Exception {
        // Initialize the database
        programmeRepository.saveAndFlush(programme);

        // Get all the programmeList
        restProgrammeMockMvc.perform(get("/api/programmes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(programme.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].length").value(hasItem(DEFAULT_LENGTH)))
            .andExpect(jsonPath("$.[*].courseLeader").value(hasItem(DEFAULT_COURSE_LEADER.toString())))
            .andExpect(jsonPath("$.[*].degree").value(hasItem(DEFAULT_DEGREE.toString())));
    }
    
    @Test
    @Transactional
    public void getProgramme() throws Exception {
        // Initialize the database
        programmeRepository.saveAndFlush(programme);

        // Get the programme
        restProgrammeMockMvc.perform(get("/api/programmes/{id}", programme.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(programme.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.length").value(DEFAULT_LENGTH))
            .andExpect(jsonPath("$.courseLeader").value(DEFAULT_COURSE_LEADER.toString()))
            .andExpect(jsonPath("$.degree").value(DEFAULT_DEGREE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProgramme() throws Exception {
        // Get the programme
        restProgrammeMockMvc.perform(get("/api/programmes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProgramme() throws Exception {
        // Initialize the database
        programmeRepository.saveAndFlush(programme);

        int databaseSizeBeforeUpdate = programmeRepository.findAll().size();

        // Update the programme
        Programme updatedProgramme = programmeRepository.findById(programme.getId()).get();
        // Disconnect from session so that the updates on updatedProgramme are not directly saved in db
        em.detach(updatedProgramme);
        updatedProgramme
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .length(UPDATED_LENGTH)
            .courseLeader(UPDATED_COURSE_LEADER)
            .degree(UPDATED_DEGREE);
        ProgrammeDTO programmeDTO = programmeMapper.toDto(updatedProgramme);

        restProgrammeMockMvc.perform(put("/api/programmes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(programmeDTO)))
            .andExpect(status().isOk());

        // Validate the Programme in the database
        List<Programme> programmeList = programmeRepository.findAll();
        assertThat(programmeList).hasSize(databaseSizeBeforeUpdate);
        Programme testProgramme = programmeList.get(programmeList.size() - 1);
        assertThat(testProgramme.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testProgramme.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProgramme.getLength()).isEqualTo(UPDATED_LENGTH);
        assertThat(testProgramme.getCourseLeader()).isEqualTo(UPDATED_COURSE_LEADER);
        assertThat(testProgramme.getDegree()).isEqualTo(UPDATED_DEGREE);
    }

    @Test
    @Transactional
    public void updateNonExistingProgramme() throws Exception {
        int databaseSizeBeforeUpdate = programmeRepository.findAll().size();

        // Create the Programme
        ProgrammeDTO programmeDTO = programmeMapper.toDto(programme);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProgrammeMockMvc.perform(put("/api/programmes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(programmeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Programme in the database
        List<Programme> programmeList = programmeRepository.findAll();
        assertThat(programmeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProgramme() throws Exception {
        // Initialize the database
        programmeRepository.saveAndFlush(programme);

        int databaseSizeBeforeDelete = programmeRepository.findAll().size();

        // Delete the programme
        restProgrammeMockMvc.perform(delete("/api/programmes/{id}", programme.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Programme> programmeList = programmeRepository.findAll();
        assertThat(programmeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Programme.class);
        Programme programme1 = new Programme();
        programme1.setId(1L);
        Programme programme2 = new Programme();
        programme2.setId(programme1.getId());
        assertThat(programme1).isEqualTo(programme2);
        programme2.setId(2L);
        assertThat(programme1).isNotEqualTo(programme2);
        programme1.setId(null);
        assertThat(programme1).isNotEqualTo(programme2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProgrammeDTO.class);
        ProgrammeDTO programmeDTO1 = new ProgrammeDTO();
        programmeDTO1.setId(1L);
        ProgrammeDTO programmeDTO2 = new ProgrammeDTO();
        assertThat(programmeDTO1).isNotEqualTo(programmeDTO2);
        programmeDTO2.setId(programmeDTO1.getId());
        assertThat(programmeDTO1).isEqualTo(programmeDTO2);
        programmeDTO2.setId(2L);
        assertThat(programmeDTO1).isNotEqualTo(programmeDTO2);
        programmeDTO1.setId(null);
        assertThat(programmeDTO1).isNotEqualTo(programmeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(programmeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(programmeMapper.fromId(null)).isNull();
    }
}

package cc.orangejuice.srs.programme.web.rest;

import cc.orangejuice.srs.programme.SvcProgrammeApp;

import cc.orangejuice.srs.programme.config.SecurityBeanOverrideConfiguration;

import cc.orangejuice.srs.programme.domain.ProgrammeProp;
import cc.orangejuice.srs.programme.repository.ProgrammePropRepository;
import cc.orangejuice.srs.programme.service.ProgrammePropService;
import cc.orangejuice.srs.programme.service.dto.ProgrammePropDTO;
import cc.orangejuice.srs.programme.service.mapper.ProgrammePropMapper;
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

import cc.orangejuice.srs.programme.domain.enumeration.ProgrammePropType;
/**
 * Test class for the ProgrammePropResource REST controller.
 *
 * @see ProgrammePropResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, SvcProgrammeApp.class})
public class ProgrammePropResourceIntTest {

    private static final Integer DEFAULT_FOR_ENROLL_YEAR = 1;
    private static final Integer UPDATED_FOR_ENROLL_YEAR = 2;

    private static final ProgrammePropType DEFAULT_TYPE = ProgrammePropType.GENERAL;
    private static final ProgrammePropType UPDATED_TYPE = ProgrammePropType.YEAR;

    private static final Integer DEFAULT_FOR_YEAR_NO = 1;
    private static final Integer UPDATED_FOR_YEAR_NO = 2;

    private static final Integer DEFAULT_FOR_SEMESTER_NO = 1;
    private static final Integer UPDATED_FOR_SEMESTER_NO = 2;

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private ProgrammePropRepository programmePropRepository;

    @Autowired
    private ProgrammePropMapper programmePropMapper;

    @Autowired
    private ProgrammePropService programmePropService;

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

    private MockMvc restProgrammePropMockMvc;

    private ProgrammeProp programmeProp;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProgrammePropResource programmePropResource = new ProgrammePropResource(programmePropService);
        this.restProgrammePropMockMvc = MockMvcBuilders.standaloneSetup(programmePropResource)
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
    public static ProgrammeProp createEntity(EntityManager em) {
        ProgrammeProp programmeProp = new ProgrammeProp()
            .forEnrollYear(DEFAULT_FOR_ENROLL_YEAR)
            .type(DEFAULT_TYPE)
            .forYearNo(DEFAULT_FOR_YEAR_NO)
            .forSemesterNo(DEFAULT_FOR_SEMESTER_NO)
            .key(DEFAULT_KEY)
            .value(DEFAULT_VALUE);
        return programmeProp;
    }

    @Before
    public void initTest() {
        programmeProp = createEntity(em);
    }

    @Test
    @Transactional
    public void createProgrammeProp() throws Exception {
        int databaseSizeBeforeCreate = programmePropRepository.findAll().size();

        // Create the ProgrammeProp
        ProgrammePropDTO programmePropDTO = programmePropMapper.toDto(programmeProp);
        restProgrammePropMockMvc.perform(post("/api/programme-props")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(programmePropDTO)))
            .andExpect(status().isCreated());

        // Validate the ProgrammeProp in the database
        List<ProgrammeProp> programmePropList = programmePropRepository.findAll();
        assertThat(programmePropList).hasSize(databaseSizeBeforeCreate + 1);
        ProgrammeProp testProgrammeProp = programmePropList.get(programmePropList.size() - 1);
        assertThat(testProgrammeProp.getForEnrollYear()).isEqualTo(DEFAULT_FOR_ENROLL_YEAR);
        assertThat(testProgrammeProp.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testProgrammeProp.getForYearNo()).isEqualTo(DEFAULT_FOR_YEAR_NO);
        assertThat(testProgrammeProp.getForSemesterNo()).isEqualTo(DEFAULT_FOR_SEMESTER_NO);
        assertThat(testProgrammeProp.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testProgrammeProp.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createProgrammePropWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = programmePropRepository.findAll().size();

        // Create the ProgrammeProp with an existing ID
        programmeProp.setId(1L);
        ProgrammePropDTO programmePropDTO = programmePropMapper.toDto(programmeProp);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProgrammePropMockMvc.perform(post("/api/programme-props")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(programmePropDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProgrammeProp in the database
        List<ProgrammeProp> programmePropList = programmePropRepository.findAll();
        assertThat(programmePropList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkForEnrollYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = programmePropRepository.findAll().size();
        // set the field null
        programmeProp.setForEnrollYear(null);

        // Create the ProgrammeProp, which fails.
        ProgrammePropDTO programmePropDTO = programmePropMapper.toDto(programmeProp);

        restProgrammePropMockMvc.perform(post("/api/programme-props")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(programmePropDTO)))
            .andExpect(status().isBadRequest());

        List<ProgrammeProp> programmePropList = programmePropRepository.findAll();
        assertThat(programmePropList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = programmePropRepository.findAll().size();
        // set the field null
        programmeProp.setType(null);

        // Create the ProgrammeProp, which fails.
        ProgrammePropDTO programmePropDTO = programmePropMapper.toDto(programmeProp);

        restProgrammePropMockMvc.perform(post("/api/programme-props")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(programmePropDTO)))
            .andExpect(status().isBadRequest());

        List<ProgrammeProp> programmePropList = programmePropRepository.findAll();
        assertThat(programmePropList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = programmePropRepository.findAll().size();
        // set the field null
        programmeProp.setKey(null);

        // Create the ProgrammeProp, which fails.
        ProgrammePropDTO programmePropDTO = programmePropMapper.toDto(programmeProp);

        restProgrammePropMockMvc.perform(post("/api/programme-props")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(programmePropDTO)))
            .andExpect(status().isBadRequest());

        List<ProgrammeProp> programmePropList = programmePropRepository.findAll();
        assertThat(programmePropList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = programmePropRepository.findAll().size();
        // set the field null
        programmeProp.setValue(null);

        // Create the ProgrammeProp, which fails.
        ProgrammePropDTO programmePropDTO = programmePropMapper.toDto(programmeProp);

        restProgrammePropMockMvc.perform(post("/api/programme-props")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(programmePropDTO)))
            .andExpect(status().isBadRequest());

        List<ProgrammeProp> programmePropList = programmePropRepository.findAll();
        assertThat(programmePropList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProgrammeProps() throws Exception {
        // Initialize the database
        programmePropRepository.saveAndFlush(programmeProp);

        // Get all the programmePropList
        restProgrammePropMockMvc.perform(get("/api/programme-props?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(programmeProp.getId().intValue())))
            .andExpect(jsonPath("$.[*].forEnrollYear").value(hasItem(DEFAULT_FOR_ENROLL_YEAR)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].forYearNo").value(hasItem(DEFAULT_FOR_YEAR_NO)))
            .andExpect(jsonPath("$.[*].forSemesterNo").value(hasItem(DEFAULT_FOR_SEMESTER_NO)))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }
    
    @Test
    @Transactional
    public void getProgrammeProp() throws Exception {
        // Initialize the database
        programmePropRepository.saveAndFlush(programmeProp);

        // Get the programmeProp
        restProgrammePropMockMvc.perform(get("/api/programme-props/{id}", programmeProp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(programmeProp.getId().intValue()))
            .andExpect(jsonPath("$.forEnrollYear").value(DEFAULT_FOR_ENROLL_YEAR))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.forYearNo").value(DEFAULT_FOR_YEAR_NO))
            .andExpect(jsonPath("$.forSemesterNo").value(DEFAULT_FOR_SEMESTER_NO))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProgrammeProp() throws Exception {
        // Get the programmeProp
        restProgrammePropMockMvc.perform(get("/api/programme-props/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProgrammeProp() throws Exception {
        // Initialize the database
        programmePropRepository.saveAndFlush(programmeProp);

        int databaseSizeBeforeUpdate = programmePropRepository.findAll().size();

        // Update the programmeProp
        ProgrammeProp updatedProgrammeProp = programmePropRepository.findById(programmeProp.getId()).get();
        // Disconnect from session so that the updates on updatedProgrammeProp are not directly saved in db
        em.detach(updatedProgrammeProp);
        updatedProgrammeProp
            .forEnrollYear(UPDATED_FOR_ENROLL_YEAR)
            .type(UPDATED_TYPE)
            .forYearNo(UPDATED_FOR_YEAR_NO)
            .forSemesterNo(UPDATED_FOR_SEMESTER_NO)
            .key(UPDATED_KEY)
            .value(UPDATED_VALUE);
        ProgrammePropDTO programmePropDTO = programmePropMapper.toDto(updatedProgrammeProp);

        restProgrammePropMockMvc.perform(put("/api/programme-props")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(programmePropDTO)))
            .andExpect(status().isOk());

        // Validate the ProgrammeProp in the database
        List<ProgrammeProp> programmePropList = programmePropRepository.findAll();
        assertThat(programmePropList).hasSize(databaseSizeBeforeUpdate);
        ProgrammeProp testProgrammeProp = programmePropList.get(programmePropList.size() - 1);
        assertThat(testProgrammeProp.getForEnrollYear()).isEqualTo(UPDATED_FOR_ENROLL_YEAR);
        assertThat(testProgrammeProp.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testProgrammeProp.getForYearNo()).isEqualTo(UPDATED_FOR_YEAR_NO);
        assertThat(testProgrammeProp.getForSemesterNo()).isEqualTo(UPDATED_FOR_SEMESTER_NO);
        assertThat(testProgrammeProp.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testProgrammeProp.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingProgrammeProp() throws Exception {
        int databaseSizeBeforeUpdate = programmePropRepository.findAll().size();

        // Create the ProgrammeProp
        ProgrammePropDTO programmePropDTO = programmePropMapper.toDto(programmeProp);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProgrammePropMockMvc.perform(put("/api/programme-props")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(programmePropDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProgrammeProp in the database
        List<ProgrammeProp> programmePropList = programmePropRepository.findAll();
        assertThat(programmePropList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProgrammeProp() throws Exception {
        // Initialize the database
        programmePropRepository.saveAndFlush(programmeProp);

        int databaseSizeBeforeDelete = programmePropRepository.findAll().size();

        // Delete the programmeProp
        restProgrammePropMockMvc.perform(delete("/api/programme-props/{id}", programmeProp.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProgrammeProp> programmePropList = programmePropRepository.findAll();
        assertThat(programmePropList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProgrammeProp.class);
        ProgrammeProp programmeProp1 = new ProgrammeProp();
        programmeProp1.setId(1L);
        ProgrammeProp programmeProp2 = new ProgrammeProp();
        programmeProp2.setId(programmeProp1.getId());
        assertThat(programmeProp1).isEqualTo(programmeProp2);
        programmeProp2.setId(2L);
        assertThat(programmeProp1).isNotEqualTo(programmeProp2);
        programmeProp1.setId(null);
        assertThat(programmeProp1).isNotEqualTo(programmeProp2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProgrammePropDTO.class);
        ProgrammePropDTO programmePropDTO1 = new ProgrammePropDTO();
        programmePropDTO1.setId(1L);
        ProgrammePropDTO programmePropDTO2 = new ProgrammePropDTO();
        assertThat(programmePropDTO1).isNotEqualTo(programmePropDTO2);
        programmePropDTO2.setId(programmePropDTO1.getId());
        assertThat(programmePropDTO1).isEqualTo(programmePropDTO2);
        programmePropDTO2.setId(2L);
        assertThat(programmePropDTO1).isNotEqualTo(programmePropDTO2);
        programmePropDTO1.setId(null);
        assertThat(programmePropDTO1).isNotEqualTo(programmePropDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(programmePropMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(programmePropMapper.fromId(null)).isNull();
    }
}

package cc.orangejuice.srs.programme.web.rest;

import cc.orangejuice.srs.programme.SvcProgrammeApp;

import cc.orangejuice.srs.programme.config.SecurityBeanOverrideConfiguration;

import cc.orangejuice.srs.programme.domain.ProgrammePropDict;
import cc.orangejuice.srs.programme.repository.ProgrammePropDictRepository;
import cc.orangejuice.srs.programme.service.ProgrammePropDictService;
import cc.orangejuice.srs.programme.service.dto.ProgrammePropDictDTO;
import cc.orangejuice.srs.programme.service.mapper.ProgrammePropDictMapper;
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
 * Test class for the ProgrammePropDictResource REST controller.
 *
 * @see ProgrammePropDictResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, SvcProgrammeApp.class})
public class ProgrammePropDictResourceIntTest {

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
    private ProgrammePropDictRepository programmePropDictRepository;

    @Autowired
    private ProgrammePropDictMapper programmePropDictMapper;

    @Autowired
    private ProgrammePropDictService programmePropDictService;

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

    private MockMvc restProgrammePropDictMockMvc;

    private ProgrammePropDict programmePropDict;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProgrammePropDictResource programmePropDictResource = new ProgrammePropDictResource(programmePropDictService);
        this.restProgrammePropDictMockMvc = MockMvcBuilders.standaloneSetup(programmePropDictResource)
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
    public static ProgrammePropDict createEntity(EntityManager em) {
        ProgrammePropDict programmePropDict = new ProgrammePropDict()
            .forEnrollYear(DEFAULT_FOR_ENROLL_YEAR)
            .type(DEFAULT_TYPE)
            .forYearNo(DEFAULT_FOR_YEAR_NO)
            .forSemesterNo(DEFAULT_FOR_SEMESTER_NO)
            .key(DEFAULT_KEY)
            .value(DEFAULT_VALUE);
        return programmePropDict;
    }

    @Before
    public void initTest() {
        programmePropDict = createEntity(em);
    }

    @Test
    @Transactional
    public void createProgrammePropDict() throws Exception {
        int databaseSizeBeforeCreate = programmePropDictRepository.findAll().size();

        // Create the ProgrammePropDict
        ProgrammePropDictDTO programmePropDictDTO = programmePropDictMapper.toDto(programmePropDict);
        restProgrammePropDictMockMvc.perform(post("/api/programme-prop-dicts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(programmePropDictDTO)))
            .andExpect(status().isCreated());

        // Validate the ProgrammePropDict in the database
        List<ProgrammePropDict> programmePropDictList = programmePropDictRepository.findAll();
        assertThat(programmePropDictList).hasSize(databaseSizeBeforeCreate + 1);
        ProgrammePropDict testProgrammePropDict = programmePropDictList.get(programmePropDictList.size() - 1);
        assertThat(testProgrammePropDict.getForEnrollYear()).isEqualTo(DEFAULT_FOR_ENROLL_YEAR);
        assertThat(testProgrammePropDict.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testProgrammePropDict.getForYearNo()).isEqualTo(DEFAULT_FOR_YEAR_NO);
        assertThat(testProgrammePropDict.getForSemesterNo()).isEqualTo(DEFAULT_FOR_SEMESTER_NO);
        assertThat(testProgrammePropDict.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testProgrammePropDict.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createProgrammePropDictWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = programmePropDictRepository.findAll().size();

        // Create the ProgrammePropDict with an existing ID
        programmePropDict.setId(1L);
        ProgrammePropDictDTO programmePropDictDTO = programmePropDictMapper.toDto(programmePropDict);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProgrammePropDictMockMvc.perform(post("/api/programme-prop-dicts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(programmePropDictDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProgrammePropDict in the database
        List<ProgrammePropDict> programmePropDictList = programmePropDictRepository.findAll();
        assertThat(programmePropDictList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkForEnrollYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = programmePropDictRepository.findAll().size();
        // set the field null
        programmePropDict.setForEnrollYear(null);

        // Create the ProgrammePropDict, which fails.
        ProgrammePropDictDTO programmePropDictDTO = programmePropDictMapper.toDto(programmePropDict);

        restProgrammePropDictMockMvc.perform(post("/api/programme-prop-dicts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(programmePropDictDTO)))
            .andExpect(status().isBadRequest());

        List<ProgrammePropDict> programmePropDictList = programmePropDictRepository.findAll();
        assertThat(programmePropDictList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = programmePropDictRepository.findAll().size();
        // set the field null
        programmePropDict.setType(null);

        // Create the ProgrammePropDict, which fails.
        ProgrammePropDictDTO programmePropDictDTO = programmePropDictMapper.toDto(programmePropDict);

        restProgrammePropDictMockMvc.perform(post("/api/programme-prop-dicts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(programmePropDictDTO)))
            .andExpect(status().isBadRequest());

        List<ProgrammePropDict> programmePropDictList = programmePropDictRepository.findAll();
        assertThat(programmePropDictList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = programmePropDictRepository.findAll().size();
        // set the field null
        programmePropDict.setKey(null);

        // Create the ProgrammePropDict, which fails.
        ProgrammePropDictDTO programmePropDictDTO = programmePropDictMapper.toDto(programmePropDict);

        restProgrammePropDictMockMvc.perform(post("/api/programme-prop-dicts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(programmePropDictDTO)))
            .andExpect(status().isBadRequest());

        List<ProgrammePropDict> programmePropDictList = programmePropDictRepository.findAll();
        assertThat(programmePropDictList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = programmePropDictRepository.findAll().size();
        // set the field null
        programmePropDict.setValue(null);

        // Create the ProgrammePropDict, which fails.
        ProgrammePropDictDTO programmePropDictDTO = programmePropDictMapper.toDto(programmePropDict);

        restProgrammePropDictMockMvc.perform(post("/api/programme-prop-dicts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(programmePropDictDTO)))
            .andExpect(status().isBadRequest());

        List<ProgrammePropDict> programmePropDictList = programmePropDictRepository.findAll();
        assertThat(programmePropDictList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProgrammePropDicts() throws Exception {
        // Initialize the database
        programmePropDictRepository.saveAndFlush(programmePropDict);

        // Get all the programmePropDictList
        restProgrammePropDictMockMvc.perform(get("/api/programme-prop-dicts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(programmePropDict.getId().intValue())))
            .andExpect(jsonPath("$.[*].forEnrollYear").value(hasItem(DEFAULT_FOR_ENROLL_YEAR)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].forYearNo").value(hasItem(DEFAULT_FOR_YEAR_NO)))
            .andExpect(jsonPath("$.[*].forSemesterNo").value(hasItem(DEFAULT_FOR_SEMESTER_NO)))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }
    
    @Test
    @Transactional
    public void getProgrammePropDict() throws Exception {
        // Initialize the database
        programmePropDictRepository.saveAndFlush(programmePropDict);

        // Get the programmePropDict
        restProgrammePropDictMockMvc.perform(get("/api/programme-prop-dicts/{id}", programmePropDict.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(programmePropDict.getId().intValue()))
            .andExpect(jsonPath("$.forEnrollYear").value(DEFAULT_FOR_ENROLL_YEAR))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.forYearNo").value(DEFAULT_FOR_YEAR_NO))
            .andExpect(jsonPath("$.forSemesterNo").value(DEFAULT_FOR_SEMESTER_NO))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProgrammePropDict() throws Exception {
        // Get the programmePropDict
        restProgrammePropDictMockMvc.perform(get("/api/programme-prop-dicts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProgrammePropDict() throws Exception {
        // Initialize the database
        programmePropDictRepository.saveAndFlush(programmePropDict);

        int databaseSizeBeforeUpdate = programmePropDictRepository.findAll().size();

        // Update the programmePropDict
        ProgrammePropDict updatedProgrammePropDict = programmePropDictRepository.findById(programmePropDict.getId()).get();
        // Disconnect from session so that the updates on updatedProgrammePropDict are not directly saved in db
        em.detach(updatedProgrammePropDict);
        updatedProgrammePropDict
            .forEnrollYear(UPDATED_FOR_ENROLL_YEAR)
            .type(UPDATED_TYPE)
            .forYearNo(UPDATED_FOR_YEAR_NO)
            .forSemesterNo(UPDATED_FOR_SEMESTER_NO)
            .key(UPDATED_KEY)
            .value(UPDATED_VALUE);
        ProgrammePropDictDTO programmePropDictDTO = programmePropDictMapper.toDto(updatedProgrammePropDict);

        restProgrammePropDictMockMvc.perform(put("/api/programme-prop-dicts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(programmePropDictDTO)))
            .andExpect(status().isOk());

        // Validate the ProgrammePropDict in the database
        List<ProgrammePropDict> programmePropDictList = programmePropDictRepository.findAll();
        assertThat(programmePropDictList).hasSize(databaseSizeBeforeUpdate);
        ProgrammePropDict testProgrammePropDict = programmePropDictList.get(programmePropDictList.size() - 1);
        assertThat(testProgrammePropDict.getForEnrollYear()).isEqualTo(UPDATED_FOR_ENROLL_YEAR);
        assertThat(testProgrammePropDict.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testProgrammePropDict.getForYearNo()).isEqualTo(UPDATED_FOR_YEAR_NO);
        assertThat(testProgrammePropDict.getForSemesterNo()).isEqualTo(UPDATED_FOR_SEMESTER_NO);
        assertThat(testProgrammePropDict.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testProgrammePropDict.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingProgrammePropDict() throws Exception {
        int databaseSizeBeforeUpdate = programmePropDictRepository.findAll().size();

        // Create the ProgrammePropDict
        ProgrammePropDictDTO programmePropDictDTO = programmePropDictMapper.toDto(programmePropDict);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProgrammePropDictMockMvc.perform(put("/api/programme-prop-dicts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(programmePropDictDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProgrammePropDict in the database
        List<ProgrammePropDict> programmePropDictList = programmePropDictRepository.findAll();
        assertThat(programmePropDictList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProgrammePropDict() throws Exception {
        // Initialize the database
        programmePropDictRepository.saveAndFlush(programmePropDict);

        int databaseSizeBeforeDelete = programmePropDictRepository.findAll().size();

        // Delete the programmePropDict
        restProgrammePropDictMockMvc.perform(delete("/api/programme-prop-dicts/{id}", programmePropDict.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProgrammePropDict> programmePropDictList = programmePropDictRepository.findAll();
        assertThat(programmePropDictList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProgrammePropDict.class);
        ProgrammePropDict programmePropDict1 = new ProgrammePropDict();
        programmePropDict1.setId(1L);
        ProgrammePropDict programmePropDict2 = new ProgrammePropDict();
        programmePropDict2.setId(programmePropDict1.getId());
        assertThat(programmePropDict1).isEqualTo(programmePropDict2);
        programmePropDict2.setId(2L);
        assertThat(programmePropDict1).isNotEqualTo(programmePropDict2);
        programmePropDict1.setId(null);
        assertThat(programmePropDict1).isNotEqualTo(programmePropDict2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProgrammePropDictDTO.class);
        ProgrammePropDictDTO programmePropDictDTO1 = new ProgrammePropDictDTO();
        programmePropDictDTO1.setId(1L);
        ProgrammePropDictDTO programmePropDictDTO2 = new ProgrammePropDictDTO();
        assertThat(programmePropDictDTO1).isNotEqualTo(programmePropDictDTO2);
        programmePropDictDTO2.setId(programmePropDictDTO1.getId());
        assertThat(programmePropDictDTO1).isEqualTo(programmePropDictDTO2);
        programmePropDictDTO2.setId(2L);
        assertThat(programmePropDictDTO1).isNotEqualTo(programmePropDictDTO2);
        programmePropDictDTO1.setId(null);
        assertThat(programmePropDictDTO1).isNotEqualTo(programmePropDictDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(programmePropDictMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(programmePropDictMapper.fromId(null)).isNull();
    }
}

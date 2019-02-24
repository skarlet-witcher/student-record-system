package cc.orangejuice.srs.programme.module.web.rest;

import cc.orangejuice.srs.programme.module.SvcProgrammeModuleApp;

import cc.orangejuice.srs.programme.module.config.SecurityBeanOverrideConfiguration;

import cc.orangejuice.srs.programme.module.domain.StudentModuleGradeDict;
import cc.orangejuice.srs.programme.module.repository.StudentModuleGradeDictRepository;
import cc.orangejuice.srs.programme.module.service.StudentModuleGradeDictService;
import cc.orangejuice.srs.programme.module.service.dto.StudentModuleGradeDictDTO;
import cc.orangejuice.srs.programme.module.service.mapper.StudentModuleGradeDictMapper;
import cc.orangejuice.srs.programme.module.web.rest.errors.ExceptionTranslator;

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


import static cc.orangejuice.srs.programme.module.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the StudentModuleGradeDictResource REST controller.
 *
 * @see StudentModuleGradeDictResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, SvcProgrammeModuleApp.class})
public class StudentModuleGradeDictResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_LOW_MARKS = 1;
    private static final Integer UPDATED_LOW_MARKS = 2;

    private static final Double DEFAULT_QPV = 1D;
    private static final Double UPDATED_QPV = 2D;

    private static final Boolean DEFAULT_IS_AFFECT_QCA = false;
    private static final Boolean UPDATED_IS_AFFECT_QCA = true;

    @Autowired
    private StudentModuleGradeDictRepository studentModuleGradeDictRepository;

    @Autowired
    private StudentModuleGradeDictMapper studentModuleGradeDictMapper;

    @Autowired
    private StudentModuleGradeDictService studentModuleGradeDictService;

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

    private MockMvc restStudentModuleGradeDictMockMvc;

    private StudentModuleGradeDict studentModuleGradeDict;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StudentModuleGradeDictResource studentModuleGradeDictResource = new StudentModuleGradeDictResource(studentModuleGradeDictService);
        this.restStudentModuleGradeDictMockMvc = MockMvcBuilders.standaloneSetup(studentModuleGradeDictResource)
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
    public static StudentModuleGradeDict createEntity(EntityManager em) {
        StudentModuleGradeDict studentModuleGradeDict = new StudentModuleGradeDict()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .lowMarks(DEFAULT_LOW_MARKS)
            .qpv(DEFAULT_QPV)
            .isAffectQca(DEFAULT_IS_AFFECT_QCA);
        return studentModuleGradeDict;
    }

    @Before
    public void initTest() {
        studentModuleGradeDict = createEntity(em);
    }

    @Test
    @Transactional
    public void createStudentModuleGradeDict() throws Exception {
        int databaseSizeBeforeCreate = studentModuleGradeDictRepository.findAll().size();

        // Create the StudentModuleGradeDict
        StudentModuleGradeDictDTO studentModuleGradeDictDTO = studentModuleGradeDictMapper.toDto(studentModuleGradeDict);
        restStudentModuleGradeDictMockMvc.perform(post("/api/student-module-grade-dicts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentModuleGradeDictDTO)))
            .andExpect(status().isCreated());

        // Validate the StudentModuleGradeDict in the database
        List<StudentModuleGradeDict> studentModuleGradeDictList = studentModuleGradeDictRepository.findAll();
        assertThat(studentModuleGradeDictList).hasSize(databaseSizeBeforeCreate + 1);
        StudentModuleGradeDict testStudentModuleGradeDict = studentModuleGradeDictList.get(studentModuleGradeDictList.size() - 1);
        assertThat(testStudentModuleGradeDict.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testStudentModuleGradeDict.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testStudentModuleGradeDict.getLowMarks()).isEqualTo(DEFAULT_LOW_MARKS);
        assertThat(testStudentModuleGradeDict.getQpv()).isEqualTo(DEFAULT_QPV);
        assertThat(testStudentModuleGradeDict.isIsAffectQca()).isEqualTo(DEFAULT_IS_AFFECT_QCA);
    }

    @Test
    @Transactional
    public void createStudentModuleGradeDictWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = studentModuleGradeDictRepository.findAll().size();

        // Create the StudentModuleGradeDict with an existing ID
        studentModuleGradeDict.setId(1L);
        StudentModuleGradeDictDTO studentModuleGradeDictDTO = studentModuleGradeDictMapper.toDto(studentModuleGradeDict);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStudentModuleGradeDictMockMvc.perform(post("/api/student-module-grade-dicts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentModuleGradeDictDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StudentModuleGradeDict in the database
        List<StudentModuleGradeDict> studentModuleGradeDictList = studentModuleGradeDictRepository.findAll();
        assertThat(studentModuleGradeDictList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentModuleGradeDictRepository.findAll().size();
        // set the field null
        studentModuleGradeDict.setName(null);

        // Create the StudentModuleGradeDict, which fails.
        StudentModuleGradeDictDTO studentModuleGradeDictDTO = studentModuleGradeDictMapper.toDto(studentModuleGradeDict);

        restStudentModuleGradeDictMockMvc.perform(post("/api/student-module-grade-dicts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentModuleGradeDictDTO)))
            .andExpect(status().isBadRequest());

        List<StudentModuleGradeDict> studentModuleGradeDictList = studentModuleGradeDictRepository.findAll();
        assertThat(studentModuleGradeDictList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentModuleGradeDictRepository.findAll().size();
        // set the field null
        studentModuleGradeDict.setDescription(null);

        // Create the StudentModuleGradeDict, which fails.
        StudentModuleGradeDictDTO studentModuleGradeDictDTO = studentModuleGradeDictMapper.toDto(studentModuleGradeDict);

        restStudentModuleGradeDictMockMvc.perform(post("/api/student-module-grade-dicts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentModuleGradeDictDTO)))
            .andExpect(status().isBadRequest());

        List<StudentModuleGradeDict> studentModuleGradeDictList = studentModuleGradeDictRepository.findAll();
        assertThat(studentModuleGradeDictList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStudentModuleGradeDicts() throws Exception {
        // Initialize the database
        studentModuleGradeDictRepository.saveAndFlush(studentModuleGradeDict);

        // Get all the studentModuleGradeDictList
        restStudentModuleGradeDictMockMvc.perform(get("/api/student-module-grade-dicts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(studentModuleGradeDict.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].lowMarks").value(hasItem(DEFAULT_LOW_MARKS)))
            .andExpect(jsonPath("$.[*].qpv").value(hasItem(DEFAULT_QPV.doubleValue())))
            .andExpect(jsonPath("$.[*].isAffectQca").value(hasItem(DEFAULT_IS_AFFECT_QCA.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getStudentModuleGradeDict() throws Exception {
        // Initialize the database
        studentModuleGradeDictRepository.saveAndFlush(studentModuleGradeDict);

        // Get the studentModuleGradeDict
        restStudentModuleGradeDictMockMvc.perform(get("/api/student-module-grade-dicts/{id}", studentModuleGradeDict.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(studentModuleGradeDict.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.lowMarks").value(DEFAULT_LOW_MARKS))
            .andExpect(jsonPath("$.qpv").value(DEFAULT_QPV.doubleValue()))
            .andExpect(jsonPath("$.isAffectQca").value(DEFAULT_IS_AFFECT_QCA.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingStudentModuleGradeDict() throws Exception {
        // Get the studentModuleGradeDict
        restStudentModuleGradeDictMockMvc.perform(get("/api/student-module-grade-dicts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStudentModuleGradeDict() throws Exception {
        // Initialize the database
        studentModuleGradeDictRepository.saveAndFlush(studentModuleGradeDict);

        int databaseSizeBeforeUpdate = studentModuleGradeDictRepository.findAll().size();

        // Update the studentModuleGradeDict
        StudentModuleGradeDict updatedStudentModuleGradeDict = studentModuleGradeDictRepository.findById(studentModuleGradeDict.getId()).get();
        // Disconnect from session so that the updates on updatedStudentModuleGradeDict are not directly saved in db
        em.detach(updatedStudentModuleGradeDict);
        updatedStudentModuleGradeDict
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .lowMarks(UPDATED_LOW_MARKS)
            .qpv(UPDATED_QPV)
            .isAffectQca(UPDATED_IS_AFFECT_QCA);
        StudentModuleGradeDictDTO studentModuleGradeDictDTO = studentModuleGradeDictMapper.toDto(updatedStudentModuleGradeDict);

        restStudentModuleGradeDictMockMvc.perform(put("/api/student-module-grade-dicts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentModuleGradeDictDTO)))
            .andExpect(status().isOk());

        // Validate the StudentModuleGradeDict in the database
        List<StudentModuleGradeDict> studentModuleGradeDictList = studentModuleGradeDictRepository.findAll();
        assertThat(studentModuleGradeDictList).hasSize(databaseSizeBeforeUpdate);
        StudentModuleGradeDict testStudentModuleGradeDict = studentModuleGradeDictList.get(studentModuleGradeDictList.size() - 1);
        assertThat(testStudentModuleGradeDict.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStudentModuleGradeDict.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testStudentModuleGradeDict.getLowMarks()).isEqualTo(UPDATED_LOW_MARKS);
        assertThat(testStudentModuleGradeDict.getQpv()).isEqualTo(UPDATED_QPV);
        assertThat(testStudentModuleGradeDict.isIsAffectQca()).isEqualTo(UPDATED_IS_AFFECT_QCA);
    }

    @Test
    @Transactional
    public void updateNonExistingStudentModuleGradeDict() throws Exception {
        int databaseSizeBeforeUpdate = studentModuleGradeDictRepository.findAll().size();

        // Create the StudentModuleGradeDict
        StudentModuleGradeDictDTO studentModuleGradeDictDTO = studentModuleGradeDictMapper.toDto(studentModuleGradeDict);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStudentModuleGradeDictMockMvc.perform(put("/api/student-module-grade-dicts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentModuleGradeDictDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StudentModuleGradeDict in the database
        List<StudentModuleGradeDict> studentModuleGradeDictList = studentModuleGradeDictRepository.findAll();
        assertThat(studentModuleGradeDictList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStudentModuleGradeDict() throws Exception {
        // Initialize the database
        studentModuleGradeDictRepository.saveAndFlush(studentModuleGradeDict);

        int databaseSizeBeforeDelete = studentModuleGradeDictRepository.findAll().size();

        // Delete the studentModuleGradeDict
        restStudentModuleGradeDictMockMvc.perform(delete("/api/student-module-grade-dicts/{id}", studentModuleGradeDict.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<StudentModuleGradeDict> studentModuleGradeDictList = studentModuleGradeDictRepository.findAll();
        assertThat(studentModuleGradeDictList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StudentModuleGradeDict.class);
        StudentModuleGradeDict studentModuleGradeDict1 = new StudentModuleGradeDict();
        studentModuleGradeDict1.setId(1L);
        StudentModuleGradeDict studentModuleGradeDict2 = new StudentModuleGradeDict();
        studentModuleGradeDict2.setId(studentModuleGradeDict1.getId());
        assertThat(studentModuleGradeDict1).isEqualTo(studentModuleGradeDict2);
        studentModuleGradeDict2.setId(2L);
        assertThat(studentModuleGradeDict1).isNotEqualTo(studentModuleGradeDict2);
        studentModuleGradeDict1.setId(null);
        assertThat(studentModuleGradeDict1).isNotEqualTo(studentModuleGradeDict2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StudentModuleGradeDictDTO.class);
        StudentModuleGradeDictDTO studentModuleGradeDictDTO1 = new StudentModuleGradeDictDTO();
        studentModuleGradeDictDTO1.setId(1L);
        StudentModuleGradeDictDTO studentModuleGradeDictDTO2 = new StudentModuleGradeDictDTO();
        assertThat(studentModuleGradeDictDTO1).isNotEqualTo(studentModuleGradeDictDTO2);
        studentModuleGradeDictDTO2.setId(studentModuleGradeDictDTO1.getId());
        assertThat(studentModuleGradeDictDTO1).isEqualTo(studentModuleGradeDictDTO2);
        studentModuleGradeDictDTO2.setId(2L);
        assertThat(studentModuleGradeDictDTO1).isNotEqualTo(studentModuleGradeDictDTO2);
        studentModuleGradeDictDTO1.setId(null);
        assertThat(studentModuleGradeDictDTO1).isNotEqualTo(studentModuleGradeDictDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(studentModuleGradeDictMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(studentModuleGradeDictMapper.fromId(null)).isNull();
    }
}

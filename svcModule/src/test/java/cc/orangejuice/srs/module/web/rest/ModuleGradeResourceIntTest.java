package cc.orangejuice.srs.module.web.rest;

import cc.orangejuice.srs.module.SvcModuleApp;
import cc.orangejuice.srs.module.config.SecurityBeanOverrideConfiguration;
import cc.orangejuice.srs.module.domain.ModuleGrade;
import cc.orangejuice.srs.module.repository.ModuleGradeRepository;
import cc.orangejuice.srs.module.service.ModuleGradeService;
import cc.orangejuice.srs.module.service.dto.ModuleGradeDTO;
import cc.orangejuice.srs.module.service.mapper.ModuleGradeMapper;
import cc.orangejuice.srs.module.web.rest.errors.ExceptionTranslator;
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

import static cc.orangejuice.srs.module.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ModuleGradeResource REST controller.
 *
 * @see ModuleGradeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, SvcModuleApp.class})
public class ModuleGradeResourceIntTest {

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
    private ModuleGradeRepository moduleGradeRepository;

    @Autowired
    private ModuleGradeMapper moduleGradeMapper;

    @Autowired
    private ModuleGradeService moduleGradeService;

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

    private MockMvc restModuleGradeMockMvc;

    private ModuleGrade moduleGrade;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ModuleGradeResource moduleGradeResource = new ModuleGradeResource(moduleGradeService);
        this.restModuleGradeMockMvc = MockMvcBuilders.standaloneSetup(moduleGradeResource)
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
    public static ModuleGrade createEntity(EntityManager em) {
        ModuleGrade moduleGrade = new ModuleGrade()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .lowMarks(DEFAULT_LOW_MARKS)
            .qpv(DEFAULT_QPV)
            .isAffectQca(DEFAULT_IS_AFFECT_QCA);
        return moduleGrade;
    }

    @Before
    public void initTest() {
        moduleGrade = createEntity(em);
    }

    @Test
    @Transactional
    public void createModuleGrade() throws Exception {
        int databaseSizeBeforeCreate = moduleGradeRepository.findAll().size();

        // Create the ModuleGrade
        ModuleGradeDTO moduleGradeDTO = moduleGradeMapper.toDto(moduleGrade);
        restModuleGradeMockMvc.perform(post("/api/module-grades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moduleGradeDTO)))
            .andExpect(status().isCreated());

        // Validate the ModuleGrade in the database
        List<ModuleGrade> moduleGradeList = moduleGradeRepository.findAll();
        assertThat(moduleGradeList).hasSize(databaseSizeBeforeCreate + 1);
        ModuleGrade testModuleGrade = moduleGradeList.get(moduleGradeList.size() - 1);
        assertThat(testModuleGrade.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testModuleGrade.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testModuleGrade.getLowMarks()).isEqualTo(DEFAULT_LOW_MARKS);
        assertThat(testModuleGrade.getQpv()).isEqualTo(DEFAULT_QPV);
        assertThat(testModuleGrade.isIsAffectQca()).isEqualTo(DEFAULT_IS_AFFECT_QCA);
    }

    @Test
    @Transactional
    public void createModuleGradeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = moduleGradeRepository.findAll().size();

        // Create the ModuleGrade with an existing ID
        moduleGrade.setId(1L);
        ModuleGradeDTO moduleGradeDTO = moduleGradeMapper.toDto(moduleGrade);

        // An entity with an existing ID cannot be created, so this API call must fail
        restModuleGradeMockMvc.perform(post("/api/module-grades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moduleGradeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ModuleGrade in the database
        List<ModuleGrade> moduleGradeList = moduleGradeRepository.findAll();
        assertThat(moduleGradeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = moduleGradeRepository.findAll().size();
        // set the field null
        moduleGrade.setName(null);

        // Create the ModuleGrade, which fails.
        ModuleGradeDTO moduleGradeDTO = moduleGradeMapper.toDto(moduleGrade);

        restModuleGradeMockMvc.perform(post("/api/module-grades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moduleGradeDTO)))
            .andExpect(status().isBadRequest());

        List<ModuleGrade> moduleGradeList = moduleGradeRepository.findAll();
        assertThat(moduleGradeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = moduleGradeRepository.findAll().size();
        // set the field null
        moduleGrade.setDescription(null);

        // Create the ModuleGrade, which fails.
        ModuleGradeDTO moduleGradeDTO = moduleGradeMapper.toDto(moduleGrade);

        restModuleGradeMockMvc.perform(post("/api/module-grades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moduleGradeDTO)))
            .andExpect(status().isBadRequest());

        List<ModuleGrade> moduleGradeList = moduleGradeRepository.findAll();
        assertThat(moduleGradeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllModuleGrades() throws Exception {
        // Initialize the database
        moduleGradeRepository.saveAndFlush(moduleGrade);

        // Get all the moduleGradeList
        restModuleGradeMockMvc.perform(get("/api/module-grades?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(moduleGrade.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].lowMarks").value(hasItem(DEFAULT_LOW_MARKS)))
            .andExpect(jsonPath("$.[*].qpv").value(hasItem(DEFAULT_QPV.doubleValue())))
            .andExpect(jsonPath("$.[*].isAffectQca").value(hasItem(DEFAULT_IS_AFFECT_QCA.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getModuleGrade() throws Exception {
        // Initialize the database
        moduleGradeRepository.saveAndFlush(moduleGrade);

        // Get the moduleGrade
        restModuleGradeMockMvc.perform(get("/api/module-grades/{id}", moduleGrade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(moduleGrade.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.lowMarks").value(DEFAULT_LOW_MARKS))
            .andExpect(jsonPath("$.qpv").value(DEFAULT_QPV.doubleValue()))
            .andExpect(jsonPath("$.isAffectQca").value(DEFAULT_IS_AFFECT_QCA.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingModuleGrade() throws Exception {
        // Get the moduleGrade
        restModuleGradeMockMvc.perform(get("/api/module-grades/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateModuleGrade() throws Exception {
        // Initialize the database
        moduleGradeRepository.saveAndFlush(moduleGrade);

        int databaseSizeBeforeUpdate = moduleGradeRepository.findAll().size();

        // Update the moduleGrade
        ModuleGrade updatedModuleGrade = moduleGradeRepository.findById(moduleGrade.getId()).get();
        // Disconnect from session so that the updates on updatedModuleGrade are not directly saved in db
        em.detach(updatedModuleGrade);
        updatedModuleGrade
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .lowMarks(UPDATED_LOW_MARKS)
            .qpv(UPDATED_QPV)
            .isAffectQca(UPDATED_IS_AFFECT_QCA);
        ModuleGradeDTO moduleGradeDTO = moduleGradeMapper.toDto(updatedModuleGrade);

        restModuleGradeMockMvc.perform(put("/api/module-grades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moduleGradeDTO)))
            .andExpect(status().isOk());

        // Validate the ModuleGrade in the database
        List<ModuleGrade> moduleGradeList = moduleGradeRepository.findAll();
        assertThat(moduleGradeList).hasSize(databaseSizeBeforeUpdate);
        ModuleGrade testModuleGrade = moduleGradeList.get(moduleGradeList.size() - 1);
        assertThat(testModuleGrade.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testModuleGrade.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testModuleGrade.getLowMarks()).isEqualTo(UPDATED_LOW_MARKS);
        assertThat(testModuleGrade.getQpv()).isEqualTo(UPDATED_QPV);
        assertThat(testModuleGrade.isIsAffectQca()).isEqualTo(UPDATED_IS_AFFECT_QCA);
    }

    @Test
    @Transactional
    public void updateNonExistingModuleGrade() throws Exception {
        int databaseSizeBeforeUpdate = moduleGradeRepository.findAll().size();

        // Create the ModuleGrade
        ModuleGradeDTO moduleGradeDTO = moduleGradeMapper.toDto(moduleGrade);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restModuleGradeMockMvc.perform(put("/api/module-grades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moduleGradeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ModuleGrade in the database
        List<ModuleGrade> moduleGradeList = moduleGradeRepository.findAll();
        assertThat(moduleGradeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteModuleGrade() throws Exception {
        // Initialize the database
        moduleGradeRepository.saveAndFlush(moduleGrade);

        int databaseSizeBeforeDelete = moduleGradeRepository.findAll().size();

        // Delete the moduleGrade
        restModuleGradeMockMvc.perform(delete("/api/module-grades/{id}", moduleGrade.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ModuleGrade> moduleGradeList = moduleGradeRepository.findAll();
        assertThat(moduleGradeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ModuleGrade.class);
        ModuleGrade moduleGrade1 = new ModuleGrade();
        moduleGrade1.setId(1L);
        ModuleGrade moduleGrade2 = new ModuleGrade();
        moduleGrade2.setId(moduleGrade1.getId());
        assertThat(moduleGrade1).isEqualTo(moduleGrade2);
        moduleGrade2.setId(2L);
        assertThat(moduleGrade1).isNotEqualTo(moduleGrade2);
        moduleGrade1.setId(null);
        assertThat(moduleGrade1).isNotEqualTo(moduleGrade2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ModuleGradeDTO.class);
        ModuleGradeDTO moduleGradeDTO1 = new ModuleGradeDTO();
        moduleGradeDTO1.setId(1L);
        ModuleGradeDTO moduleGradeDTO2 = new ModuleGradeDTO();
        assertThat(moduleGradeDTO1).isNotEqualTo(moduleGradeDTO2);
        moduleGradeDTO2.setId(moduleGradeDTO1.getId());
        assertThat(moduleGradeDTO1).isEqualTo(moduleGradeDTO2);
        moduleGradeDTO2.setId(2L);
        assertThat(moduleGradeDTO1).isNotEqualTo(moduleGradeDTO2);
        moduleGradeDTO1.setId(null);
        assertThat(moduleGradeDTO1).isNotEqualTo(moduleGradeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(moduleGradeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(moduleGradeMapper.fromId(null)).isNull();
    }
}

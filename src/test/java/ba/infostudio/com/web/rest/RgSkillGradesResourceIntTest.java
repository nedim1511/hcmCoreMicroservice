package ba.infostudio.com.web.rest;

import ba.infostudio.com.HcmCoreMicroserviceApp;

import ba.infostudio.com.domain.RgSkillGrades;
import ba.infostudio.com.repository.RgSkillGradesRepository;
import ba.infostudio.com.service.dto.RgSkillGradesDTO;
import ba.infostudio.com.service.mapper.RgSkillGradesMapper;
import ba.infostudio.com.web.rest.errors.ExceptionTranslator;

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

import javax.persistence.EntityManager;
import java.util.List;

import static ba.infostudio.com.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the RgSkillGradesResource REST controller.
 *
 * @see RgSkillGradesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HcmCoreMicroserviceApp.class)
public class RgSkillGradesResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_GRADE = 1;
    private static final Integer UPDATED_GRADE = 2;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERICAL = "AAAAAAAAAA";
    private static final String UPDATED_NUMERICAL = "BBBBBBBBBB";

    @Autowired
    private RgSkillGradesRepository rgSkillGradesRepository;

    @Autowired
    private RgSkillGradesMapper rgSkillGradesMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRgSkillGradesMockMvc;

    private RgSkillGrades rgSkillGrades;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RgSkillGradesResource rgSkillGradesResource = new RgSkillGradesResource(rgSkillGradesRepository, rgSkillGradesMapper);
        this.restRgSkillGradesMockMvc = MockMvcBuilders.standaloneSetup(rgSkillGradesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RgSkillGrades createEntity(EntityManager em) {
        RgSkillGrades rgSkillGrades = new RgSkillGrades()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .grade(DEFAULT_GRADE)
            .description(DEFAULT_DESCRIPTION)
            .numerical(DEFAULT_NUMERICAL);
        return rgSkillGrades;
    }

    @Before
    public void initTest() {
        rgSkillGrades = createEntity(em);
    }

    @Test
    @Transactional
    public void createRgSkillGrades() throws Exception {
        int databaseSizeBeforeCreate = rgSkillGradesRepository.findAll().size();

        // Create the RgSkillGrades
        RgSkillGradesDTO rgSkillGradesDTO = rgSkillGradesMapper.toDto(rgSkillGrades);
        restRgSkillGradesMockMvc.perform(post("/api/rg-skill-grades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rgSkillGradesDTO)))
            .andExpect(status().isCreated());

        // Validate the RgSkillGrades in the database
        List<RgSkillGrades> rgSkillGradesList = rgSkillGradesRepository.findAll();
        assertThat(rgSkillGradesList).hasSize(databaseSizeBeforeCreate + 1);
        RgSkillGrades testRgSkillGrades = rgSkillGradesList.get(rgSkillGradesList.size() - 1);
        assertThat(testRgSkillGrades.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testRgSkillGrades.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRgSkillGrades.getGrade()).isEqualTo(DEFAULT_GRADE);
        assertThat(testRgSkillGrades.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRgSkillGrades.getNumerical()).isEqualTo(DEFAULT_NUMERICAL);
    }

    @Test
    @Transactional
    public void createRgSkillGradesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rgSkillGradesRepository.findAll().size();

        // Create the RgSkillGrades with an existing ID
        rgSkillGrades.setId(1L);
        RgSkillGradesDTO rgSkillGradesDTO = rgSkillGradesMapper.toDto(rgSkillGrades);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRgSkillGradesMockMvc.perform(post("/api/rg-skill-grades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rgSkillGradesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RgSkillGrades in the database
        List<RgSkillGrades> rgSkillGradesList = rgSkillGradesRepository.findAll();
        assertThat(rgSkillGradesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = rgSkillGradesRepository.findAll().size();
        // set the field null
        rgSkillGrades.setCode(null);

        // Create the RgSkillGrades, which fails.
        RgSkillGradesDTO rgSkillGradesDTO = rgSkillGradesMapper.toDto(rgSkillGrades);

        restRgSkillGradesMockMvc.perform(post("/api/rg-skill-grades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rgSkillGradesDTO)))
            .andExpect(status().isBadRequest());

        List<RgSkillGrades> rgSkillGradesList = rgSkillGradesRepository.findAll();
        assertThat(rgSkillGradesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = rgSkillGradesRepository.findAll().size();
        // set the field null
        rgSkillGrades.setName(null);

        // Create the RgSkillGrades, which fails.
        RgSkillGradesDTO rgSkillGradesDTO = rgSkillGradesMapper.toDto(rgSkillGrades);

        restRgSkillGradesMockMvc.perform(post("/api/rg-skill-grades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rgSkillGradesDTO)))
            .andExpect(status().isBadRequest());

        List<RgSkillGrades> rgSkillGradesList = rgSkillGradesRepository.findAll();
        assertThat(rgSkillGradesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGradeIsRequired() throws Exception {
        int databaseSizeBeforeTest = rgSkillGradesRepository.findAll().size();
        // set the field null
        rgSkillGrades.setGrade(null);

        // Create the RgSkillGrades, which fails.
        RgSkillGradesDTO rgSkillGradesDTO = rgSkillGradesMapper.toDto(rgSkillGrades);

        restRgSkillGradesMockMvc.perform(post("/api/rg-skill-grades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rgSkillGradesDTO)))
            .andExpect(status().isBadRequest());

        List<RgSkillGrades> rgSkillGradesList = rgSkillGradesRepository.findAll();
        assertThat(rgSkillGradesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRgSkillGrades() throws Exception {
        // Initialize the database
        rgSkillGradesRepository.saveAndFlush(rgSkillGrades);

        // Get all the rgSkillGradesList
        restRgSkillGradesMockMvc.perform(get("/api/rg-skill-grades?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rgSkillGrades.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].grade").value(hasItem(DEFAULT_GRADE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].numerical").value(hasItem(DEFAULT_NUMERICAL.toString())));
    }

    @Test
    @Transactional
    public void getRgSkillGrades() throws Exception {
        // Initialize the database
        rgSkillGradesRepository.saveAndFlush(rgSkillGrades);

        // Get the rgSkillGrades
        restRgSkillGradesMockMvc.perform(get("/api/rg-skill-grades/{id}", rgSkillGrades.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rgSkillGrades.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.grade").value(DEFAULT_GRADE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.numerical").value(DEFAULT_NUMERICAL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRgSkillGrades() throws Exception {
        // Get the rgSkillGrades
        restRgSkillGradesMockMvc.perform(get("/api/rg-skill-grades/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRgSkillGrades() throws Exception {
        // Initialize the database
        rgSkillGradesRepository.saveAndFlush(rgSkillGrades);
        int databaseSizeBeforeUpdate = rgSkillGradesRepository.findAll().size();

        // Update the rgSkillGrades
        RgSkillGrades updatedRgSkillGrades = rgSkillGradesRepository.findOne(rgSkillGrades.getId());
        // Disconnect from session so that the updates on updatedRgSkillGrades are not directly saved in db
        em.detach(updatedRgSkillGrades);
        updatedRgSkillGrades
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .grade(UPDATED_GRADE)
            .description(UPDATED_DESCRIPTION)
            .numerical(UPDATED_NUMERICAL);
        RgSkillGradesDTO rgSkillGradesDTO = rgSkillGradesMapper.toDto(updatedRgSkillGrades);

        restRgSkillGradesMockMvc.perform(put("/api/rg-skill-grades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rgSkillGradesDTO)))
            .andExpect(status().isOk());

        // Validate the RgSkillGrades in the database
        List<RgSkillGrades> rgSkillGradesList = rgSkillGradesRepository.findAll();
        assertThat(rgSkillGradesList).hasSize(databaseSizeBeforeUpdate);
        RgSkillGrades testRgSkillGrades = rgSkillGradesList.get(rgSkillGradesList.size() - 1);
        assertThat(testRgSkillGrades.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testRgSkillGrades.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRgSkillGrades.getGrade()).isEqualTo(UPDATED_GRADE);
        assertThat(testRgSkillGrades.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRgSkillGrades.getNumerical()).isEqualTo(UPDATED_NUMERICAL);
    }

    @Test
    @Transactional
    public void updateNonExistingRgSkillGrades() throws Exception {
        int databaseSizeBeforeUpdate = rgSkillGradesRepository.findAll().size();

        // Create the RgSkillGrades
        RgSkillGradesDTO rgSkillGradesDTO = rgSkillGradesMapper.toDto(rgSkillGrades);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRgSkillGradesMockMvc.perform(put("/api/rg-skill-grades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rgSkillGradesDTO)))
            .andExpect(status().isCreated());

        // Validate the RgSkillGrades in the database
        List<RgSkillGrades> rgSkillGradesList = rgSkillGradesRepository.findAll();
        assertThat(rgSkillGradesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRgSkillGrades() throws Exception {
        // Initialize the database
        rgSkillGradesRepository.saveAndFlush(rgSkillGrades);
        int databaseSizeBeforeDelete = rgSkillGradesRepository.findAll().size();

        // Get the rgSkillGrades
        restRgSkillGradesMockMvc.perform(delete("/api/rg-skill-grades/{id}", rgSkillGrades.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RgSkillGrades> rgSkillGradesList = rgSkillGradesRepository.findAll();
        assertThat(rgSkillGradesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RgSkillGrades.class);
        RgSkillGrades rgSkillGrades1 = new RgSkillGrades();
        rgSkillGrades1.setId(1L);
        RgSkillGrades rgSkillGrades2 = new RgSkillGrades();
        rgSkillGrades2.setId(rgSkillGrades1.getId());
        assertThat(rgSkillGrades1).isEqualTo(rgSkillGrades2);
        rgSkillGrades2.setId(2L);
        assertThat(rgSkillGrades1).isNotEqualTo(rgSkillGrades2);
        rgSkillGrades1.setId(null);
        assertThat(rgSkillGrades1).isNotEqualTo(rgSkillGrades2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RgSkillGradesDTO.class);
        RgSkillGradesDTO rgSkillGradesDTO1 = new RgSkillGradesDTO();
        rgSkillGradesDTO1.setId(1L);
        RgSkillGradesDTO rgSkillGradesDTO2 = new RgSkillGradesDTO();
        assertThat(rgSkillGradesDTO1).isNotEqualTo(rgSkillGradesDTO2);
        rgSkillGradesDTO2.setId(rgSkillGradesDTO1.getId());
        assertThat(rgSkillGradesDTO1).isEqualTo(rgSkillGradesDTO2);
        rgSkillGradesDTO2.setId(2L);
        assertThat(rgSkillGradesDTO1).isNotEqualTo(rgSkillGradesDTO2);
        rgSkillGradesDTO1.setId(null);
        assertThat(rgSkillGradesDTO1).isNotEqualTo(rgSkillGradesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(rgSkillGradesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(rgSkillGradesMapper.fromId(null)).isNull();
    }
}

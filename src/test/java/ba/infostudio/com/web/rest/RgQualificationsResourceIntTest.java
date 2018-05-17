package ba.infostudio.com.web.rest;

import ba.infostudio.com.HcmCoreMicroserviceApp;

import ba.infostudio.com.domain.RgQualifications;
import ba.infostudio.com.repository.RgQualificationsRepository;
import ba.infostudio.com.service.dto.RgQualificationsDTO;
import ba.infostudio.com.service.mapper.RgQualificationsMapper;
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
 * Test class for the RgQualificationsResource REST controller.
 *
 * @see RgQualificationsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HcmCoreMicroserviceApp.class)
public class RgQualificationsResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private RgQualificationsRepository rgQualificationsRepository;

    @Autowired
    private RgQualificationsMapper rgQualificationsMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRgQualificationsMockMvc;

    private RgQualifications rgQualifications;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RgQualificationsResource rgQualificationsResource = new RgQualificationsResource(rgQualificationsRepository, rgQualificationsMapper);
        this.restRgQualificationsMockMvc = MockMvcBuilders.standaloneSetup(rgQualificationsResource)
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
    public static RgQualifications createEntity(EntityManager em) {
        RgQualifications rgQualifications = new RgQualifications()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return rgQualifications;
    }

    @Before
    public void initTest() {
        rgQualifications = createEntity(em);
    }

    @Test
    @Transactional
    public void createRgQualifications() throws Exception {
        int databaseSizeBeforeCreate = rgQualificationsRepository.findAll().size();

        // Create the RgQualifications
        RgQualificationsDTO rgQualificationsDTO = rgQualificationsMapper.toDto(rgQualifications);
        restRgQualificationsMockMvc.perform(post("/api/rg-qualifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rgQualificationsDTO)))
            .andExpect(status().isCreated());

        // Validate the RgQualifications in the database
        List<RgQualifications> rgQualificationsList = rgQualificationsRepository.findAll();
        assertThat(rgQualificationsList).hasSize(databaseSizeBeforeCreate + 1);
        RgQualifications testRgQualifications = rgQualificationsList.get(rgQualificationsList.size() - 1);
        assertThat(testRgQualifications.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testRgQualifications.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRgQualifications.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createRgQualificationsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rgQualificationsRepository.findAll().size();

        // Create the RgQualifications with an existing ID
        rgQualifications.setId(1L);
        RgQualificationsDTO rgQualificationsDTO = rgQualificationsMapper.toDto(rgQualifications);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRgQualificationsMockMvc.perform(post("/api/rg-qualifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rgQualificationsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RgQualifications in the database
        List<RgQualifications> rgQualificationsList = rgQualificationsRepository.findAll();
        assertThat(rgQualificationsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = rgQualificationsRepository.findAll().size();
        // set the field null
        rgQualifications.setCode(null);

        // Create the RgQualifications, which fails.
        RgQualificationsDTO rgQualificationsDTO = rgQualificationsMapper.toDto(rgQualifications);

        restRgQualificationsMockMvc.perform(post("/api/rg-qualifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rgQualificationsDTO)))
            .andExpect(status().isBadRequest());

        List<RgQualifications> rgQualificationsList = rgQualificationsRepository.findAll();
        assertThat(rgQualificationsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = rgQualificationsRepository.findAll().size();
        // set the field null
        rgQualifications.setName(null);

        // Create the RgQualifications, which fails.
        RgQualificationsDTO rgQualificationsDTO = rgQualificationsMapper.toDto(rgQualifications);

        restRgQualificationsMockMvc.perform(post("/api/rg-qualifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rgQualificationsDTO)))
            .andExpect(status().isBadRequest());

        List<RgQualifications> rgQualificationsList = rgQualificationsRepository.findAll();
        assertThat(rgQualificationsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRgQualifications() throws Exception {
        // Initialize the database
        rgQualificationsRepository.saveAndFlush(rgQualifications);

        // Get all the rgQualificationsList
        restRgQualificationsMockMvc.perform(get("/api/rg-qualifications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rgQualifications.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getRgQualifications() throws Exception {
        // Initialize the database
        rgQualificationsRepository.saveAndFlush(rgQualifications);

        // Get the rgQualifications
        restRgQualificationsMockMvc.perform(get("/api/rg-qualifications/{id}", rgQualifications.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rgQualifications.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRgQualifications() throws Exception {
        // Get the rgQualifications
        restRgQualificationsMockMvc.perform(get("/api/rg-qualifications/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRgQualifications() throws Exception {
        // Initialize the database
        rgQualificationsRepository.saveAndFlush(rgQualifications);
        int databaseSizeBeforeUpdate = rgQualificationsRepository.findAll().size();

        // Update the rgQualifications
        RgQualifications updatedRgQualifications = rgQualificationsRepository.findOne(rgQualifications.getId());
        // Disconnect from session so that the updates on updatedRgQualifications are not directly saved in db
        em.detach(updatedRgQualifications);
        updatedRgQualifications
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        RgQualificationsDTO rgQualificationsDTO = rgQualificationsMapper.toDto(updatedRgQualifications);

        restRgQualificationsMockMvc.perform(put("/api/rg-qualifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rgQualificationsDTO)))
            .andExpect(status().isOk());

        // Validate the RgQualifications in the database
        List<RgQualifications> rgQualificationsList = rgQualificationsRepository.findAll();
        assertThat(rgQualificationsList).hasSize(databaseSizeBeforeUpdate);
        RgQualifications testRgQualifications = rgQualificationsList.get(rgQualificationsList.size() - 1);
        assertThat(testRgQualifications.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testRgQualifications.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRgQualifications.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingRgQualifications() throws Exception {
        int databaseSizeBeforeUpdate = rgQualificationsRepository.findAll().size();

        // Create the RgQualifications
        RgQualificationsDTO rgQualificationsDTO = rgQualificationsMapper.toDto(rgQualifications);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRgQualificationsMockMvc.perform(put("/api/rg-qualifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rgQualificationsDTO)))
            .andExpect(status().isCreated());

        // Validate the RgQualifications in the database
        List<RgQualifications> rgQualificationsList = rgQualificationsRepository.findAll();
        assertThat(rgQualificationsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRgQualifications() throws Exception {
        // Initialize the database
        rgQualificationsRepository.saveAndFlush(rgQualifications);
        int databaseSizeBeforeDelete = rgQualificationsRepository.findAll().size();

        // Get the rgQualifications
        restRgQualificationsMockMvc.perform(delete("/api/rg-qualifications/{id}", rgQualifications.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RgQualifications> rgQualificationsList = rgQualificationsRepository.findAll();
        assertThat(rgQualificationsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RgQualifications.class);
        RgQualifications rgQualifications1 = new RgQualifications();
        rgQualifications1.setId(1L);
        RgQualifications rgQualifications2 = new RgQualifications();
        rgQualifications2.setId(rgQualifications1.getId());
        assertThat(rgQualifications1).isEqualTo(rgQualifications2);
        rgQualifications2.setId(2L);
        assertThat(rgQualifications1).isNotEqualTo(rgQualifications2);
        rgQualifications1.setId(null);
        assertThat(rgQualifications1).isNotEqualTo(rgQualifications2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RgQualificationsDTO.class);
        RgQualificationsDTO rgQualificationsDTO1 = new RgQualificationsDTO();
        rgQualificationsDTO1.setId(1L);
        RgQualificationsDTO rgQualificationsDTO2 = new RgQualificationsDTO();
        assertThat(rgQualificationsDTO1).isNotEqualTo(rgQualificationsDTO2);
        rgQualificationsDTO2.setId(rgQualificationsDTO1.getId());
        assertThat(rgQualificationsDTO1).isEqualTo(rgQualificationsDTO2);
        rgQualificationsDTO2.setId(2L);
        assertThat(rgQualificationsDTO1).isNotEqualTo(rgQualificationsDTO2);
        rgQualificationsDTO1.setId(null);
        assertThat(rgQualificationsDTO1).isNotEqualTo(rgQualificationsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(rgQualificationsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(rgQualificationsMapper.fromId(null)).isNull();
    }
}

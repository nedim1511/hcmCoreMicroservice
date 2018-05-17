package ba.infostudio.com.web.rest;

import ba.infostudio.com.HcmCoreMicroserviceApp;

import ba.infostudio.com.domain.LeLegalEntities;
import ba.infostudio.com.repository.LeLegalEntitiesRepository;
import ba.infostudio.com.service.dto.LeLegalEntitiesDTO;
import ba.infostudio.com.service.mapper.LeLegalEntitiesMapper;
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
 * Test class for the LeLegalEntitiesResource REST controller.
 *
 * @see LeLegalEntitiesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HcmCoreMicroserviceApp.class)
public class LeLegalEntitiesResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ID_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ID_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_DUTY_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_DUTY_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_POSTAL_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_POSTAL_NUMBER = "BBBBBBBBBB";

    @Autowired
    private LeLegalEntitiesRepository leLegalEntitiesRepository;

    @Autowired
    private LeLegalEntitiesMapper leLegalEntitiesMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLeLegalEntitiesMockMvc;

    private LeLegalEntities leLegalEntities;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LeLegalEntitiesResource leLegalEntitiesResource = new LeLegalEntitiesResource(leLegalEntitiesRepository, leLegalEntitiesMapper);
        this.restLeLegalEntitiesMockMvc = MockMvcBuilders.standaloneSetup(leLegalEntitiesResource)
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
    public static LeLegalEntities createEntity(EntityManager em) {
        LeLegalEntities leLegalEntities = new LeLegalEntities()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .idNumber(DEFAULT_ID_NUMBER)
            .dutyNumber(DEFAULT_DUTY_NUMBER)
            .address(DEFAULT_ADDRESS)
            .postalNumber(DEFAULT_POSTAL_NUMBER);
        return leLegalEntities;
    }

    @Before
    public void initTest() {
        leLegalEntities = createEntity(em);
    }

    @Test
    @Transactional
    public void createLeLegalEntities() throws Exception {
        int databaseSizeBeforeCreate = leLegalEntitiesRepository.findAll().size();

        // Create the LeLegalEntities
        LeLegalEntitiesDTO leLegalEntitiesDTO = leLegalEntitiesMapper.toDto(leLegalEntities);
        restLeLegalEntitiesMockMvc.perform(post("/api/le-legal-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leLegalEntitiesDTO)))
            .andExpect(status().isCreated());

        // Validate the LeLegalEntities in the database
        List<LeLegalEntities> leLegalEntitiesList = leLegalEntitiesRepository.findAll();
        assertThat(leLegalEntitiesList).hasSize(databaseSizeBeforeCreate + 1);
        LeLegalEntities testLeLegalEntities = leLegalEntitiesList.get(leLegalEntitiesList.size() - 1);
        assertThat(testLeLegalEntities.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testLeLegalEntities.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLeLegalEntities.getIdNumber()).isEqualTo(DEFAULT_ID_NUMBER);
        assertThat(testLeLegalEntities.getDutyNumber()).isEqualTo(DEFAULT_DUTY_NUMBER);
        assertThat(testLeLegalEntities.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testLeLegalEntities.getPostalNumber()).isEqualTo(DEFAULT_POSTAL_NUMBER);
    }

    @Test
    @Transactional
    public void createLeLegalEntitiesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = leLegalEntitiesRepository.findAll().size();

        // Create the LeLegalEntities with an existing ID
        leLegalEntities.setId(1L);
        LeLegalEntitiesDTO leLegalEntitiesDTO = leLegalEntitiesMapper.toDto(leLegalEntities);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLeLegalEntitiesMockMvc.perform(post("/api/le-legal-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leLegalEntitiesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LeLegalEntities in the database
        List<LeLegalEntities> leLegalEntitiesList = leLegalEntitiesRepository.findAll();
        assertThat(leLegalEntitiesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = leLegalEntitiesRepository.findAll().size();
        // set the field null
        leLegalEntities.setCode(null);

        // Create the LeLegalEntities, which fails.
        LeLegalEntitiesDTO leLegalEntitiesDTO = leLegalEntitiesMapper.toDto(leLegalEntities);

        restLeLegalEntitiesMockMvc.perform(post("/api/le-legal-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leLegalEntitiesDTO)))
            .andExpect(status().isBadRequest());

        List<LeLegalEntities> leLegalEntitiesList = leLegalEntitiesRepository.findAll();
        assertThat(leLegalEntitiesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = leLegalEntitiesRepository.findAll().size();
        // set the field null
        leLegalEntities.setAddress(null);

        // Create the LeLegalEntities, which fails.
        LeLegalEntitiesDTO leLegalEntitiesDTO = leLegalEntitiesMapper.toDto(leLegalEntities);

        restLeLegalEntitiesMockMvc.perform(post("/api/le-legal-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leLegalEntitiesDTO)))
            .andExpect(status().isBadRequest());

        List<LeLegalEntities> leLegalEntitiesList = leLegalEntitiesRepository.findAll();
        assertThat(leLegalEntitiesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPostalNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = leLegalEntitiesRepository.findAll().size();
        // set the field null
        leLegalEntities.setPostalNumber(null);

        // Create the LeLegalEntities, which fails.
        LeLegalEntitiesDTO leLegalEntitiesDTO = leLegalEntitiesMapper.toDto(leLegalEntities);

        restLeLegalEntitiesMockMvc.perform(post("/api/le-legal-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leLegalEntitiesDTO)))
            .andExpect(status().isBadRequest());

        List<LeLegalEntities> leLegalEntitiesList = leLegalEntitiesRepository.findAll();
        assertThat(leLegalEntitiesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLeLegalEntities() throws Exception {
        // Initialize the database
        leLegalEntitiesRepository.saveAndFlush(leLegalEntities);

        // Get all the leLegalEntitiesList
        restLeLegalEntitiesMockMvc.perform(get("/api/le-legal-entities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leLegalEntities.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].idNumber").value(hasItem(DEFAULT_ID_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].dutyNumber").value(hasItem(DEFAULT_DUTY_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].postalNumber").value(hasItem(DEFAULT_POSTAL_NUMBER.toString())));
    }

    @Test
    @Transactional
    public void getLeLegalEntities() throws Exception {
        // Initialize the database
        leLegalEntitiesRepository.saveAndFlush(leLegalEntities);

        // Get the leLegalEntities
        restLeLegalEntitiesMockMvc.perform(get("/api/le-legal-entities/{id}", leLegalEntities.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(leLegalEntities.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.idNumber").value(DEFAULT_ID_NUMBER.toString()))
            .andExpect(jsonPath("$.dutyNumber").value(DEFAULT_DUTY_NUMBER.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.postalNumber").value(DEFAULT_POSTAL_NUMBER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLeLegalEntities() throws Exception {
        // Get the leLegalEntities
        restLeLegalEntitiesMockMvc.perform(get("/api/le-legal-entities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLeLegalEntities() throws Exception {
        // Initialize the database
        leLegalEntitiesRepository.saveAndFlush(leLegalEntities);
        int databaseSizeBeforeUpdate = leLegalEntitiesRepository.findAll().size();

        // Update the leLegalEntities
        LeLegalEntities updatedLeLegalEntities = leLegalEntitiesRepository.findOne(leLegalEntities.getId());
        // Disconnect from session so that the updates on updatedLeLegalEntities are not directly saved in db
        em.detach(updatedLeLegalEntities);
        updatedLeLegalEntities
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .idNumber(UPDATED_ID_NUMBER)
            .dutyNumber(UPDATED_DUTY_NUMBER)
            .address(UPDATED_ADDRESS)
            .postalNumber(UPDATED_POSTAL_NUMBER);
        LeLegalEntitiesDTO leLegalEntitiesDTO = leLegalEntitiesMapper.toDto(updatedLeLegalEntities);

        restLeLegalEntitiesMockMvc.perform(put("/api/le-legal-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leLegalEntitiesDTO)))
            .andExpect(status().isOk());

        // Validate the LeLegalEntities in the database
        List<LeLegalEntities> leLegalEntitiesList = leLegalEntitiesRepository.findAll();
        assertThat(leLegalEntitiesList).hasSize(databaseSizeBeforeUpdate);
        LeLegalEntities testLeLegalEntities = leLegalEntitiesList.get(leLegalEntitiesList.size() - 1);
        assertThat(testLeLegalEntities.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testLeLegalEntities.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLeLegalEntities.getIdNumber()).isEqualTo(UPDATED_ID_NUMBER);
        assertThat(testLeLegalEntities.getDutyNumber()).isEqualTo(UPDATED_DUTY_NUMBER);
        assertThat(testLeLegalEntities.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testLeLegalEntities.getPostalNumber()).isEqualTo(UPDATED_POSTAL_NUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingLeLegalEntities() throws Exception {
        int databaseSizeBeforeUpdate = leLegalEntitiesRepository.findAll().size();

        // Create the LeLegalEntities
        LeLegalEntitiesDTO leLegalEntitiesDTO = leLegalEntitiesMapper.toDto(leLegalEntities);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLeLegalEntitiesMockMvc.perform(put("/api/le-legal-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leLegalEntitiesDTO)))
            .andExpect(status().isCreated());

        // Validate the LeLegalEntities in the database
        List<LeLegalEntities> leLegalEntitiesList = leLegalEntitiesRepository.findAll();
        assertThat(leLegalEntitiesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLeLegalEntities() throws Exception {
        // Initialize the database
        leLegalEntitiesRepository.saveAndFlush(leLegalEntities);
        int databaseSizeBeforeDelete = leLegalEntitiesRepository.findAll().size();

        // Get the leLegalEntities
        restLeLegalEntitiesMockMvc.perform(delete("/api/le-legal-entities/{id}", leLegalEntities.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LeLegalEntities> leLegalEntitiesList = leLegalEntitiesRepository.findAll();
        assertThat(leLegalEntitiesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LeLegalEntities.class);
        LeLegalEntities leLegalEntities1 = new LeLegalEntities();
        leLegalEntities1.setId(1L);
        LeLegalEntities leLegalEntities2 = new LeLegalEntities();
        leLegalEntities2.setId(leLegalEntities1.getId());
        assertThat(leLegalEntities1).isEqualTo(leLegalEntities2);
        leLegalEntities2.setId(2L);
        assertThat(leLegalEntities1).isNotEqualTo(leLegalEntities2);
        leLegalEntities1.setId(null);
        assertThat(leLegalEntities1).isNotEqualTo(leLegalEntities2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LeLegalEntitiesDTO.class);
        LeLegalEntitiesDTO leLegalEntitiesDTO1 = new LeLegalEntitiesDTO();
        leLegalEntitiesDTO1.setId(1L);
        LeLegalEntitiesDTO leLegalEntitiesDTO2 = new LeLegalEntitiesDTO();
        assertThat(leLegalEntitiesDTO1).isNotEqualTo(leLegalEntitiesDTO2);
        leLegalEntitiesDTO2.setId(leLegalEntitiesDTO1.getId());
        assertThat(leLegalEntitiesDTO1).isEqualTo(leLegalEntitiesDTO2);
        leLegalEntitiesDTO2.setId(2L);
        assertThat(leLegalEntitiesDTO1).isNotEqualTo(leLegalEntitiesDTO2);
        leLegalEntitiesDTO1.setId(null);
        assertThat(leLegalEntitiesDTO1).isNotEqualTo(leLegalEntitiesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(leLegalEntitiesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(leLegalEntitiesMapper.fromId(null)).isNull();
    }
}

package ba.infostudio.com.web.rest;

import ba.infostudio.com.HcmCoreMicroserviceApp;

import ba.infostudio.com.domain.LeLegalEntityTypes;
import ba.infostudio.com.repository.LeLegalEntityTypesRepository;
import ba.infostudio.com.service.dto.LeLegalEntityTypesDTO;
import ba.infostudio.com.service.mapper.LeLegalEntityTypesMapper;
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
 * Test class for the LeLegalEntityTypesResource REST controller.
 *
 * @see LeLegalEntityTypesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HcmCoreMicroserviceApp.class)
public class LeLegalEntityTypesResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private LeLegalEntityTypesRepository leLegalEntityTypesRepository;

    @Autowired
    private LeLegalEntityTypesMapper leLegalEntityTypesMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLeLegalEntityTypesMockMvc;

    private LeLegalEntityTypes leLegalEntityTypes;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LeLegalEntityTypesResource leLegalEntityTypesResource = new LeLegalEntityTypesResource(leLegalEntityTypesRepository, leLegalEntityTypesMapper);
        this.restLeLegalEntityTypesMockMvc = MockMvcBuilders.standaloneSetup(leLegalEntityTypesResource)
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
    public static LeLegalEntityTypes createEntity(EntityManager em) {
        LeLegalEntityTypes leLegalEntityTypes = new LeLegalEntityTypes()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return leLegalEntityTypes;
    }

    @Before
    public void initTest() {
        leLegalEntityTypes = createEntity(em);
    }

    @Test
    @Transactional
    public void createLeLegalEntityTypes() throws Exception {
        int databaseSizeBeforeCreate = leLegalEntityTypesRepository.findAll().size();

        // Create the LeLegalEntityTypes
        LeLegalEntityTypesDTO leLegalEntityTypesDTO = leLegalEntityTypesMapper.toDto(leLegalEntityTypes);
        restLeLegalEntityTypesMockMvc.perform(post("/api/le-legal-entity-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leLegalEntityTypesDTO)))
            .andExpect(status().isCreated());

        // Validate the LeLegalEntityTypes in the database
        List<LeLegalEntityTypes> leLegalEntityTypesList = leLegalEntityTypesRepository.findAll();
        assertThat(leLegalEntityTypesList).hasSize(databaseSizeBeforeCreate + 1);
        LeLegalEntityTypes testLeLegalEntityTypes = leLegalEntityTypesList.get(leLegalEntityTypesList.size() - 1);
        assertThat(testLeLegalEntityTypes.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testLeLegalEntityTypes.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLeLegalEntityTypes.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createLeLegalEntityTypesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = leLegalEntityTypesRepository.findAll().size();

        // Create the LeLegalEntityTypes with an existing ID
        leLegalEntityTypes.setId(1L);
        LeLegalEntityTypesDTO leLegalEntityTypesDTO = leLegalEntityTypesMapper.toDto(leLegalEntityTypes);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLeLegalEntityTypesMockMvc.perform(post("/api/le-legal-entity-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leLegalEntityTypesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LeLegalEntityTypes in the database
        List<LeLegalEntityTypes> leLegalEntityTypesList = leLegalEntityTypesRepository.findAll();
        assertThat(leLegalEntityTypesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = leLegalEntityTypesRepository.findAll().size();
        // set the field null
        leLegalEntityTypes.setCode(null);

        // Create the LeLegalEntityTypes, which fails.
        LeLegalEntityTypesDTO leLegalEntityTypesDTO = leLegalEntityTypesMapper.toDto(leLegalEntityTypes);

        restLeLegalEntityTypesMockMvc.perform(post("/api/le-legal-entity-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leLegalEntityTypesDTO)))
            .andExpect(status().isBadRequest());

        List<LeLegalEntityTypes> leLegalEntityTypesList = leLegalEntityTypesRepository.findAll();
        assertThat(leLegalEntityTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = leLegalEntityTypesRepository.findAll().size();
        // set the field null
        leLegalEntityTypes.setName(null);

        // Create the LeLegalEntityTypes, which fails.
        LeLegalEntityTypesDTO leLegalEntityTypesDTO = leLegalEntityTypesMapper.toDto(leLegalEntityTypes);

        restLeLegalEntityTypesMockMvc.perform(post("/api/le-legal-entity-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leLegalEntityTypesDTO)))
            .andExpect(status().isBadRequest());

        List<LeLegalEntityTypes> leLegalEntityTypesList = leLegalEntityTypesRepository.findAll();
        assertThat(leLegalEntityTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLeLegalEntityTypes() throws Exception {
        // Initialize the database
        leLegalEntityTypesRepository.saveAndFlush(leLegalEntityTypes);

        // Get all the leLegalEntityTypesList
        restLeLegalEntityTypesMockMvc.perform(get("/api/le-legal-entity-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leLegalEntityTypes.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getLeLegalEntityTypes() throws Exception {
        // Initialize the database
        leLegalEntityTypesRepository.saveAndFlush(leLegalEntityTypes);

        // Get the leLegalEntityTypes
        restLeLegalEntityTypesMockMvc.perform(get("/api/le-legal-entity-types/{id}", leLegalEntityTypes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(leLegalEntityTypes.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLeLegalEntityTypes() throws Exception {
        // Get the leLegalEntityTypes
        restLeLegalEntityTypesMockMvc.perform(get("/api/le-legal-entity-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLeLegalEntityTypes() throws Exception {
        // Initialize the database
        leLegalEntityTypesRepository.saveAndFlush(leLegalEntityTypes);
        int databaseSizeBeforeUpdate = leLegalEntityTypesRepository.findAll().size();

        // Update the leLegalEntityTypes
        LeLegalEntityTypes updatedLeLegalEntityTypes = leLegalEntityTypesRepository.findOne(leLegalEntityTypes.getId());
        // Disconnect from session so that the updates on updatedLeLegalEntityTypes are not directly saved in db
        em.detach(updatedLeLegalEntityTypes);
        updatedLeLegalEntityTypes
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        LeLegalEntityTypesDTO leLegalEntityTypesDTO = leLegalEntityTypesMapper.toDto(updatedLeLegalEntityTypes);

        restLeLegalEntityTypesMockMvc.perform(put("/api/le-legal-entity-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leLegalEntityTypesDTO)))
            .andExpect(status().isOk());

        // Validate the LeLegalEntityTypes in the database
        List<LeLegalEntityTypes> leLegalEntityTypesList = leLegalEntityTypesRepository.findAll();
        assertThat(leLegalEntityTypesList).hasSize(databaseSizeBeforeUpdate);
        LeLegalEntityTypes testLeLegalEntityTypes = leLegalEntityTypesList.get(leLegalEntityTypesList.size() - 1);
        assertThat(testLeLegalEntityTypes.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testLeLegalEntityTypes.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLeLegalEntityTypes.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingLeLegalEntityTypes() throws Exception {
        int databaseSizeBeforeUpdate = leLegalEntityTypesRepository.findAll().size();

        // Create the LeLegalEntityTypes
        LeLegalEntityTypesDTO leLegalEntityTypesDTO = leLegalEntityTypesMapper.toDto(leLegalEntityTypes);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLeLegalEntityTypesMockMvc.perform(put("/api/le-legal-entity-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leLegalEntityTypesDTO)))
            .andExpect(status().isCreated());

        // Validate the LeLegalEntityTypes in the database
        List<LeLegalEntityTypes> leLegalEntityTypesList = leLegalEntityTypesRepository.findAll();
        assertThat(leLegalEntityTypesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLeLegalEntityTypes() throws Exception {
        // Initialize the database
        leLegalEntityTypesRepository.saveAndFlush(leLegalEntityTypes);
        int databaseSizeBeforeDelete = leLegalEntityTypesRepository.findAll().size();

        // Get the leLegalEntityTypes
        restLeLegalEntityTypesMockMvc.perform(delete("/api/le-legal-entity-types/{id}", leLegalEntityTypes.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LeLegalEntityTypes> leLegalEntityTypesList = leLegalEntityTypesRepository.findAll();
        assertThat(leLegalEntityTypesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LeLegalEntityTypes.class);
        LeLegalEntityTypes leLegalEntityTypes1 = new LeLegalEntityTypes();
        leLegalEntityTypes1.setId(1L);
        LeLegalEntityTypes leLegalEntityTypes2 = new LeLegalEntityTypes();
        leLegalEntityTypes2.setId(leLegalEntityTypes1.getId());
        assertThat(leLegalEntityTypes1).isEqualTo(leLegalEntityTypes2);
        leLegalEntityTypes2.setId(2L);
        assertThat(leLegalEntityTypes1).isNotEqualTo(leLegalEntityTypes2);
        leLegalEntityTypes1.setId(null);
        assertThat(leLegalEntityTypes1).isNotEqualTo(leLegalEntityTypes2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LeLegalEntityTypesDTO.class);
        LeLegalEntityTypesDTO leLegalEntityTypesDTO1 = new LeLegalEntityTypesDTO();
        leLegalEntityTypesDTO1.setId(1L);
        LeLegalEntityTypesDTO leLegalEntityTypesDTO2 = new LeLegalEntityTypesDTO();
        assertThat(leLegalEntityTypesDTO1).isNotEqualTo(leLegalEntityTypesDTO2);
        leLegalEntityTypesDTO2.setId(leLegalEntityTypesDTO1.getId());
        assertThat(leLegalEntityTypesDTO1).isEqualTo(leLegalEntityTypesDTO2);
        leLegalEntityTypesDTO2.setId(2L);
        assertThat(leLegalEntityTypesDTO1).isNotEqualTo(leLegalEntityTypesDTO2);
        leLegalEntityTypesDTO1.setId(null);
        assertThat(leLegalEntityTypesDTO1).isNotEqualTo(leLegalEntityTypesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(leLegalEntityTypesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(leLegalEntityTypesMapper.fromId(null)).isNull();
    }
}

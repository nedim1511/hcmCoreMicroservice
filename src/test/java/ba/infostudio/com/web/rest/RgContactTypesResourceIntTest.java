package ba.infostudio.com.web.rest;

import ba.infostudio.com.HcmCoreMicroserviceApp;

import ba.infostudio.com.domain.RgContactTypes;
import ba.infostudio.com.repository.RgContactTypesRepository;
import ba.infostudio.com.service.dto.RgContactTypesDTO;
import ba.infostudio.com.service.mapper.RgContactTypesMapper;
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
 * Test class for the RgContactTypesResource REST controller.
 *
 * @see RgContactTypesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HcmCoreMicroserviceApp.class)
public class RgContactTypesResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private RgContactTypesRepository rgContactTypesRepository;

    @Autowired
    private RgContactTypesMapper rgContactTypesMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRgContactTypesMockMvc;

    private RgContactTypes rgContactTypes;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RgContactTypesResource rgContactTypesResource = new RgContactTypesResource(rgContactTypesRepository, rgContactTypesMapper);
        this.restRgContactTypesMockMvc = MockMvcBuilders.standaloneSetup(rgContactTypesResource)
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
    public static RgContactTypes createEntity(EntityManager em) {
        RgContactTypes rgContactTypes = new RgContactTypes()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return rgContactTypes;
    }

    @Before
    public void initTest() {
        rgContactTypes = createEntity(em);
    }

    @Test
    @Transactional
    public void createRgContactTypes() throws Exception {
        int databaseSizeBeforeCreate = rgContactTypesRepository.findAll().size();

        // Create the RgContactTypes
        RgContactTypesDTO rgContactTypesDTO = rgContactTypesMapper.toDto(rgContactTypes);
        restRgContactTypesMockMvc.perform(post("/api/rg-contact-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rgContactTypesDTO)))
            .andExpect(status().isCreated());

        // Validate the RgContactTypes in the database
        List<RgContactTypes> rgContactTypesList = rgContactTypesRepository.findAll();
        assertThat(rgContactTypesList).hasSize(databaseSizeBeforeCreate + 1);
        RgContactTypes testRgContactTypes = rgContactTypesList.get(rgContactTypesList.size() - 1);
        assertThat(testRgContactTypes.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testRgContactTypes.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRgContactTypes.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createRgContactTypesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rgContactTypesRepository.findAll().size();

        // Create the RgContactTypes with an existing ID
        rgContactTypes.setId(1L);
        RgContactTypesDTO rgContactTypesDTO = rgContactTypesMapper.toDto(rgContactTypes);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRgContactTypesMockMvc.perform(post("/api/rg-contact-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rgContactTypesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RgContactTypes in the database
        List<RgContactTypes> rgContactTypesList = rgContactTypesRepository.findAll();
        assertThat(rgContactTypesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = rgContactTypesRepository.findAll().size();
        // set the field null
        rgContactTypes.setCode(null);

        // Create the RgContactTypes, which fails.
        RgContactTypesDTO rgContactTypesDTO = rgContactTypesMapper.toDto(rgContactTypes);

        restRgContactTypesMockMvc.perform(post("/api/rg-contact-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rgContactTypesDTO)))
            .andExpect(status().isBadRequest());

        List<RgContactTypes> rgContactTypesList = rgContactTypesRepository.findAll();
        assertThat(rgContactTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = rgContactTypesRepository.findAll().size();
        // set the field null
        rgContactTypes.setName(null);

        // Create the RgContactTypes, which fails.
        RgContactTypesDTO rgContactTypesDTO = rgContactTypesMapper.toDto(rgContactTypes);

        restRgContactTypesMockMvc.perform(post("/api/rg-contact-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rgContactTypesDTO)))
            .andExpect(status().isBadRequest());

        List<RgContactTypes> rgContactTypesList = rgContactTypesRepository.findAll();
        assertThat(rgContactTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRgContactTypes() throws Exception {
        // Initialize the database
        rgContactTypesRepository.saveAndFlush(rgContactTypes);

        // Get all the rgContactTypesList
        restRgContactTypesMockMvc.perform(get("/api/rg-contact-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rgContactTypes.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getRgContactTypes() throws Exception {
        // Initialize the database
        rgContactTypesRepository.saveAndFlush(rgContactTypes);

        // Get the rgContactTypes
        restRgContactTypesMockMvc.perform(get("/api/rg-contact-types/{id}", rgContactTypes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rgContactTypes.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRgContactTypes() throws Exception {
        // Get the rgContactTypes
        restRgContactTypesMockMvc.perform(get("/api/rg-contact-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRgContactTypes() throws Exception {
        // Initialize the database
        rgContactTypesRepository.saveAndFlush(rgContactTypes);
        int databaseSizeBeforeUpdate = rgContactTypesRepository.findAll().size();

        // Update the rgContactTypes
        RgContactTypes updatedRgContactTypes = rgContactTypesRepository.findOne(rgContactTypes.getId());
        // Disconnect from session so that the updates on updatedRgContactTypes are not directly saved in db
        em.detach(updatedRgContactTypes);
        updatedRgContactTypes
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        RgContactTypesDTO rgContactTypesDTO = rgContactTypesMapper.toDto(updatedRgContactTypes);

        restRgContactTypesMockMvc.perform(put("/api/rg-contact-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rgContactTypesDTO)))
            .andExpect(status().isOk());

        // Validate the RgContactTypes in the database
        List<RgContactTypes> rgContactTypesList = rgContactTypesRepository.findAll();
        assertThat(rgContactTypesList).hasSize(databaseSizeBeforeUpdate);
        RgContactTypes testRgContactTypes = rgContactTypesList.get(rgContactTypesList.size() - 1);
        assertThat(testRgContactTypes.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testRgContactTypes.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRgContactTypes.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingRgContactTypes() throws Exception {
        int databaseSizeBeforeUpdate = rgContactTypesRepository.findAll().size();

        // Create the RgContactTypes
        RgContactTypesDTO rgContactTypesDTO = rgContactTypesMapper.toDto(rgContactTypes);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRgContactTypesMockMvc.perform(put("/api/rg-contact-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rgContactTypesDTO)))
            .andExpect(status().isCreated());

        // Validate the RgContactTypes in the database
        List<RgContactTypes> rgContactTypesList = rgContactTypesRepository.findAll();
        assertThat(rgContactTypesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRgContactTypes() throws Exception {
        // Initialize the database
        rgContactTypesRepository.saveAndFlush(rgContactTypes);
        int databaseSizeBeforeDelete = rgContactTypesRepository.findAll().size();

        // Get the rgContactTypes
        restRgContactTypesMockMvc.perform(delete("/api/rg-contact-types/{id}", rgContactTypes.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RgContactTypes> rgContactTypesList = rgContactTypesRepository.findAll();
        assertThat(rgContactTypesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RgContactTypes.class);
        RgContactTypes rgContactTypes1 = new RgContactTypes();
        rgContactTypes1.setId(1L);
        RgContactTypes rgContactTypes2 = new RgContactTypes();
        rgContactTypes2.setId(rgContactTypes1.getId());
        assertThat(rgContactTypes1).isEqualTo(rgContactTypes2);
        rgContactTypes2.setId(2L);
        assertThat(rgContactTypes1).isNotEqualTo(rgContactTypes2);
        rgContactTypes1.setId(null);
        assertThat(rgContactTypes1).isNotEqualTo(rgContactTypes2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RgContactTypesDTO.class);
        RgContactTypesDTO rgContactTypesDTO1 = new RgContactTypesDTO();
        rgContactTypesDTO1.setId(1L);
        RgContactTypesDTO rgContactTypesDTO2 = new RgContactTypesDTO();
        assertThat(rgContactTypesDTO1).isNotEqualTo(rgContactTypesDTO2);
        rgContactTypesDTO2.setId(rgContactTypesDTO1.getId());
        assertThat(rgContactTypesDTO1).isEqualTo(rgContactTypesDTO2);
        rgContactTypesDTO2.setId(2L);
        assertThat(rgContactTypesDTO1).isNotEqualTo(rgContactTypesDTO2);
        rgContactTypesDTO1.setId(null);
        assertThat(rgContactTypesDTO1).isNotEqualTo(rgContactTypesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(rgContactTypesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(rgContactTypesMapper.fromId(null)).isNull();
    }
}

package ba.infostudio.com.web.rest;

import ba.infostudio.com.HcmCoreMicroserviceApp;

import ba.infostudio.com.domain.RgRegionTypes;
import ba.infostudio.com.repository.RgRegionTypesRepository;
import ba.infostudio.com.service.dto.RgRegionTypesDTO;
import ba.infostudio.com.service.mapper.RgRegionTypesMapper;
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
 * Test class for the RgRegionTypesResource REST controller.
 *
 * @see RgRegionTypesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HcmCoreMicroserviceApp.class)
public class RgRegionTypesResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private RgRegionTypesRepository rgRegionTypesRepository;

    @Autowired
    private RgRegionTypesMapper rgRegionTypesMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRgRegionTypesMockMvc;

    private RgRegionTypes rgRegionTypes;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RgRegionTypesResource rgRegionTypesResource = new RgRegionTypesResource(rgRegionTypesRepository, rgRegionTypesMapper);
        this.restRgRegionTypesMockMvc = MockMvcBuilders.standaloneSetup(rgRegionTypesResource)
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
    public static RgRegionTypes createEntity(EntityManager em) {
        RgRegionTypes rgRegionTypes = new RgRegionTypes()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return rgRegionTypes;
    }

    @Before
    public void initTest() {
        rgRegionTypes = createEntity(em);
    }

    @Test
    @Transactional
    public void createRgRegionTypes() throws Exception {
        int databaseSizeBeforeCreate = rgRegionTypesRepository.findAll().size();

        // Create the RgRegionTypes
        RgRegionTypesDTO rgRegionTypesDTO = rgRegionTypesMapper.toDto(rgRegionTypes);
        restRgRegionTypesMockMvc.perform(post("/api/rg-region-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rgRegionTypesDTO)))
            .andExpect(status().isCreated());

        // Validate the RgRegionTypes in the database
        List<RgRegionTypes> rgRegionTypesList = rgRegionTypesRepository.findAll();
        assertThat(rgRegionTypesList).hasSize(databaseSizeBeforeCreate + 1);
        RgRegionTypes testRgRegionTypes = rgRegionTypesList.get(rgRegionTypesList.size() - 1);
        assertThat(testRgRegionTypes.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testRgRegionTypes.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRgRegionTypes.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createRgRegionTypesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rgRegionTypesRepository.findAll().size();

        // Create the RgRegionTypes with an existing ID
        rgRegionTypes.setId(1L);
        RgRegionTypesDTO rgRegionTypesDTO = rgRegionTypesMapper.toDto(rgRegionTypes);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRgRegionTypesMockMvc.perform(post("/api/rg-region-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rgRegionTypesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RgRegionTypes in the database
        List<RgRegionTypes> rgRegionTypesList = rgRegionTypesRepository.findAll();
        assertThat(rgRegionTypesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = rgRegionTypesRepository.findAll().size();
        // set the field null
        rgRegionTypes.setCode(null);

        // Create the RgRegionTypes, which fails.
        RgRegionTypesDTO rgRegionTypesDTO = rgRegionTypesMapper.toDto(rgRegionTypes);

        restRgRegionTypesMockMvc.perform(post("/api/rg-region-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rgRegionTypesDTO)))
            .andExpect(status().isBadRequest());

        List<RgRegionTypes> rgRegionTypesList = rgRegionTypesRepository.findAll();
        assertThat(rgRegionTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = rgRegionTypesRepository.findAll().size();
        // set the field null
        rgRegionTypes.setName(null);

        // Create the RgRegionTypes, which fails.
        RgRegionTypesDTO rgRegionTypesDTO = rgRegionTypesMapper.toDto(rgRegionTypes);

        restRgRegionTypesMockMvc.perform(post("/api/rg-region-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rgRegionTypesDTO)))
            .andExpect(status().isBadRequest());

        List<RgRegionTypes> rgRegionTypesList = rgRegionTypesRepository.findAll();
        assertThat(rgRegionTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRgRegionTypes() throws Exception {
        // Initialize the database
        rgRegionTypesRepository.saveAndFlush(rgRegionTypes);

        // Get all the rgRegionTypesList
        restRgRegionTypesMockMvc.perform(get("/api/rg-region-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rgRegionTypes.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getRgRegionTypes() throws Exception {
        // Initialize the database
        rgRegionTypesRepository.saveAndFlush(rgRegionTypes);

        // Get the rgRegionTypes
        restRgRegionTypesMockMvc.perform(get("/api/rg-region-types/{id}", rgRegionTypes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rgRegionTypes.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRgRegionTypes() throws Exception {
        // Get the rgRegionTypes
        restRgRegionTypesMockMvc.perform(get("/api/rg-region-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRgRegionTypes() throws Exception {
        // Initialize the database
        rgRegionTypesRepository.saveAndFlush(rgRegionTypes);
        int databaseSizeBeforeUpdate = rgRegionTypesRepository.findAll().size();

        // Update the rgRegionTypes
        RgRegionTypes updatedRgRegionTypes = rgRegionTypesRepository.findOne(rgRegionTypes.getId());
        // Disconnect from session so that the updates on updatedRgRegionTypes are not directly saved in db
        em.detach(updatedRgRegionTypes);
        updatedRgRegionTypes
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        RgRegionTypesDTO rgRegionTypesDTO = rgRegionTypesMapper.toDto(updatedRgRegionTypes);

        restRgRegionTypesMockMvc.perform(put("/api/rg-region-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rgRegionTypesDTO)))
            .andExpect(status().isOk());

        // Validate the RgRegionTypes in the database
        List<RgRegionTypes> rgRegionTypesList = rgRegionTypesRepository.findAll();
        assertThat(rgRegionTypesList).hasSize(databaseSizeBeforeUpdate);
        RgRegionTypes testRgRegionTypes = rgRegionTypesList.get(rgRegionTypesList.size() - 1);
        assertThat(testRgRegionTypes.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testRgRegionTypes.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRgRegionTypes.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingRgRegionTypes() throws Exception {
        int databaseSizeBeforeUpdate = rgRegionTypesRepository.findAll().size();

        // Create the RgRegionTypes
        RgRegionTypesDTO rgRegionTypesDTO = rgRegionTypesMapper.toDto(rgRegionTypes);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRgRegionTypesMockMvc.perform(put("/api/rg-region-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rgRegionTypesDTO)))
            .andExpect(status().isCreated());

        // Validate the RgRegionTypes in the database
        List<RgRegionTypes> rgRegionTypesList = rgRegionTypesRepository.findAll();
        assertThat(rgRegionTypesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRgRegionTypes() throws Exception {
        // Initialize the database
        rgRegionTypesRepository.saveAndFlush(rgRegionTypes);
        int databaseSizeBeforeDelete = rgRegionTypesRepository.findAll().size();

        // Get the rgRegionTypes
        restRgRegionTypesMockMvc.perform(delete("/api/rg-region-types/{id}", rgRegionTypes.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RgRegionTypes> rgRegionTypesList = rgRegionTypesRepository.findAll();
        assertThat(rgRegionTypesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RgRegionTypes.class);
        RgRegionTypes rgRegionTypes1 = new RgRegionTypes();
        rgRegionTypes1.setId(1L);
        RgRegionTypes rgRegionTypes2 = new RgRegionTypes();
        rgRegionTypes2.setId(rgRegionTypes1.getId());
        assertThat(rgRegionTypes1).isEqualTo(rgRegionTypes2);
        rgRegionTypes2.setId(2L);
        assertThat(rgRegionTypes1).isNotEqualTo(rgRegionTypes2);
        rgRegionTypes1.setId(null);
        assertThat(rgRegionTypes1).isNotEqualTo(rgRegionTypes2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RgRegionTypesDTO.class);
        RgRegionTypesDTO rgRegionTypesDTO1 = new RgRegionTypesDTO();
        rgRegionTypesDTO1.setId(1L);
        RgRegionTypesDTO rgRegionTypesDTO2 = new RgRegionTypesDTO();
        assertThat(rgRegionTypesDTO1).isNotEqualTo(rgRegionTypesDTO2);
        rgRegionTypesDTO2.setId(rgRegionTypesDTO1.getId());
        assertThat(rgRegionTypesDTO1).isEqualTo(rgRegionTypesDTO2);
        rgRegionTypesDTO2.setId(2L);
        assertThat(rgRegionTypesDTO1).isNotEqualTo(rgRegionTypesDTO2);
        rgRegionTypesDTO1.setId(null);
        assertThat(rgRegionTypesDTO1).isNotEqualTo(rgRegionTypesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(rgRegionTypesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(rgRegionTypesMapper.fromId(null)).isNull();
    }
}

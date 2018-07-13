package ba.infostudio.com.web.rest;

import ba.infostudio.com.HcmCoreMicroserviceApp;

import ba.infostudio.com.domain.RgAccomplishmentTypes;
import ba.infostudio.com.repository.RgAccomplishmentTypesRepository;
import ba.infostudio.com.service.RgAccomplishmentTypesService;
import ba.infostudio.com.service.dto.RgAccomplishmentTypesDTO;
import ba.infostudio.com.service.mapper.RgAccomplishmentTypesMapper;
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
 * Test class for the RgAccomplishmentTypesResource REST controller.
 *
 * @see RgAccomplishmentTypesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HcmCoreMicroserviceApp.class)
public class RgAccomplishmentTypesResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private RgAccomplishmentTypesRepository rgAccomplishmentTypesRepository;


    @Autowired
    private RgAccomplishmentTypesMapper rgAccomplishmentTypesMapper;
    

    @Autowired
    private RgAccomplishmentTypesService rgAccomplishmentTypesService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRgAccomplishmentTypesMockMvc;

    private RgAccomplishmentTypes rgAccomplishmentTypes;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RgAccomplishmentTypesResource rgAccomplishmentTypesResource = new RgAccomplishmentTypesResource(rgAccomplishmentTypesService);
        this.restRgAccomplishmentTypesMockMvc = MockMvcBuilders.standaloneSetup(rgAccomplishmentTypesResource)
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
    public static RgAccomplishmentTypes createEntity(EntityManager em) {
        RgAccomplishmentTypes rgAccomplishmentTypes = new RgAccomplishmentTypes()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return rgAccomplishmentTypes;
    }

    @Before
    public void initTest() {
        rgAccomplishmentTypes = createEntity(em);
    }

    @Test
    @Transactional
    public void createRgAccomplishmentTypes() throws Exception {
        int databaseSizeBeforeCreate = rgAccomplishmentTypesRepository.findAll().size();

        // Create the RgAccomplishmentTypes
        RgAccomplishmentTypesDTO rgAccomplishmentTypesDTO = rgAccomplishmentTypesMapper.toDto(rgAccomplishmentTypes);
        restRgAccomplishmentTypesMockMvc.perform(post("/api/rg-accomplishment-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rgAccomplishmentTypesDTO)))
            .andExpect(status().isCreated());

        // Validate the RgAccomplishmentTypes in the database
        List<RgAccomplishmentTypes> rgAccomplishmentTypesList = rgAccomplishmentTypesRepository.findAll();
        assertThat(rgAccomplishmentTypesList).hasSize(databaseSizeBeforeCreate + 1);
        RgAccomplishmentTypes testRgAccomplishmentTypes = rgAccomplishmentTypesList.get(rgAccomplishmentTypesList.size() - 1);
        assertThat(testRgAccomplishmentTypes.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testRgAccomplishmentTypes.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRgAccomplishmentTypes.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createRgAccomplishmentTypesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rgAccomplishmentTypesRepository.findAll().size();

        // Create the RgAccomplishmentTypes with an existing ID
        rgAccomplishmentTypes.setId(1L);
        RgAccomplishmentTypesDTO rgAccomplishmentTypesDTO = rgAccomplishmentTypesMapper.toDto(rgAccomplishmentTypes);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRgAccomplishmentTypesMockMvc.perform(post("/api/rg-accomplishment-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rgAccomplishmentTypesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RgAccomplishmentTypes in the database
        List<RgAccomplishmentTypes> rgAccomplishmentTypesList = rgAccomplishmentTypesRepository.findAll();
        assertThat(rgAccomplishmentTypesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = rgAccomplishmentTypesRepository.findAll().size();
        // set the field null
        rgAccomplishmentTypes.setCode(null);

        // Create the RgAccomplishmentTypes, which fails.
        RgAccomplishmentTypesDTO rgAccomplishmentTypesDTO = rgAccomplishmentTypesMapper.toDto(rgAccomplishmentTypes);

        restRgAccomplishmentTypesMockMvc.perform(post("/api/rg-accomplishment-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rgAccomplishmentTypesDTO)))
            .andExpect(status().isBadRequest());

        List<RgAccomplishmentTypes> rgAccomplishmentTypesList = rgAccomplishmentTypesRepository.findAll();
        assertThat(rgAccomplishmentTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = rgAccomplishmentTypesRepository.findAll().size();
        // set the field null
        rgAccomplishmentTypes.setName(null);

        // Create the RgAccomplishmentTypes, which fails.
        RgAccomplishmentTypesDTO rgAccomplishmentTypesDTO = rgAccomplishmentTypesMapper.toDto(rgAccomplishmentTypes);

        restRgAccomplishmentTypesMockMvc.perform(post("/api/rg-accomplishment-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rgAccomplishmentTypesDTO)))
            .andExpect(status().isBadRequest());

        List<RgAccomplishmentTypes> rgAccomplishmentTypesList = rgAccomplishmentTypesRepository.findAll();
        assertThat(rgAccomplishmentTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRgAccomplishmentTypes() throws Exception {
        // Initialize the database
        rgAccomplishmentTypesRepository.saveAndFlush(rgAccomplishmentTypes);

        // Get all the rgAccomplishmentTypesList
        restRgAccomplishmentTypesMockMvc.perform(get("/api/rg-accomplishment-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rgAccomplishmentTypes.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    

    @Test
    @Transactional
    public void getRgAccomplishmentTypes() throws Exception {
        // Initialize the database
        rgAccomplishmentTypesRepository.saveAndFlush(rgAccomplishmentTypes);

        // Get the rgAccomplishmentTypes
        restRgAccomplishmentTypesMockMvc.perform(get("/api/rg-accomplishment-types/{id}", rgAccomplishmentTypes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rgAccomplishmentTypes.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingRgAccomplishmentTypes() throws Exception {
        // Get the rgAccomplishmentTypes
        restRgAccomplishmentTypesMockMvc.perform(get("/api/rg-accomplishment-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRgAccomplishmentTypes() throws Exception {
        // Initialize the database
        rgAccomplishmentTypesRepository.saveAndFlush(rgAccomplishmentTypes);

        int databaseSizeBeforeUpdate = rgAccomplishmentTypesRepository.findAll().size();

        // Update the rgAccomplishmentTypes
        RgAccomplishmentTypes updatedRgAccomplishmentTypes = rgAccomplishmentTypesRepository.findById(rgAccomplishmentTypes.getId()).get();
        // Disconnect from session so that the updates on updatedRgAccomplishmentTypes are not directly saved in db
        em.detach(updatedRgAccomplishmentTypes);
        updatedRgAccomplishmentTypes
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        RgAccomplishmentTypesDTO rgAccomplishmentTypesDTO = rgAccomplishmentTypesMapper.toDto(updatedRgAccomplishmentTypes);

        restRgAccomplishmentTypesMockMvc.perform(put("/api/rg-accomplishment-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rgAccomplishmentTypesDTO)))
            .andExpect(status().isOk());

        // Validate the RgAccomplishmentTypes in the database
        List<RgAccomplishmentTypes> rgAccomplishmentTypesList = rgAccomplishmentTypesRepository.findAll();
        assertThat(rgAccomplishmentTypesList).hasSize(databaseSizeBeforeUpdate);
        RgAccomplishmentTypes testRgAccomplishmentTypes = rgAccomplishmentTypesList.get(rgAccomplishmentTypesList.size() - 1);
        assertThat(testRgAccomplishmentTypes.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testRgAccomplishmentTypes.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRgAccomplishmentTypes.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingRgAccomplishmentTypes() throws Exception {
        int databaseSizeBeforeUpdate = rgAccomplishmentTypesRepository.findAll().size();

        // Create the RgAccomplishmentTypes
        RgAccomplishmentTypesDTO rgAccomplishmentTypesDTO = rgAccomplishmentTypesMapper.toDto(rgAccomplishmentTypes);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRgAccomplishmentTypesMockMvc.perform(put("/api/rg-accomplishment-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rgAccomplishmentTypesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RgAccomplishmentTypes in the database
        List<RgAccomplishmentTypes> rgAccomplishmentTypesList = rgAccomplishmentTypesRepository.findAll();
        assertThat(rgAccomplishmentTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRgAccomplishmentTypes() throws Exception {
        // Initialize the database
        rgAccomplishmentTypesRepository.saveAndFlush(rgAccomplishmentTypes);

        int databaseSizeBeforeDelete = rgAccomplishmentTypesRepository.findAll().size();

        // Get the rgAccomplishmentTypes
        restRgAccomplishmentTypesMockMvc.perform(delete("/api/rg-accomplishment-types/{id}", rgAccomplishmentTypes.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RgAccomplishmentTypes> rgAccomplishmentTypesList = rgAccomplishmentTypesRepository.findAll();
        assertThat(rgAccomplishmentTypesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RgAccomplishmentTypes.class);
        RgAccomplishmentTypes rgAccomplishmentTypes1 = new RgAccomplishmentTypes();
        rgAccomplishmentTypes1.setId(1L);
        RgAccomplishmentTypes rgAccomplishmentTypes2 = new RgAccomplishmentTypes();
        rgAccomplishmentTypes2.setId(rgAccomplishmentTypes1.getId());
        assertThat(rgAccomplishmentTypes1).isEqualTo(rgAccomplishmentTypes2);
        rgAccomplishmentTypes2.setId(2L);
        assertThat(rgAccomplishmentTypes1).isNotEqualTo(rgAccomplishmentTypes2);
        rgAccomplishmentTypes1.setId(null);
        assertThat(rgAccomplishmentTypes1).isNotEqualTo(rgAccomplishmentTypes2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RgAccomplishmentTypesDTO.class);
        RgAccomplishmentTypesDTO rgAccomplishmentTypesDTO1 = new RgAccomplishmentTypesDTO();
        rgAccomplishmentTypesDTO1.setId(1L);
        RgAccomplishmentTypesDTO rgAccomplishmentTypesDTO2 = new RgAccomplishmentTypesDTO();
        assertThat(rgAccomplishmentTypesDTO1).isNotEqualTo(rgAccomplishmentTypesDTO2);
        rgAccomplishmentTypesDTO2.setId(rgAccomplishmentTypesDTO1.getId());
        assertThat(rgAccomplishmentTypesDTO1).isEqualTo(rgAccomplishmentTypesDTO2);
        rgAccomplishmentTypesDTO2.setId(2L);
        assertThat(rgAccomplishmentTypesDTO1).isNotEqualTo(rgAccomplishmentTypesDTO2);
        rgAccomplishmentTypesDTO1.setId(null);
        assertThat(rgAccomplishmentTypesDTO1).isNotEqualTo(rgAccomplishmentTypesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(rgAccomplishmentTypesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(rgAccomplishmentTypesMapper.fromId(null)).isNull();
    }
}

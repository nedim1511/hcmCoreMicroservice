package ba.infostudio.com.web.rest;

import ba.infostudio.com.HcmCoreMicroserviceApp;

import ba.infostudio.com.domain.ApConstants;
import ba.infostudio.com.repository.ApConstantsRepository;
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
 * Test class for the ApConstantsResource REST controller.
 *
 * @see ApConstantsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HcmCoreMicroserviceApp.class)
public class ApConstantsResourceIntTest {

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private ApConstantsRepository apConstantsRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restApConstantsMockMvc;

    private ApConstants apConstants;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ApConstantsResource apConstantsResource = new ApConstantsResource(apConstantsRepository);
        this.restApConstantsMockMvc = MockMvcBuilders.standaloneSetup(apConstantsResource)
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
    public static ApConstants createEntity(EntityManager em) {
        ApConstants apConstants = new ApConstants()
            .key(DEFAULT_KEY)
            .value(DEFAULT_VALUE);
        return apConstants;
    }

    @Before
    public void initTest() {
        apConstants = createEntity(em);
    }

    @Test
    @Transactional
    public void createApConstants() throws Exception {
        int databaseSizeBeforeCreate = apConstantsRepository.findAll().size();

        // Create the ApConstants
        restApConstantsMockMvc.perform(post("/api/ap-constants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apConstants)))
            .andExpect(status().isCreated());

        // Validate the ApConstants in the database
        List<ApConstants> apConstantsList = apConstantsRepository.findAll();
        assertThat(apConstantsList).hasSize(databaseSizeBeforeCreate + 1);
        ApConstants testApConstants = apConstantsList.get(apConstantsList.size() - 1);
        assertThat(testApConstants.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testApConstants.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createApConstantsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = apConstantsRepository.findAll().size();

        // Create the ApConstants with an existing ID
        apConstants.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApConstantsMockMvc.perform(post("/api/ap-constants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apConstants)))
            .andExpect(status().isBadRequest());

        // Validate the ApConstants in the database
        List<ApConstants> apConstantsList = apConstantsRepository.findAll();
        assertThat(apConstantsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllApConstants() throws Exception {
        // Initialize the database
        apConstantsRepository.saveAndFlush(apConstants);

        // Get all the apConstantsList
        restApConstantsMockMvc.perform(get("/api/ap-constants?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(apConstants.getId().intValue())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }

    @Test
    @Transactional
    public void getApConstants() throws Exception {
        // Initialize the database
        apConstantsRepository.saveAndFlush(apConstants);

        // Get the apConstants
        restApConstantsMockMvc.perform(get("/api/ap-constants/{id}", apConstants.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(apConstants.getId().intValue()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingApConstants() throws Exception {
        // Get the apConstants
        restApConstantsMockMvc.perform(get("/api/ap-constants/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApConstants() throws Exception {
        // Initialize the database
        apConstantsRepository.saveAndFlush(apConstants);
        int databaseSizeBeforeUpdate = apConstantsRepository.findAll().size();

        // Update the apConstants
        ApConstants updatedApConstants = apConstantsRepository.findOne(apConstants.getId());
        // Disconnect from session so that the updates on updatedApConstants are not directly saved in db
        em.detach(updatedApConstants);
        updatedApConstants
            .key(UPDATED_KEY)
            .value(UPDATED_VALUE);

        restApConstantsMockMvc.perform(put("/api/ap-constants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedApConstants)))
            .andExpect(status().isOk());

        // Validate the ApConstants in the database
        List<ApConstants> apConstantsList = apConstantsRepository.findAll();
        assertThat(apConstantsList).hasSize(databaseSizeBeforeUpdate);
        ApConstants testApConstants = apConstantsList.get(apConstantsList.size() - 1);
        assertThat(testApConstants.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testApConstants.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingApConstants() throws Exception {
        int databaseSizeBeforeUpdate = apConstantsRepository.findAll().size();

        // Create the ApConstants

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restApConstantsMockMvc.perform(put("/api/ap-constants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apConstants)))
            .andExpect(status().isCreated());

        // Validate the ApConstants in the database
        List<ApConstants> apConstantsList = apConstantsRepository.findAll();
        assertThat(apConstantsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteApConstants() throws Exception {
        // Initialize the database
        apConstantsRepository.saveAndFlush(apConstants);
        int databaseSizeBeforeDelete = apConstantsRepository.findAll().size();

        // Get the apConstants
        restApConstantsMockMvc.perform(delete("/api/ap-constants/{id}", apConstants.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ApConstants> apConstantsList = apConstantsRepository.findAll();
        assertThat(apConstantsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApConstants.class);
        ApConstants apConstants1 = new ApConstants();
        apConstants1.setId(1L);
        ApConstants apConstants2 = new ApConstants();
        apConstants2.setId(apConstants1.getId());
        assertThat(apConstants1).isEqualTo(apConstants2);
        apConstants2.setId(2L);
        assertThat(apConstants1).isNotEqualTo(apConstants2);
        apConstants1.setId(null);
        assertThat(apConstants1).isNotEqualTo(apConstants2);
    }
}

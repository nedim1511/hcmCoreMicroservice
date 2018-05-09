package ba.infostudio.com.web.rest;

import ba.infostudio.com.HcmCoreMicroserviceApp;

import ba.infostudio.com.domain.RgCurrency;
import ba.infostudio.com.repository.RgCurrencyRepository;
import ba.infostudio.com.service.RgCurrencyService;
import ba.infostudio.com.service.dto.RgCurrencyDTO;
import ba.infostudio.com.service.mapper.RgCurrencyMapper;
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
 * Test class for the RgCurrencyResource REST controller.
 *
 * @see RgCurrencyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HcmCoreMicroserviceApp.class)
public class RgCurrencyResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private RgCurrencyRepository rgCurrencyRepository;

    @Autowired
    private RgCurrencyMapper rgCurrencyMapper;

    @Autowired
    private RgCurrencyService rgCurrencyService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRgCurrencyMockMvc;

    private RgCurrency rgCurrency;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RgCurrencyResource rgCurrencyResource = new RgCurrencyResource(rgCurrencyService);
        this.restRgCurrencyMockMvc = MockMvcBuilders.standaloneSetup(rgCurrencyResource)
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
    public static RgCurrency createEntity(EntityManager em) {
        RgCurrency rgCurrency = new RgCurrency()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME);
        return rgCurrency;
    }

    @Before
    public void initTest() {
        rgCurrency = createEntity(em);
    }

    @Test
    @Transactional
    public void createRgCurrency() throws Exception {
        int databaseSizeBeforeCreate = rgCurrencyRepository.findAll().size();

        // Create the RgCurrency
        RgCurrencyDTO rgCurrencyDTO = rgCurrencyMapper.toDto(rgCurrency);
        restRgCurrencyMockMvc.perform(post("/api/rg-currencies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rgCurrencyDTO)))
            .andExpect(status().isCreated());

        // Validate the RgCurrency in the database
        List<RgCurrency> rgCurrencyList = rgCurrencyRepository.findAll();
        assertThat(rgCurrencyList).hasSize(databaseSizeBeforeCreate + 1);
        RgCurrency testRgCurrency = rgCurrencyList.get(rgCurrencyList.size() - 1);
        assertThat(testRgCurrency.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testRgCurrency.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createRgCurrencyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rgCurrencyRepository.findAll().size();

        // Create the RgCurrency with an existing ID
        rgCurrency.setId(1L);
        RgCurrencyDTO rgCurrencyDTO = rgCurrencyMapper.toDto(rgCurrency);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRgCurrencyMockMvc.perform(post("/api/rg-currencies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rgCurrencyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RgCurrency in the database
        List<RgCurrency> rgCurrencyList = rgCurrencyRepository.findAll();
        assertThat(rgCurrencyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRgCurrencies() throws Exception {
        // Initialize the database
        rgCurrencyRepository.saveAndFlush(rgCurrency);

        // Get all the rgCurrencyList
        restRgCurrencyMockMvc.perform(get("/api/rg-currencies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rgCurrency.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getRgCurrency() throws Exception {
        // Initialize the database
        rgCurrencyRepository.saveAndFlush(rgCurrency);

        // Get the rgCurrency
        restRgCurrencyMockMvc.perform(get("/api/rg-currencies/{id}", rgCurrency.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rgCurrency.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRgCurrency() throws Exception {
        // Get the rgCurrency
        restRgCurrencyMockMvc.perform(get("/api/rg-currencies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRgCurrency() throws Exception {
        // Initialize the database
        rgCurrencyRepository.saveAndFlush(rgCurrency);
        int databaseSizeBeforeUpdate = rgCurrencyRepository.findAll().size();

        // Update the rgCurrency
        RgCurrency updatedRgCurrency = rgCurrencyRepository.findOne(rgCurrency.getId());
        // Disconnect from session so that the updates on updatedRgCurrency are not directly saved in db
        em.detach(updatedRgCurrency);
        updatedRgCurrency
            .code(UPDATED_CODE)
            .name(UPDATED_NAME);
        RgCurrencyDTO rgCurrencyDTO = rgCurrencyMapper.toDto(updatedRgCurrency);

        restRgCurrencyMockMvc.perform(put("/api/rg-currencies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rgCurrencyDTO)))
            .andExpect(status().isOk());

        // Validate the RgCurrency in the database
        List<RgCurrency> rgCurrencyList = rgCurrencyRepository.findAll();
        assertThat(rgCurrencyList).hasSize(databaseSizeBeforeUpdate);
        RgCurrency testRgCurrency = rgCurrencyList.get(rgCurrencyList.size() - 1);
        assertThat(testRgCurrency.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testRgCurrency.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingRgCurrency() throws Exception {
        int databaseSizeBeforeUpdate = rgCurrencyRepository.findAll().size();

        // Create the RgCurrency
        RgCurrencyDTO rgCurrencyDTO = rgCurrencyMapper.toDto(rgCurrency);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRgCurrencyMockMvc.perform(put("/api/rg-currencies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rgCurrencyDTO)))
            .andExpect(status().isCreated());

        // Validate the RgCurrency in the database
        List<RgCurrency> rgCurrencyList = rgCurrencyRepository.findAll();
        assertThat(rgCurrencyList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRgCurrency() throws Exception {
        // Initialize the database
        rgCurrencyRepository.saveAndFlush(rgCurrency);
        int databaseSizeBeforeDelete = rgCurrencyRepository.findAll().size();

        // Get the rgCurrency
        restRgCurrencyMockMvc.perform(delete("/api/rg-currencies/{id}", rgCurrency.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RgCurrency> rgCurrencyList = rgCurrencyRepository.findAll();
        assertThat(rgCurrencyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RgCurrency.class);
        RgCurrency rgCurrency1 = new RgCurrency();
        rgCurrency1.setId(1L);
        RgCurrency rgCurrency2 = new RgCurrency();
        rgCurrency2.setId(rgCurrency1.getId());
        assertThat(rgCurrency1).isEqualTo(rgCurrency2);
        rgCurrency2.setId(2L);
        assertThat(rgCurrency1).isNotEqualTo(rgCurrency2);
        rgCurrency1.setId(null);
        assertThat(rgCurrency1).isNotEqualTo(rgCurrency2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RgCurrencyDTO.class);
        RgCurrencyDTO rgCurrencyDTO1 = new RgCurrencyDTO();
        rgCurrencyDTO1.setId(1L);
        RgCurrencyDTO rgCurrencyDTO2 = new RgCurrencyDTO();
        assertThat(rgCurrencyDTO1).isNotEqualTo(rgCurrencyDTO2);
        rgCurrencyDTO2.setId(rgCurrencyDTO1.getId());
        assertThat(rgCurrencyDTO1).isEqualTo(rgCurrencyDTO2);
        rgCurrencyDTO2.setId(2L);
        assertThat(rgCurrencyDTO1).isNotEqualTo(rgCurrencyDTO2);
        rgCurrencyDTO1.setId(null);
        assertThat(rgCurrencyDTO1).isNotEqualTo(rgCurrencyDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(rgCurrencyMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(rgCurrencyMapper.fromId(null)).isNull();
    }
}

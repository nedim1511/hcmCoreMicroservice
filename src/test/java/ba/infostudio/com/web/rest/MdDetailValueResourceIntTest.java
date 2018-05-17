package ba.infostudio.com.web.rest;

import ba.infostudio.com.HcmCoreMicroserviceApp;

import ba.infostudio.com.domain.MdDetailValue;
import ba.infostudio.com.repository.MdDetailValueRepository;
import ba.infostudio.com.service.dto.MdDetailValueDTO;
import ba.infostudio.com.service.mapper.MdDetailValueMapper;
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
 * Test class for the MdDetailValueResource REST controller.
 *
 * @see MdDetailValueResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HcmCoreMicroserviceApp.class)
public class MdDetailValueResourceIntTest {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_DISPLAY_VALUES = "AAAAAAAAAA";
    private static final String UPDATED_DISPLAY_VALUES = "BBBBBBBBBB";

    private static final Integer DEFAULT_ORDERING = 1;
    private static final Integer UPDATED_ORDERING = 2;

    @Autowired
    private MdDetailValueRepository mdDetailValueRepository;

    @Autowired
    private MdDetailValueMapper mdDetailValueMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMdDetailValueMockMvc;

    private MdDetailValue mdDetailValue;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MdDetailValueResource mdDetailValueResource = new MdDetailValueResource(mdDetailValueRepository, mdDetailValueMapper);
        this.restMdDetailValueMockMvc = MockMvcBuilders.standaloneSetup(mdDetailValueResource)
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
    public static MdDetailValue createEntity(EntityManager em) {
        MdDetailValue mdDetailValue = new MdDetailValue()
            .value(DEFAULT_VALUE)
            .displayValues(DEFAULT_DISPLAY_VALUES)
            .ordering(DEFAULT_ORDERING);
        return mdDetailValue;
    }

    @Before
    public void initTest() {
        mdDetailValue = createEntity(em);
    }

    @Test
    @Transactional
    public void createMdDetailValue() throws Exception {
        int databaseSizeBeforeCreate = mdDetailValueRepository.findAll().size();

        // Create the MdDetailValue
        MdDetailValueDTO mdDetailValueDTO = mdDetailValueMapper.toDto(mdDetailValue);
        restMdDetailValueMockMvc.perform(post("/api/md-detail-values")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mdDetailValueDTO)))
            .andExpect(status().isCreated());

        // Validate the MdDetailValue in the database
        List<MdDetailValue> mdDetailValueList = mdDetailValueRepository.findAll();
        assertThat(mdDetailValueList).hasSize(databaseSizeBeforeCreate + 1);
        MdDetailValue testMdDetailValue = mdDetailValueList.get(mdDetailValueList.size() - 1);
        assertThat(testMdDetailValue.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testMdDetailValue.getDisplayValues()).isEqualTo(DEFAULT_DISPLAY_VALUES);
        assertThat(testMdDetailValue.getOrdering()).isEqualTo(DEFAULT_ORDERING);
    }

    @Test
    @Transactional
    public void createMdDetailValueWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mdDetailValueRepository.findAll().size();

        // Create the MdDetailValue with an existing ID
        mdDetailValue.setId(1L);
        MdDetailValueDTO mdDetailValueDTO = mdDetailValueMapper.toDto(mdDetailValue);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMdDetailValueMockMvc.perform(post("/api/md-detail-values")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mdDetailValueDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MdDetailValue in the database
        List<MdDetailValue> mdDetailValueList = mdDetailValueRepository.findAll();
        assertThat(mdDetailValueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = mdDetailValueRepository.findAll().size();
        // set the field null
        mdDetailValue.setValue(null);

        // Create the MdDetailValue, which fails.
        MdDetailValueDTO mdDetailValueDTO = mdDetailValueMapper.toDto(mdDetailValue);

        restMdDetailValueMockMvc.perform(post("/api/md-detail-values")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mdDetailValueDTO)))
            .andExpect(status().isBadRequest());

        List<MdDetailValue> mdDetailValueList = mdDetailValueRepository.findAll();
        assertThat(mdDetailValueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDisplayValuesIsRequired() throws Exception {
        int databaseSizeBeforeTest = mdDetailValueRepository.findAll().size();
        // set the field null
        mdDetailValue.setDisplayValues(null);

        // Create the MdDetailValue, which fails.
        MdDetailValueDTO mdDetailValueDTO = mdDetailValueMapper.toDto(mdDetailValue);

        restMdDetailValueMockMvc.perform(post("/api/md-detail-values")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mdDetailValueDTO)))
            .andExpect(status().isBadRequest());

        List<MdDetailValue> mdDetailValueList = mdDetailValueRepository.findAll();
        assertThat(mdDetailValueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMdDetailValues() throws Exception {
        // Initialize the database
        mdDetailValueRepository.saveAndFlush(mdDetailValue);

        // Get all the mdDetailValueList
        restMdDetailValueMockMvc.perform(get("/api/md-detail-values?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mdDetailValue.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())))
            .andExpect(jsonPath("$.[*].displayValues").value(hasItem(DEFAULT_DISPLAY_VALUES.toString())))
            .andExpect(jsonPath("$.[*].ordering").value(hasItem(DEFAULT_ORDERING)));
    }

    @Test
    @Transactional
    public void getMdDetailValue() throws Exception {
        // Initialize the database
        mdDetailValueRepository.saveAndFlush(mdDetailValue);

        // Get the mdDetailValue
        restMdDetailValueMockMvc.perform(get("/api/md-detail-values/{id}", mdDetailValue.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mdDetailValue.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()))
            .andExpect(jsonPath("$.displayValues").value(DEFAULT_DISPLAY_VALUES.toString()))
            .andExpect(jsonPath("$.ordering").value(DEFAULT_ORDERING));
    }

    @Test
    @Transactional
    public void getNonExistingMdDetailValue() throws Exception {
        // Get the mdDetailValue
        restMdDetailValueMockMvc.perform(get("/api/md-detail-values/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMdDetailValue() throws Exception {
        // Initialize the database
        mdDetailValueRepository.saveAndFlush(mdDetailValue);
        int databaseSizeBeforeUpdate = mdDetailValueRepository.findAll().size();

        // Update the mdDetailValue
        MdDetailValue updatedMdDetailValue = mdDetailValueRepository.findOne(mdDetailValue.getId());
        // Disconnect from session so that the updates on updatedMdDetailValue are not directly saved in db
        em.detach(updatedMdDetailValue);
        updatedMdDetailValue
            .value(UPDATED_VALUE)
            .displayValues(UPDATED_DISPLAY_VALUES)
            .ordering(UPDATED_ORDERING);
        MdDetailValueDTO mdDetailValueDTO = mdDetailValueMapper.toDto(updatedMdDetailValue);

        restMdDetailValueMockMvc.perform(put("/api/md-detail-values")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mdDetailValueDTO)))
            .andExpect(status().isOk());

        // Validate the MdDetailValue in the database
        List<MdDetailValue> mdDetailValueList = mdDetailValueRepository.findAll();
        assertThat(mdDetailValueList).hasSize(databaseSizeBeforeUpdate);
        MdDetailValue testMdDetailValue = mdDetailValueList.get(mdDetailValueList.size() - 1);
        assertThat(testMdDetailValue.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testMdDetailValue.getDisplayValues()).isEqualTo(UPDATED_DISPLAY_VALUES);
        assertThat(testMdDetailValue.getOrdering()).isEqualTo(UPDATED_ORDERING);
    }

    @Test
    @Transactional
    public void updateNonExistingMdDetailValue() throws Exception {
        int databaseSizeBeforeUpdate = mdDetailValueRepository.findAll().size();

        // Create the MdDetailValue
        MdDetailValueDTO mdDetailValueDTO = mdDetailValueMapper.toDto(mdDetailValue);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMdDetailValueMockMvc.perform(put("/api/md-detail-values")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mdDetailValueDTO)))
            .andExpect(status().isCreated());

        // Validate the MdDetailValue in the database
        List<MdDetailValue> mdDetailValueList = mdDetailValueRepository.findAll();
        assertThat(mdDetailValueList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMdDetailValue() throws Exception {
        // Initialize the database
        mdDetailValueRepository.saveAndFlush(mdDetailValue);
        int databaseSizeBeforeDelete = mdDetailValueRepository.findAll().size();

        // Get the mdDetailValue
        restMdDetailValueMockMvc.perform(delete("/api/md-detail-values/{id}", mdDetailValue.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MdDetailValue> mdDetailValueList = mdDetailValueRepository.findAll();
        assertThat(mdDetailValueList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MdDetailValue.class);
        MdDetailValue mdDetailValue1 = new MdDetailValue();
        mdDetailValue1.setId(1L);
        MdDetailValue mdDetailValue2 = new MdDetailValue();
        mdDetailValue2.setId(mdDetailValue1.getId());
        assertThat(mdDetailValue1).isEqualTo(mdDetailValue2);
        mdDetailValue2.setId(2L);
        assertThat(mdDetailValue1).isNotEqualTo(mdDetailValue2);
        mdDetailValue1.setId(null);
        assertThat(mdDetailValue1).isNotEqualTo(mdDetailValue2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MdDetailValueDTO.class);
        MdDetailValueDTO mdDetailValueDTO1 = new MdDetailValueDTO();
        mdDetailValueDTO1.setId(1L);
        MdDetailValueDTO mdDetailValueDTO2 = new MdDetailValueDTO();
        assertThat(mdDetailValueDTO1).isNotEqualTo(mdDetailValueDTO2);
        mdDetailValueDTO2.setId(mdDetailValueDTO1.getId());
        assertThat(mdDetailValueDTO1).isEqualTo(mdDetailValueDTO2);
        mdDetailValueDTO2.setId(2L);
        assertThat(mdDetailValueDTO1).isNotEqualTo(mdDetailValueDTO2);
        mdDetailValueDTO1.setId(null);
        assertThat(mdDetailValueDTO1).isNotEqualTo(mdDetailValueDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(mdDetailValueMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(mdDetailValueMapper.fromId(null)).isNull();
    }
}

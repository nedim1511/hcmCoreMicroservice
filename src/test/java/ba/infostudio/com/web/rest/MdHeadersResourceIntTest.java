package ba.infostudio.com.web.rest;

import ba.infostudio.com.HcmCoreMicroserviceApp;

import ba.infostudio.com.domain.MdHeaders;
import ba.infostudio.com.repository.MdHeadersRepository;
import ba.infostudio.com.service.dto.MdHeadersDTO;
import ba.infostudio.com.service.mapper.MdHeadersMapper;
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
 * Test class for the MdHeadersResource REST controller.
 *
 * @see MdHeadersResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HcmCoreMicroserviceApp.class)
public class MdHeadersResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private MdHeadersRepository mdHeadersRepository;

    @Autowired
    private MdHeadersMapper mdHeadersMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMdHeadersMockMvc;

    private MdHeaders mdHeaders;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MdHeadersResource mdHeadersResource = new MdHeadersResource(mdHeadersRepository, mdHeadersMapper);
        this.restMdHeadersMockMvc = MockMvcBuilders.standaloneSetup(mdHeadersResource)
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
    public static MdHeaders createEntity(EntityManager em) {
        MdHeaders mdHeaders = new MdHeaders()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return mdHeaders;
    }

    @Before
    public void initTest() {
        mdHeaders = createEntity(em);
    }

    @Test
    @Transactional
    public void createMdHeaders() throws Exception {
        int databaseSizeBeforeCreate = mdHeadersRepository.findAll().size();

        // Create the MdHeaders
        MdHeadersDTO mdHeadersDTO = mdHeadersMapper.toDto(mdHeaders);
        restMdHeadersMockMvc.perform(post("/api/md-headers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mdHeadersDTO)))
            .andExpect(status().isCreated());

        // Validate the MdHeaders in the database
        List<MdHeaders> mdHeadersList = mdHeadersRepository.findAll();
        assertThat(mdHeadersList).hasSize(databaseSizeBeforeCreate + 1);
        MdHeaders testMdHeaders = mdHeadersList.get(mdHeadersList.size() - 1);
        assertThat(testMdHeaders.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testMdHeaders.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMdHeaders.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createMdHeadersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mdHeadersRepository.findAll().size();

        // Create the MdHeaders with an existing ID
        mdHeaders.setId(1L);
        MdHeadersDTO mdHeadersDTO = mdHeadersMapper.toDto(mdHeaders);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMdHeadersMockMvc.perform(post("/api/md-headers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mdHeadersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MdHeaders in the database
        List<MdHeaders> mdHeadersList = mdHeadersRepository.findAll();
        assertThat(mdHeadersList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = mdHeadersRepository.findAll().size();
        // set the field null
        mdHeaders.setCode(null);

        // Create the MdHeaders, which fails.
        MdHeadersDTO mdHeadersDTO = mdHeadersMapper.toDto(mdHeaders);

        restMdHeadersMockMvc.perform(post("/api/md-headers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mdHeadersDTO)))
            .andExpect(status().isBadRequest());

        List<MdHeaders> mdHeadersList = mdHeadersRepository.findAll();
        assertThat(mdHeadersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = mdHeadersRepository.findAll().size();
        // set the field null
        mdHeaders.setName(null);

        // Create the MdHeaders, which fails.
        MdHeadersDTO mdHeadersDTO = mdHeadersMapper.toDto(mdHeaders);

        restMdHeadersMockMvc.perform(post("/api/md-headers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mdHeadersDTO)))
            .andExpect(status().isBadRequest());

        List<MdHeaders> mdHeadersList = mdHeadersRepository.findAll();
        assertThat(mdHeadersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMdHeaders() throws Exception {
        // Initialize the database
        mdHeadersRepository.saveAndFlush(mdHeaders);

        // Get all the mdHeadersList
        restMdHeadersMockMvc.perform(get("/api/md-headers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mdHeaders.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getMdHeaders() throws Exception {
        // Initialize the database
        mdHeadersRepository.saveAndFlush(mdHeaders);

        // Get the mdHeaders
        restMdHeadersMockMvc.perform(get("/api/md-headers/{id}", mdHeaders.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mdHeaders.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMdHeaders() throws Exception {
        // Get the mdHeaders
        restMdHeadersMockMvc.perform(get("/api/md-headers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMdHeaders() throws Exception {
        // Initialize the database
        mdHeadersRepository.saveAndFlush(mdHeaders);
        int databaseSizeBeforeUpdate = mdHeadersRepository.findAll().size();

        // Update the mdHeaders
        MdHeaders updatedMdHeaders = mdHeadersRepository.findOne(mdHeaders.getId());
        // Disconnect from session so that the updates on updatedMdHeaders are not directly saved in db
        em.detach(updatedMdHeaders);
        updatedMdHeaders
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        MdHeadersDTO mdHeadersDTO = mdHeadersMapper.toDto(updatedMdHeaders);

        restMdHeadersMockMvc.perform(put("/api/md-headers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mdHeadersDTO)))
            .andExpect(status().isOk());

        // Validate the MdHeaders in the database
        List<MdHeaders> mdHeadersList = mdHeadersRepository.findAll();
        assertThat(mdHeadersList).hasSize(databaseSizeBeforeUpdate);
        MdHeaders testMdHeaders = mdHeadersList.get(mdHeadersList.size() - 1);
        assertThat(testMdHeaders.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testMdHeaders.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMdHeaders.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingMdHeaders() throws Exception {
        int databaseSizeBeforeUpdate = mdHeadersRepository.findAll().size();

        // Create the MdHeaders
        MdHeadersDTO mdHeadersDTO = mdHeadersMapper.toDto(mdHeaders);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMdHeadersMockMvc.perform(put("/api/md-headers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mdHeadersDTO)))
            .andExpect(status().isCreated());

        // Validate the MdHeaders in the database
        List<MdHeaders> mdHeadersList = mdHeadersRepository.findAll();
        assertThat(mdHeadersList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMdHeaders() throws Exception {
        // Initialize the database
        mdHeadersRepository.saveAndFlush(mdHeaders);
        int databaseSizeBeforeDelete = mdHeadersRepository.findAll().size();

        // Get the mdHeaders
        restMdHeadersMockMvc.perform(delete("/api/md-headers/{id}", mdHeaders.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MdHeaders> mdHeadersList = mdHeadersRepository.findAll();
        assertThat(mdHeadersList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MdHeaders.class);
        MdHeaders mdHeaders1 = new MdHeaders();
        mdHeaders1.setId(1L);
        MdHeaders mdHeaders2 = new MdHeaders();
        mdHeaders2.setId(mdHeaders1.getId());
        assertThat(mdHeaders1).isEqualTo(mdHeaders2);
        mdHeaders2.setId(2L);
        assertThat(mdHeaders1).isNotEqualTo(mdHeaders2);
        mdHeaders1.setId(null);
        assertThat(mdHeaders1).isNotEqualTo(mdHeaders2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MdHeadersDTO.class);
        MdHeadersDTO mdHeadersDTO1 = new MdHeadersDTO();
        mdHeadersDTO1.setId(1L);
        MdHeadersDTO mdHeadersDTO2 = new MdHeadersDTO();
        assertThat(mdHeadersDTO1).isNotEqualTo(mdHeadersDTO2);
        mdHeadersDTO2.setId(mdHeadersDTO1.getId());
        assertThat(mdHeadersDTO1).isEqualTo(mdHeadersDTO2);
        mdHeadersDTO2.setId(2L);
        assertThat(mdHeadersDTO1).isNotEqualTo(mdHeadersDTO2);
        mdHeadersDTO1.setId(null);
        assertThat(mdHeadersDTO1).isNotEqualTo(mdHeadersDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(mdHeadersMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(mdHeadersMapper.fromId(null)).isNull();
    }
}

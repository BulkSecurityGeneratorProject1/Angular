package com.blockbrain.propmgmnt.web.rest;

import com.blockbrain.propmgmnt.PropMgmntApp;

import com.blockbrain.propmgmnt.domain.Mflapartment;
import com.blockbrain.propmgmnt.repository.MflapartmentRepository;
import com.blockbrain.propmgmnt.web.rest.errors.ExceptionTranslator;

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


import static com.blockbrain.propmgmnt.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MflapartmentResource REST controller.
 *
 * @see MflapartmentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PropMgmntApp.class)
public class MflapartmentResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_OWNER = "AAAAAAAAAA";
    private static final String UPDATED_OWNER = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_LOAN_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_LOAN_NUMBER = "BBBBBBBBBB";

    @Autowired
    private MflapartmentRepository mflapartmentRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMflapartmentMockMvc;

    private Mflapartment mflapartment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MflapartmentResource mflapartmentResource = new MflapartmentResource(mflapartmentRepository);
        this.restMflapartmentMockMvc = MockMvcBuilders.standaloneSetup(mflapartmentResource)
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
    public static Mflapartment createEntity(EntityManager em) {
        Mflapartment mflapartment = new Mflapartment()
            .name(DEFAULT_NAME)
            .owner(DEFAULT_OWNER)
            .address(DEFAULT_ADDRESS)
            .loanNumber(DEFAULT_LOAN_NUMBER);
        return mflapartment;
    }

    @Before
    public void initTest() {
        mflapartment = createEntity(em);
    }

    @Test
    @Transactional
    public void createMflapartment() throws Exception {
        int databaseSizeBeforeCreate = mflapartmentRepository.findAll().size();

        // Create the Mflapartment
        restMflapartmentMockMvc.perform(post("/api/mflapartments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mflapartment)))
            .andExpect(status().isCreated());

        // Validate the Mflapartment in the database
        List<Mflapartment> mflapartmentList = mflapartmentRepository.findAll();
        assertThat(mflapartmentList).hasSize(databaseSizeBeforeCreate + 1);
        Mflapartment testMflapartment = mflapartmentList.get(mflapartmentList.size() - 1);
        assertThat(testMflapartment.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMflapartment.getOwner()).isEqualTo(DEFAULT_OWNER);
        assertThat(testMflapartment.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testMflapartment.getLoanNumber()).isEqualTo(DEFAULT_LOAN_NUMBER);
    }

    @Test
    @Transactional
    public void createMflapartmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mflapartmentRepository.findAll().size();

        // Create the Mflapartment with an existing ID
        mflapartment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMflapartmentMockMvc.perform(post("/api/mflapartments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mflapartment)))
            .andExpect(status().isBadRequest());

        // Validate the Mflapartment in the database
        List<Mflapartment> mflapartmentList = mflapartmentRepository.findAll();
        assertThat(mflapartmentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = mflapartmentRepository.findAll().size();
        // set the field null
        mflapartment.setName(null);

        // Create the Mflapartment, which fails.

        restMflapartmentMockMvc.perform(post("/api/mflapartments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mflapartment)))
            .andExpect(status().isBadRequest());

        List<Mflapartment> mflapartmentList = mflapartmentRepository.findAll();
        assertThat(mflapartmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOwnerIsRequired() throws Exception {
        int databaseSizeBeforeTest = mflapartmentRepository.findAll().size();
        // set the field null
        mflapartment.setOwner(null);

        // Create the Mflapartment, which fails.

        restMflapartmentMockMvc.perform(post("/api/mflapartments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mflapartment)))
            .andExpect(status().isBadRequest());

        List<Mflapartment> mflapartmentList = mflapartmentRepository.findAll();
        assertThat(mflapartmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = mflapartmentRepository.findAll().size();
        // set the field null
        mflapartment.setAddress(null);

        // Create the Mflapartment, which fails.

        restMflapartmentMockMvc.perform(post("/api/mflapartments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mflapartment)))
            .andExpect(status().isBadRequest());

        List<Mflapartment> mflapartmentList = mflapartmentRepository.findAll();
        assertThat(mflapartmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMflapartments() throws Exception {
        // Initialize the database
        mflapartmentRepository.saveAndFlush(mflapartment);

        // Get all the mflapartmentList
        restMflapartmentMockMvc.perform(get("/api/mflapartments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mflapartment.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].owner").value(hasItem(DEFAULT_OWNER.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].loanNumber").value(hasItem(DEFAULT_LOAN_NUMBER.toString())));
    }
    

    @Test
    @Transactional
    public void getMflapartment() throws Exception {
        // Initialize the database
        mflapartmentRepository.saveAndFlush(mflapartment);

        // Get the mflapartment
        restMflapartmentMockMvc.perform(get("/api/mflapartments/{id}", mflapartment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mflapartment.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.owner").value(DEFAULT_OWNER.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.loanNumber").value(DEFAULT_LOAN_NUMBER.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingMflapartment() throws Exception {
        // Get the mflapartment
        restMflapartmentMockMvc.perform(get("/api/mflapartments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMflapartment() throws Exception {
        // Initialize the database
        mflapartmentRepository.saveAndFlush(mflapartment);

        int databaseSizeBeforeUpdate = mflapartmentRepository.findAll().size();

        // Update the mflapartment
        Mflapartment updatedMflapartment = mflapartmentRepository.findById(mflapartment.getId()).get();
        // Disconnect from session so that the updates on updatedMflapartment are not directly saved in db
        em.detach(updatedMflapartment);
        updatedMflapartment
            .name(UPDATED_NAME)
            .owner(UPDATED_OWNER)
            .address(UPDATED_ADDRESS)
            .loanNumber(UPDATED_LOAN_NUMBER);

        restMflapartmentMockMvc.perform(put("/api/mflapartments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMflapartment)))
            .andExpect(status().isOk());

        // Validate the Mflapartment in the database
        List<Mflapartment> mflapartmentList = mflapartmentRepository.findAll();
        assertThat(mflapartmentList).hasSize(databaseSizeBeforeUpdate);
        Mflapartment testMflapartment = mflapartmentList.get(mflapartmentList.size() - 1);
        assertThat(testMflapartment.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMflapartment.getOwner()).isEqualTo(UPDATED_OWNER);
        assertThat(testMflapartment.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testMflapartment.getLoanNumber()).isEqualTo(UPDATED_LOAN_NUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingMflapartment() throws Exception {
        int databaseSizeBeforeUpdate = mflapartmentRepository.findAll().size();

        // Create the Mflapartment

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMflapartmentMockMvc.perform(put("/api/mflapartments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mflapartment)))
            .andExpect(status().isBadRequest());

        // Validate the Mflapartment in the database
        List<Mflapartment> mflapartmentList = mflapartmentRepository.findAll();
        assertThat(mflapartmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMflapartment() throws Exception {
        // Initialize the database
        mflapartmentRepository.saveAndFlush(mflapartment);

        int databaseSizeBeforeDelete = mflapartmentRepository.findAll().size();

        // Get the mflapartment
        restMflapartmentMockMvc.perform(delete("/api/mflapartments/{id}", mflapartment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Mflapartment> mflapartmentList = mflapartmentRepository.findAll();
        assertThat(mflapartmentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Mflapartment.class);
        Mflapartment mflapartment1 = new Mflapartment();
        mflapartment1.setId(1L);
        Mflapartment mflapartment2 = new Mflapartment();
        mflapartment2.setId(mflapartment1.getId());
        assertThat(mflapartment1).isEqualTo(mflapartment2);
        mflapartment2.setId(2L);
        assertThat(mflapartment1).isNotEqualTo(mflapartment2);
        mflapartment1.setId(null);
        assertThat(mflapartment1).isNotEqualTo(mflapartment2);
    }
}

package com.blockbrain.propmgmnt.web.rest;

import com.blockbrain.propmgmnt.PropMgmntApp;

import com.blockbrain.propmgmnt.domain.Deposit;
import com.blockbrain.propmgmnt.repository.DepositRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.blockbrain.propmgmnt.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.blockbrain.propmgmnt.domain.enumeration.DepositType;
import com.blockbrain.propmgmnt.domain.enumeration.DepositStatus;
/**
 * Test class for the DepositResource REST controller.
 *
 * @see DepositResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PropMgmntApp.class)
public class DepositResourceIntTest {

    private static final LocalDate DEFAULT_INITIATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_INITIATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_APPROVED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_APPROVED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final DepositType DEFAULT_TYPE = DepositType.Loan;
    private static final DepositType UPDATED_TYPE = DepositType.Insurance;

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;

    private static final DepositStatus DEFAULT_DEP_STATUS = DepositStatus.Initiated;
    private static final DepositStatus UPDATED_DEP_STATUS = DepositStatus.Approved;

    @Autowired
    private DepositRepository depositRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDepositMockMvc;

    private Deposit deposit;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DepositResource depositResource = new DepositResource(depositRepository);
        this.restDepositMockMvc = MockMvcBuilders.standaloneSetup(depositResource)
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
    public static Deposit createEntity(EntityManager em) {
        Deposit deposit = new Deposit()
            .initiatedDate(DEFAULT_INITIATED_DATE)
            .approvedDate(DEFAULT_APPROVED_DATE)
            .type(DEFAULT_TYPE)
            .amount(DEFAULT_AMOUNT)
            .depStatus(DEFAULT_DEP_STATUS);
        return deposit;
    }

    @Before
    public void initTest() {
        deposit = createEntity(em);
    }

    @Test
    @Transactional
    public void createDeposit() throws Exception {
        int databaseSizeBeforeCreate = depositRepository.findAll().size();

        // Create the Deposit
        restDepositMockMvc.perform(post("/api/deposits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deposit)))
            .andExpect(status().isCreated());

        // Validate the Deposit in the database
        List<Deposit> depositList = depositRepository.findAll();
        assertThat(depositList).hasSize(databaseSizeBeforeCreate + 1);
        Deposit testDeposit = depositList.get(depositList.size() - 1);
        assertThat(testDeposit.getInitiatedDate()).isEqualTo(DEFAULT_INITIATED_DATE);
        assertThat(testDeposit.getApprovedDate()).isEqualTo(DEFAULT_APPROVED_DATE);
        assertThat(testDeposit.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testDeposit.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testDeposit.getDepStatus()).isEqualTo(DEFAULT_DEP_STATUS);
    }

    @Test
    @Transactional
    public void createDepositWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = depositRepository.findAll().size();

        // Create the Deposit with an existing ID
        deposit.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDepositMockMvc.perform(post("/api/deposits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deposit)))
            .andExpect(status().isBadRequest());

        // Validate the Deposit in the database
        List<Deposit> depositList = depositRepository.findAll();
        assertThat(depositList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkInitiatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = depositRepository.findAll().size();
        // set the field null
        deposit.setInitiatedDate(null);

        // Create the Deposit, which fails.

        restDepositMockMvc.perform(post("/api/deposits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deposit)))
            .andExpect(status().isBadRequest());

        List<Deposit> depositList = depositRepository.findAll();
        assertThat(depositList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkApprovedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = depositRepository.findAll().size();
        // set the field null
        deposit.setApprovedDate(null);

        // Create the Deposit, which fails.

        restDepositMockMvc.perform(post("/api/deposits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deposit)))
            .andExpect(status().isBadRequest());

        List<Deposit> depositList = depositRepository.findAll();
        assertThat(depositList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = depositRepository.findAll().size();
        // set the field null
        deposit.setType(null);

        // Create the Deposit, which fails.

        restDepositMockMvc.perform(post("/api/deposits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deposit)))
            .andExpect(status().isBadRequest());

        List<Deposit> depositList = depositRepository.findAll();
        assertThat(depositList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = depositRepository.findAll().size();
        // set the field null
        deposit.setAmount(null);

        // Create the Deposit, which fails.

        restDepositMockMvc.perform(post("/api/deposits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deposit)))
            .andExpect(status().isBadRequest());

        List<Deposit> depositList = depositRepository.findAll();
        assertThat(depositList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDepStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = depositRepository.findAll().size();
        // set the field null
        deposit.setDepStatus(null);

        // Create the Deposit, which fails.

        restDepositMockMvc.perform(post("/api/deposits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deposit)))
            .andExpect(status().isBadRequest());

        List<Deposit> depositList = depositRepository.findAll();
        assertThat(depositList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDeposits() throws Exception {
        // Initialize the database
        depositRepository.saveAndFlush(deposit);

        // Get all the depositList
        restDepositMockMvc.perform(get("/api/deposits?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deposit.getId().intValue())))
            .andExpect(jsonPath("$.[*].initiatedDate").value(hasItem(DEFAULT_INITIATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].approvedDate").value(hasItem(DEFAULT_APPROVED_DATE.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].depStatus").value(hasItem(DEFAULT_DEP_STATUS.toString())));
    }
    

    @Test
    @Transactional
    public void getDeposit() throws Exception {
        // Initialize the database
        depositRepository.saveAndFlush(deposit);

        // Get the deposit
        restDepositMockMvc.perform(get("/api/deposits/{id}", deposit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(deposit.getId().intValue()))
            .andExpect(jsonPath("$.initiatedDate").value(DEFAULT_INITIATED_DATE.toString()))
            .andExpect(jsonPath("$.approvedDate").value(DEFAULT_APPROVED_DATE.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.depStatus").value(DEFAULT_DEP_STATUS.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingDeposit() throws Exception {
        // Get the deposit
        restDepositMockMvc.perform(get("/api/deposits/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDeposit() throws Exception {
        // Initialize the database
        depositRepository.saveAndFlush(deposit);

        int databaseSizeBeforeUpdate = depositRepository.findAll().size();

        // Update the deposit
        Deposit updatedDeposit = depositRepository.findById(deposit.getId()).get();
        // Disconnect from session so that the updates on updatedDeposit are not directly saved in db
        em.detach(updatedDeposit);
        updatedDeposit
            .initiatedDate(UPDATED_INITIATED_DATE)
            .approvedDate(UPDATED_APPROVED_DATE)
            .type(UPDATED_TYPE)
            .amount(UPDATED_AMOUNT)
            .depStatus(UPDATED_DEP_STATUS);

        restDepositMockMvc.perform(put("/api/deposits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDeposit)))
            .andExpect(status().isOk());

        // Validate the Deposit in the database
        List<Deposit> depositList = depositRepository.findAll();
        assertThat(depositList).hasSize(databaseSizeBeforeUpdate);
        Deposit testDeposit = depositList.get(depositList.size() - 1);
        assertThat(testDeposit.getInitiatedDate()).isEqualTo(UPDATED_INITIATED_DATE);
        assertThat(testDeposit.getApprovedDate()).isEqualTo(UPDATED_APPROVED_DATE);
        assertThat(testDeposit.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testDeposit.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testDeposit.getDepStatus()).isEqualTo(UPDATED_DEP_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingDeposit() throws Exception {
        int databaseSizeBeforeUpdate = depositRepository.findAll().size();

        // Create the Deposit

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDepositMockMvc.perform(put("/api/deposits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deposit)))
            .andExpect(status().isBadRequest());

        // Validate the Deposit in the database
        List<Deposit> depositList = depositRepository.findAll();
        assertThat(depositList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDeposit() throws Exception {
        // Initialize the database
        depositRepository.saveAndFlush(deposit);

        int databaseSizeBeforeDelete = depositRepository.findAll().size();

        // Get the deposit
        restDepositMockMvc.perform(delete("/api/deposits/{id}", deposit.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Deposit> depositList = depositRepository.findAll();
        assertThat(depositList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Deposit.class);
        Deposit deposit1 = new Deposit();
        deposit1.setId(1L);
        Deposit deposit2 = new Deposit();
        deposit2.setId(deposit1.getId());
        assertThat(deposit1).isEqualTo(deposit2);
        deposit2.setId(2L);
        assertThat(deposit1).isNotEqualTo(deposit2);
        deposit1.setId(null);
        assertThat(deposit1).isNotEqualTo(deposit2);
    }
}

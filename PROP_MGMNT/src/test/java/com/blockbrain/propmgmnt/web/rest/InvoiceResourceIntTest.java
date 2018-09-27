package com.blockbrain.propmgmnt.web.rest;

import static com.blockbrain.propmgmnt.web.rest.TestUtil.createFormattingConversionService;
import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import javax.persistence.EntityManager;

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

import com.blockbrain.propmgmnt.PropMgmntApp;
import com.blockbrain.propmgmnt.domain.Invoice;
import com.blockbrain.propmgmnt.domain.User;
import com.blockbrain.propmgmnt.domain.enumeration.ExpenseType;
import com.blockbrain.propmgmnt.domain.enumeration.IncomeType;
import com.blockbrain.propmgmnt.domain.enumeration.InvoiceStatus;
import com.blockbrain.propmgmnt.domain.enumeration.InvoiceType;
import com.blockbrain.propmgmnt.repository.InvoiceRepository;
import com.blockbrain.propmgmnt.repository.UserRepository;
import com.blockbrain.propmgmnt.web.rest.errors.ExceptionTranslator;
/**
 * Test class for the InvoiceResource REST controller.
 *
 * @see InvoiceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PropMgmntApp.class)
public class InvoiceResourceIntTest {

    private static final LocalDate DEFAULT_GENERATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_GENERATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_PAID_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PAID_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final InvoiceType DEFAULT_TYPE = InvoiceType.Income;
    private static final InvoiceType UPDATED_TYPE = InvoiceType.Expense;

    private static final IncomeType DEFAULT_INCOME_CATEGORY = IncomeType.Rent;
    private static final IncomeType UPDATED_INCOME_CATEGORY = IncomeType.Laundry;

    private static final ExpenseType DEFAULT_EXPENSE_CATEGORY = ExpenseType.Taxes;
    private static final ExpenseType UPDATED_EXPENSE_CATEGORY = ExpenseType.Insurance;

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;

    private static final InvoiceStatus DEFAULT_INV_STATUS = InvoiceStatus.Generated;
    private static final InvoiceStatus UPDATED_INV_STATUS = InvoiceStatus.Paid;

    @Autowired
    private InvoiceRepository invoiceRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restInvoiceMockMvc;

    private Invoice invoice;
    
    @Autowired
    private UserRepository userRepository;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InvoiceResource invoiceResource = new InvoiceResource(invoiceRepository);
        this.restInvoiceMockMvc = MockMvcBuilders.standaloneSetup(invoiceResource)
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
    public static Invoice createEntity(EntityManager em) {
        Invoice invoice = new Invoice()
            .generatedDate(DEFAULT_GENERATED_DATE)
            .paidDate(DEFAULT_PAID_DATE)
            .type(DEFAULT_TYPE)
            .incomeCategory(DEFAULT_INCOME_CATEGORY)
            .expenseCategory(DEFAULT_EXPENSE_CATEGORY)
            .amount(DEFAULT_AMOUNT)
            .invStatus(DEFAULT_INV_STATUS);
        return invoice;
    }

    @Before
    public void initTest() {
        invoice = createEntity(em);
    }

    @Test
    @Transactional
    public void createInvoice() throws Exception {
        int databaseSizeBeforeCreate = invoiceRepository.findAll().size();

        // Create the Invoice
        restInvoiceMockMvc.perform(post("/api/invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoice)))
            .andExpect(status().isCreated());

        // Validate the Invoice in the database
        List<Invoice> invoiceList = invoiceRepository.findAll();
        assertThat(invoiceList).hasSize(databaseSizeBeforeCreate + 1);
        Invoice testInvoice = invoiceList.get(invoiceList.size() - 1);
        assertThat(testInvoice.getGeneratedDate()).isEqualTo(DEFAULT_GENERATED_DATE);
        assertThat(testInvoice.getPaidDate()).isEqualTo(DEFAULT_PAID_DATE);
        assertThat(testInvoice.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testInvoice.getIncomeCategory()).isEqualTo(DEFAULT_INCOME_CATEGORY);
        assertThat(testInvoice.getExpenseCategory()).isEqualTo(DEFAULT_EXPENSE_CATEGORY);
        assertThat(testInvoice.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testInvoice.getInvStatus()).isEqualTo(DEFAULT_INV_STATUS);
    }

    @Test
    @Transactional
    public void createInvoiceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = invoiceRepository.findAll().size();

        // Create the Invoice with an existing ID
        invoice.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInvoiceMockMvc.perform(post("/api/invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoice)))
            .andExpect(status().isBadRequest());

        // Validate the Invoice in the database
        List<Invoice> invoiceList = invoiceRepository.findAll();
        assertThat(invoiceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkGeneratedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = invoiceRepository.findAll().size();
        // set the field null
        invoice.setGeneratedDate(null);

        // Create the Invoice, which fails.

        restInvoiceMockMvc.perform(post("/api/invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoice)))
            .andExpect(status().isBadRequest());

        List<Invoice> invoiceList = invoiceRepository.findAll();
        assertThat(invoiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = invoiceRepository.findAll().size();
        // set the field null
        invoice.setType(null);

        // Create the Invoice, which fails.

        restInvoiceMockMvc.perform(post("/api/invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoice)))
            .andExpect(status().isBadRequest());

        List<Invoice> invoiceList = invoiceRepository.findAll();
        assertThat(invoiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = invoiceRepository.findAll().size();
        // set the field null
        invoice.setAmount(null);

        // Create the Invoice, which fails.

        restInvoiceMockMvc.perform(post("/api/invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoice)))
            .andExpect(status().isBadRequest());

        List<Invoice> invoiceList = invoiceRepository.findAll();
        assertThat(invoiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInvStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = invoiceRepository.findAll().size();
        // set the field null
        invoice.setInvStatus(null);

        // Create the Invoice, which fails.

        restInvoiceMockMvc.perform(post("/api/invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoice)))
            .andExpect(status().isBadRequest());

        List<Invoice> invoiceList = invoiceRepository.findAll();
        assertThat(invoiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInvoices() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList
        restInvoiceMockMvc.perform(get("/api/invoices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invoice.getId().intValue())))
            .andExpect(jsonPath("$.[*].generatedDate").value(hasItem(DEFAULT_GENERATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].paidDate").value(hasItem(DEFAULT_PAID_DATE.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].incomeCategory").value(hasItem(DEFAULT_INCOME_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].expenseCategory").value(hasItem(DEFAULT_EXPENSE_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].invStatus").value(hasItem(DEFAULT_INV_STATUS.toString())));
    }
    
    
    @Test
    @Transactional
    public void getAllInvoicesThisMonth() throws Exception {
        // Initialize the database
        LocalDate today = LocalDate.now();
        LocalDate thisMonth = today.with(firstDayOfMonth());
        LocalDate lastMonth = thisMonth.minusMonths(1);
        createIncomeByMonth(thisMonth, lastMonth);
       // invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList
        restInvoiceMockMvc.perform(get("/api/invoices-this-month?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
            /*.andExpect(jsonPath("$.[*].id").value(hasItem(invoice.getId().intValue())))
            .andExpect(jsonPath("$.[*].generatedDate").value(hasItem(DEFAULT_GENERATED_DATE.toString())))*/
/*            .andExpect(jsonPath("$.[*].paidDate").value(hasItem(DEFAULT_PAID_DATE.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].incomeCategory").value(hasItem(DEFAULT_INCOME_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].expenseCategory").value(hasItem(DEFAULT_EXPENSE_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].invStatus").value(hasItem(DEFAULT_INV_STATUS.toString())));*/
    }
    
    
    private void createIncomeByMonth(LocalDate thisMonth, LocalDate lastMonth) {
    	User user = userRepository.findOneByLogin("user").get();
    	// Create Income in two separate months
    	
        Invoice invoice = new Invoice()
                .generatedDate(thisMonth.plusDays(2))
                .paidDate(DEFAULT_PAID_DATE)
                .type(DEFAULT_TYPE)
                .incomeCategory(DEFAULT_INCOME_CATEGORY)
                .expenseCategory(DEFAULT_EXPENSE_CATEGORY)
                .amount(DEFAULT_AMOUNT)
                .invStatus(DEFAULT_INV_STATUS);
        invoiceRepository.saveAndFlush(invoice);
        
        invoice = new Invoice()
                .generatedDate(thisMonth.plusDays(3))
                .paidDate(DEFAULT_PAID_DATE)
                .type(DEFAULT_TYPE)
                .incomeCategory(DEFAULT_INCOME_CATEGORY)
                .expenseCategory(DEFAULT_EXPENSE_CATEGORY)
                .amount(DEFAULT_AMOUNT)
                .invStatus(DEFAULT_INV_STATUS);
        invoiceRepository.saveAndFlush(invoice);
        
        invoice = new Invoice()
                .generatedDate(lastMonth.plusDays(3))
                .paidDate(DEFAULT_PAID_DATE)
                .type(DEFAULT_TYPE)
                .incomeCategory(DEFAULT_INCOME_CATEGORY)
                .expenseCategory(DEFAULT_EXPENSE_CATEGORY)
                .amount(DEFAULT_AMOUNT)
                .invStatus(DEFAULT_INV_STATUS);
        invoiceRepository.saveAndFlush(invoice);
        
        invoice = new Invoice()
                .generatedDate(lastMonth.plusDays(4))
                .paidDate(DEFAULT_PAID_DATE)
                .type(DEFAULT_TYPE)
                .incomeCategory(DEFAULT_INCOME_CATEGORY)
                .expenseCategory(DEFAULT_EXPENSE_CATEGORY)
                .amount(DEFAULT_AMOUNT)
                .invStatus(DEFAULT_INV_STATUS);
        invoiceRepository.saveAndFlush(invoice);


    	}
    

    @Test
    @Transactional
    public void getInvoice() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get the invoice
        restInvoiceMockMvc.perform(get("/api/invoices/{id}", invoice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(invoice.getId().intValue()))
            .andExpect(jsonPath("$.generatedDate").value(DEFAULT_GENERATED_DATE.toString()))
            .andExpect(jsonPath("$.paidDate").value(DEFAULT_PAID_DATE.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.incomeCategory").value(DEFAULT_INCOME_CATEGORY.toString()))
            .andExpect(jsonPath("$.expenseCategory").value(DEFAULT_EXPENSE_CATEGORY.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.invStatus").value(DEFAULT_INV_STATUS.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingInvoice() throws Exception {
        // Get the invoice
        restInvoiceMockMvc.perform(get("/api/invoices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInvoice() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        int databaseSizeBeforeUpdate = invoiceRepository.findAll().size();

        // Update the invoice
        Invoice updatedInvoice = invoiceRepository.findById(invoice.getId()).get();
        // Disconnect from session so that the updates on updatedInvoice are not directly saved in db
        em.detach(updatedInvoice);
        updatedInvoice
            .generatedDate(UPDATED_GENERATED_DATE)
            .paidDate(UPDATED_PAID_DATE)
            .type(UPDATED_TYPE)
            .incomeCategory(UPDATED_INCOME_CATEGORY)
            .expenseCategory(UPDATED_EXPENSE_CATEGORY)
            .amount(UPDATED_AMOUNT)
            .invStatus(UPDATED_INV_STATUS);

        restInvoiceMockMvc.perform(put("/api/invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedInvoice)))
            .andExpect(status().isOk());

        // Validate the Invoice in the database
        List<Invoice> invoiceList = invoiceRepository.findAll();
        assertThat(invoiceList).hasSize(databaseSizeBeforeUpdate);
        Invoice testInvoice = invoiceList.get(invoiceList.size() - 1);
        assertThat(testInvoice.getGeneratedDate()).isEqualTo(UPDATED_GENERATED_DATE);
        assertThat(testInvoice.getPaidDate()).isEqualTo(UPDATED_PAID_DATE);
        assertThat(testInvoice.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testInvoice.getIncomeCategory()).isEqualTo(UPDATED_INCOME_CATEGORY);
        assertThat(testInvoice.getExpenseCategory()).isEqualTo(UPDATED_EXPENSE_CATEGORY);
        assertThat(testInvoice.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testInvoice.getInvStatus()).isEqualTo(UPDATED_INV_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingInvoice() throws Exception {
        int databaseSizeBeforeUpdate = invoiceRepository.findAll().size();

        // Create the Invoice

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restInvoiceMockMvc.perform(put("/api/invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoice)))
            .andExpect(status().isBadRequest());

        // Validate the Invoice in the database
        List<Invoice> invoiceList = invoiceRepository.findAll();
        assertThat(invoiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInvoice() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        int databaseSizeBeforeDelete = invoiceRepository.findAll().size();

        // Get the invoice
        restInvoiceMockMvc.perform(delete("/api/invoices/{id}", invoice.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Invoice> invoiceList = invoiceRepository.findAll();
        assertThat(invoiceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Invoice.class);
        Invoice invoice1 = new Invoice();
        invoice1.setId(1L);
        Invoice invoice2 = new Invoice();
        invoice2.setId(invoice1.getId());
        assertThat(invoice1).isEqualTo(invoice2);
        invoice2.setId(2L);
        assertThat(invoice1).isNotEqualTo(invoice2);
        invoice1.setId(null);
        assertThat(invoice1).isNotEqualTo(invoice2);
    }
}

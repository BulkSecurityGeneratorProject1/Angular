package com.blockbrain.propmgmnt.web.rest;

import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;
import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;
import static java.time.temporal.TemporalAdjusters.firstDayOfYear;
import static java.time.temporal.TemporalAdjusters.lastDayOfYear;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blockbrain.propmgmnt.domain.Invoice;
import com.blockbrain.propmgmnt.domain.enumeration.InvoiceType;
import com.blockbrain.propmgmnt.repository.InvoiceRepository;
import com.blockbrain.propmgmnt.web.rest.errors.BadRequestAlertException;
import com.blockbrain.propmgmnt.web.rest.util.HeaderUtil;
import com.blockbrain.propmgmnt.web.rest.vm.IncomeExpenseStatistics;
import com.blockbrain.propmgmnt.web.rest.vm.MonthWiseIncomeExpenseStatistics;
import com.codahale.metrics.annotation.Timed;

import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing Invoice.
 */
@RestController
@RequestMapping("/api")
public class InvoiceResource {

    private final Logger log = LoggerFactory.getLogger(InvoiceResource.class);

    private static final String ENTITY_NAME = "invoice";

    private final InvoiceRepository invoiceRepository;

    public InvoiceResource(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    /**
     * POST  /invoices : Create a new invoice.
     *
     * @param invoice the invoice to create
     * @return the ResponseEntity with status 201 (Created) and with body the new invoice, or with status 400 (Bad Request) if the invoice has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/invoices")
    @Timed
    public ResponseEntity<Invoice> createInvoice(@Valid @RequestBody Invoice invoice) throws URISyntaxException {
        log.debug("REST request to save Invoice : {}", invoice);
        if (invoice.getId() != null) {
            throw new BadRequestAlertException("A new invoice cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Invoice result = invoiceRepository.save(invoice);
        return ResponseEntity.created(new URI("/api/invoices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /invoices : Updates an existing invoice.
     *
     * @param invoice the invoice to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated invoice,
     * or with status 400 (Bad Request) if the invoice is not valid,
     * or with status 500 (Internal Server Error) if the invoice couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/invoices")
    @Timed
    public ResponseEntity<Invoice> updateInvoice(@Valid @RequestBody Invoice invoice) throws URISyntaxException {
        log.debug("REST request to update Invoice : {}", invoice);
        if (invoice.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Invoice result = invoiceRepository.save(invoice);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, invoice.getId().toString()))
            .body(result);
    }

    /**
     * GET  /invoices : get all the invoices.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of invoices in body
     */
    @GetMapping("/invoices")
    @Timed
    public List<Invoice> getAllInvoices() {
        log.debug("REST request to get all Invoices");
        return invoiceRepository.findAll();
    }
    
    /**
     * GET  /invoices : get all the invoices for this month.
     
     * @return the ResponseEntity with status 200 (OK) and the list of invoices in body
     */
    
    @GetMapping("/invoices-this-month")
    @Timed
    public IncomeExpenseStatistics getAllInvoicesThisMonth() {
        log.debug("REST request to get all Invoices");
        LocalDate today = LocalDate.now();
        Month currentMonth = today.getMonth();
        
        int currentYear = today.getYear();
        LocalDate firstDayOfMonth = today.with(firstDayOfMonth());
        LocalDate lastDayOfMonth = today.with(lastDayOfMonth());
        
        LocalDate firstDay = today.with(firstDayOfYear()); // 2015-01-01          
        LocalDate lastDay = today.with(lastDayOfYear()); // 2015-12-31
        
        IncomeExpenseStatistics incomeExpenseStatistics = new IncomeExpenseStatistics(currentMonth,currentYear);
        
        List<MonthWiseIncomeExpenseStatistics> monthwiseIncomeList = invoiceRepository.getMonthlyScheduleAdherenceBySection(firstDay, lastDay,"Income");
        System.out.println("monthwiseIncomeList size is" + monthwiseIncomeList.size());
        
        for(MonthWiseIncomeExpenseStatistics stats:monthwiseIncomeList)
        {
        	Month day = Month.of(stats.getMonth());
        	stats.setMonthText(day.getDisplayName(TextStyle.SHORT,Locale.ENGLISH));
        }
        
        incomeExpenseStatistics.setMonthWiseIncomeStatistics(monthwiseIncomeList);
        
        List<MonthWiseIncomeExpenseStatistics> monthwiseExpenseList = invoiceRepository.getMonthlyScheduleAdherenceBySection(firstDay, lastDay,"Expense");
        System.out.println("monthwiseExpenseList size is" + monthwiseExpenseList.size());
        
        for(MonthWiseIncomeExpenseStatistics stats:monthwiseExpenseList)
        {
        	Month day = Month.of(stats.getMonth());
        	stats.setMonthText(day.getDisplayName(TextStyle.SHORT,Locale.ENGLISH));
        }
        
        incomeExpenseStatistics.setMonthWiseExpenseStatistics(monthwiseExpenseList);
        
        List<Invoice> readings =
        		invoiceRepository.findByGeneratedDateAfterAndGeneratedDateBefore(firstDayOfMonth,
        				lastDayOfMonth);
       
        for(Invoice inv:readings)
        {
        	if(inv.getType().equals(InvoiceType.Income))
        		incomeExpenseStatistics.setTotalMonthlyIncome(incomeExpenseStatistics.getTotalMonthlyIncome()+inv.getAmount());
        	else
        		incomeExpenseStatistics.setTotalMonthlyExpense(incomeExpenseStatistics.getTotalMonthlyExpense()+inv.getAmount());
        }
        
        
        List<Invoice> yearlyreadings =
        		invoiceRepository.findByGeneratedDateAfterAndGeneratedDateBefore(firstDay,
        				lastDay);
        for(Invoice inv:yearlyreadings)
        {
        	if(inv.getType().equals(InvoiceType.Income))
        		incomeExpenseStatistics.setTotalYearlyIncome(incomeExpenseStatistics.getTotalYearlyIncome()+inv.getAmount());
        	else
        		incomeExpenseStatistics.setTotalYearlyExpense(incomeExpenseStatistics.getTotalYearlyExpense()+inv.getAmount());
        }
        return incomeExpenseStatistics;
        
    }
    /**
     * GET  /invoices/:id : get the "id" invoice.
     *
     * @param id the id of the invoice to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the invoice, or with status 404 (Not Found)
     */
    @GetMapping("/invoices/{id}")
    @Timed
    public ResponseEntity<Invoice> getInvoice(@PathVariable Long id) {
        log.debug("REST request to get Invoice : {}", id);
        Optional<Invoice> invoice = invoiceRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(invoice);
    }

    /**
     * DELETE  /invoices/:id : delete the "id" invoice.
     *
     * @param id the id of the invoice to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/invoices/{id}")
    @Timed
    public ResponseEntity<Void> deleteInvoice(@PathVariable Long id) {
        log.debug("REST request to delete Invoice : {}", id);

        invoiceRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

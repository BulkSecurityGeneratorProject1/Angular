package com.blockbrain.propmgmnt.repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.blockbrain.propmgmnt.domain.Invoice;
import com.blockbrain.propmgmnt.web.rest.vm.MonthWiseIncomeExpenseStatistics;


/**
 * Spring Data  repository for the Invoice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

	List<Invoice> findByGeneratedDateAfterAndGeneratedDateBefore(LocalDate firstDayOfMonth, LocalDate lastDayOfMonth);
	
	@Query(value = "SELECT "
	        + " new com.blockbrain.propmgmnt.web.rest.vm.MonthWiseIncomeExpenseStatistics(Month(invoice.generatedDate), SUM(invoice.amount))"
	        + " FROM Invoice invoice" 
	        + " WHERE invoice.generatedDate BETWEEN :startDate AND :endDate"
	        + " AND JHI_TYPE = :type"
	        + " GROUP BY Month(invoice.generatedDate) ORDER BY Month(invoice.generatedDate) DESC")
	public List <MonthWiseIncomeExpenseStatistics> getMonthlyScheduleAdherenceBySection(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate,
			@Param("type") String type);
	

}

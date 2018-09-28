package com.blockbrain.propmgmnt.web.rest.vm;

import lombok.AllArgsConstructor;
import lombok.Data;


import java.time.Month;
import java.time.Year;

@Data
@AllArgsConstructor
public class MonthWiseIncomeExpenseStatistics {

	public MonthWiseIncomeExpenseStatistics() {
		
	}
    public MonthWiseIncomeExpenseStatistics(int month, Double actualTotal) {
		super();
		this.month = month;
		this.actualTotal = actualTotal;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public Double getActualTotal() {
		return actualTotal;
	}
	public void setActualTotal(Double actualTotal) {
		this.actualTotal = actualTotal;
	}

	public String getMonthText() {
		return monthText;
	}
	public void setMonthText(String monthText) {
		this.monthText = monthText;
	}
	int month;
	String monthText;
    Double actualTotal;
    
}
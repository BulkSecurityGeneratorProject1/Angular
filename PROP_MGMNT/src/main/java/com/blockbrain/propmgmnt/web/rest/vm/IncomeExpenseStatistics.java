package com.blockbrain.propmgmnt.web.rest.vm;



import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class IncomeExpenseStatistics {
	
private Month month;
private int year;
private Double totalMonthlyIncome;
private Double totalMonthlyExpense;
private List<MonthWiseIncomeExpenseStatistics> monthWiseIncomeStatistics;

private List<MonthWiseIncomeExpenseStatistics> monthWiseExpenseStatistics;

private Double totalYearlyIncome;
private Double totalYearlyExpense;

public IncomeExpenseStatistics(Month month, int year) {
		this.month = month;
		this.year = year;
		this.totalMonthlyIncome = 0.0;
		this.totalMonthlyExpense = 0.0;
		this.totalYearlyIncome = 0.0;
		this.totalYearlyExpense = 0.0;
		this.monthWiseIncomeStatistics = new ArrayList<MonthWiseIncomeExpenseStatistics>();
		this.monthWiseExpenseStatistics = new ArrayList<MonthWiseIncomeExpenseStatistics>();
}

public List<MonthWiseIncomeExpenseStatistics> getMonthWiseIncomeStatistics() {
	return monthWiseIncomeStatistics;
}

public void setMonthWiseIncomeStatistics(List<MonthWiseIncomeExpenseStatistics> monthWiseIncomeStatistics) {
	this.monthWiseIncomeStatistics = monthWiseIncomeStatistics;
}

public List<MonthWiseIncomeExpenseStatistics> getMonthWiseExpenseStatistics() {
	return monthWiseExpenseStatistics;
}

public void setMonthWiseExpenseStatistics(List<MonthWiseIncomeExpenseStatistics> monthWiseExpenseStatistics) {
	this.monthWiseExpenseStatistics = monthWiseExpenseStatistics;
}

public Double getTotalYearlyIncome() {
	return totalYearlyIncome;
}
public void setTotalYearlyIncome(Double totalYearlyIncome) {
	this.totalYearlyIncome = totalYearlyIncome;
}
public Double getTotalYearlyExpense() {
	return totalYearlyExpense;
}

public void setTotalYearlyExpense(Double totalYearlyExpense) {
	this.totalYearlyExpense = totalYearlyExpense;
}
public Double getTotalMonthlyIncome() {
	return totalMonthlyIncome;
}
public void setTotalMonthlyIncome(Double totalMonthlyIncome) {
	this.totalMonthlyIncome = totalMonthlyIncome;
}

public Double getTotalMonthlyExpense() {
	return totalMonthlyExpense;
}
public void setTotalMonthlyExpense(Double totalMonthlyExpense) {
	this.totalMonthlyExpense = totalMonthlyExpense;
}

public Month getmonth() {
	return month;
}

public void setmonth(Month month) {
	this.month = month;
}
@Override
public String toString() {
	return "incomeThismonth{" +
	"income=" + totalMonthlyIncome +
	", month=" + month +
	'}';
	}
}
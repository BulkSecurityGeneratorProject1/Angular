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
private List<MonthWiseIncomeExpenseStatistics> monthWiseIncomeExpenseStatistics;

private Double totalYearlyIncome;


private Double totalYearlyExpense;

public IncomeExpenseStatistics(Month month, int year) {
		this.month = month;
		this.year = year;
		this.totalMonthlyIncome = 0.0;
		this.totalMonthlyExpense = 0.0;
		this.totalYearlyIncome = 0.0;
		this.totalYearlyExpense = 0.0;
		this.monthWiseIncomeExpenseStatistics = new ArrayList<MonthWiseIncomeExpenseStatistics>();
}
public List<MonthWiseIncomeExpenseStatistics> getMonthWiseIncomeExpenseStatistics() {
	return monthWiseIncomeExpenseStatistics;
}

public void setMonthWiseIncomeExpenseStatistics(
		List<MonthWiseIncomeExpenseStatistics> monthWiseIncomeExpenseStatistics) {
	this.monthWiseIncomeExpenseStatistics = monthWiseIncomeExpenseStatistics;
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
package com.blockbrain.propmgmnt.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.blockbrain.propmgmnt.domain.enumeration.InvoiceType;

import com.blockbrain.propmgmnt.domain.enumeration.IncomeType;

import com.blockbrain.propmgmnt.domain.enumeration.ExpenseType;

import com.blockbrain.propmgmnt.domain.enumeration.InvoiceStatus;

/**
 * A Invoice.
 */
@Entity
@Table(name = "invoice")
public class Invoice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "generated_date", nullable = false)
    private LocalDate generatedDate;

    @Column(name = "paid_date")
    private LocalDate paidDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type", nullable = false)
    private InvoiceType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "income_category")
    private IncomeType incomeCategory;

    @Enumerated(EnumType.STRING)
    @Column(name = "expense_category")
    private ExpenseType expenseCategory;

    @NotNull
    @Column(name = "amount", nullable = false)
    private Double amount;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "inv_status", nullable = false)
    private InvoiceStatus invStatus;

    @ManyToOne
    @JsonIgnoreProperties("invoices")
    private Apartment apartment;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getGeneratedDate() {
        return generatedDate;
    }

    public Invoice generatedDate(LocalDate generatedDate) {
        this.generatedDate = generatedDate;
        return this;
    }

    public void setGeneratedDate(LocalDate generatedDate) {
        this.generatedDate = generatedDate;
    }

    public LocalDate getPaidDate() {
        return paidDate;
    }

    public Invoice paidDate(LocalDate paidDate) {
        this.paidDate = paidDate;
        return this;
    }

    public void setPaidDate(LocalDate paidDate) {
        this.paidDate = paidDate;
    }

    public InvoiceType getType() {
        return type;
    }

    public Invoice type(InvoiceType type) {
        this.type = type;
        return this;
    }

    public void setType(InvoiceType type) {
        this.type = type;
    }

    public IncomeType getIncomeCategory() {
        return incomeCategory;
    }

    public Invoice incomeCategory(IncomeType incomeCategory) {
        this.incomeCategory = incomeCategory;
        return this;
    }

    public void setIncomeCategory(IncomeType incomeCategory) {
        this.incomeCategory = incomeCategory;
    }

    public ExpenseType getExpenseCategory() {
        return expenseCategory;
    }

    public Invoice expenseCategory(ExpenseType expenseCategory) {
        this.expenseCategory = expenseCategory;
        return this;
    }

    public void setExpenseCategory(ExpenseType expenseCategory) {
        this.expenseCategory = expenseCategory;
    }

    public Double getAmount() {
        return amount;
    }

    public Invoice amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public InvoiceStatus getInvStatus() {
        return invStatus;
    }

    public Invoice invStatus(InvoiceStatus invStatus) {
        this.invStatus = invStatus;
        return this;
    }

    public void setInvStatus(InvoiceStatus invStatus) {
        this.invStatus = invStatus;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public Invoice apartment(Apartment apartment) {
        this.apartment = apartment;
        return this;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Invoice invoice = (Invoice) o;
        if (invoice.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), invoice.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Invoice{" +
            "id=" + getId() +
            ", generatedDate='" + getGeneratedDate() + "'" +
            ", paidDate='" + getPaidDate() + "'" +
            ", type='" + getType() + "'" +
            ", incomeCategory='" + getIncomeCategory() + "'" +
            ", expenseCategory='" + getExpenseCategory() + "'" +
            ", amount=" + getAmount() +
            ", invStatus='" + getInvStatus() + "'" +
            "}";
    }
}

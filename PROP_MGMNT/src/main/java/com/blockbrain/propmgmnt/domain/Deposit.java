package com.blockbrain.propmgmnt.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.blockbrain.propmgmnt.domain.enumeration.DepositType;

import com.blockbrain.propmgmnt.domain.enumeration.DepositStatus;

/**
 * A Deposit.
 */
@Entity
@Table(name = "deposit")
public class Deposit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "initiated_date", nullable = false)
    private LocalDate initiatedDate;

    @NotNull
    @Column(name = "approved_date", nullable = false)
    private LocalDate approvedDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type", nullable = false)
    private DepositType type;

    @NotNull
    @Column(name = "amount", nullable = false)
    private Double amount;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "dep_status", nullable = false)
    private DepositStatus depStatus;

    @ManyToOne
    @JsonIgnoreProperties("deposits")
    private Apartment apartment;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getInitiatedDate() {
        return initiatedDate;
    }

    public Deposit initiatedDate(LocalDate initiatedDate) {
        this.initiatedDate = initiatedDate;
        return this;
    }

    public void setInitiatedDate(LocalDate initiatedDate) {
        this.initiatedDate = initiatedDate;
    }

    public LocalDate getApprovedDate() {
        return approvedDate;
    }

    public Deposit approvedDate(LocalDate approvedDate) {
        this.approvedDate = approvedDate;
        return this;
    }

    public void setApprovedDate(LocalDate approvedDate) {
        this.approvedDate = approvedDate;
    }

    public DepositType getType() {
        return type;
    }

    public Deposit type(DepositType type) {
        this.type = type;
        return this;
    }

    public void setType(DepositType type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public Deposit amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public DepositStatus getDepStatus() {
        return depStatus;
    }

    public Deposit depStatus(DepositStatus depStatus) {
        this.depStatus = depStatus;
        return this;
    }

    public void setDepStatus(DepositStatus depStatus) {
        this.depStatus = depStatus;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public Deposit apartment(Apartment apartment) {
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
        Deposit deposit = (Deposit) o;
        if (deposit.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), deposit.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Deposit{" +
            "id=" + getId() +
            ", initiatedDate='" + getInitiatedDate() + "'" +
            ", approvedDate='" + getApprovedDate() + "'" +
            ", type='" + getType() + "'" +
            ", amount=" + getAmount() +
            ", depStatus='" + getDepStatus() + "'" +
            "}";
    }
}

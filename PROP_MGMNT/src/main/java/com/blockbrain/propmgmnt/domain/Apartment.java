package com.blockbrain.propmgmnt.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.blockbrain.propmgmnt.domain.enumeration.UnitType;

import com.blockbrain.propmgmnt.domain.enumeration.ApartmentStatus;

/**
 * A Apartment.
 */
@Entity
@Table(name = "apartment")
public class Apartment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "unit", nullable = false)
    private String unit;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private UnitType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "unit_status")
    private ApartmentStatus unitStatus;

    @OneToMany(mappedBy = "apartment")
    private Set<Invoice> invoices = new HashSet<>();

    @OneToMany(mappedBy = "apartment")
    private Set<Deposit> deposits = new HashSet<>();

    @OneToOne(mappedBy = "apartment")
    @JsonIgnore
    private Agreement agreement;

    @ManyToOne
    @JsonIgnoreProperties("apartments")
    private Mflapartment mflapartment;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUnit() {
        return unit;
    }

    public Apartment unit(String unit) {
        this.unit = unit;
        return this;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public UnitType getType() {
        return type;
    }

    public Apartment type(UnitType type) {
        this.type = type;
        return this;
    }

    public void setType(UnitType type) {
        this.type = type;
    }

    public ApartmentStatus getUnitStatus() {
        return unitStatus;
    }

    public Apartment unitStatus(ApartmentStatus unitStatus) {
        this.unitStatus = unitStatus;
        return this;
    }

    public void setUnitStatus(ApartmentStatus unitStatus) {
        this.unitStatus = unitStatus;
    }

    public Set<Invoice> getInvoices() {
        return invoices;
    }

    public Apartment invoices(Set<Invoice> invoices) {
        this.invoices = invoices;
        return this;
    }

    public Apartment addInvoice(Invoice invoice) {
        this.invoices.add(invoice);
        invoice.setApartment(this);
        return this;
    }

    public Apartment removeInvoice(Invoice invoice) {
        this.invoices.remove(invoice);
        invoice.setApartment(null);
        return this;
    }

    public void setInvoices(Set<Invoice> invoices) {
        this.invoices = invoices;
    }

    public Set<Deposit> getDeposits() {
        return deposits;
    }

    public Apartment deposits(Set<Deposit> deposits) {
        this.deposits = deposits;
        return this;
    }

    public Apartment addDeposit(Deposit deposit) {
        this.deposits.add(deposit);
        deposit.setApartment(this);
        return this;
    }

    public Apartment removeDeposit(Deposit deposit) {
        this.deposits.remove(deposit);
        deposit.setApartment(null);
        return this;
    }

    public void setDeposits(Set<Deposit> deposits) {
        this.deposits = deposits;
    }

    public Agreement getAgreement() {
        return agreement;
    }

    public Apartment agreement(Agreement agreement) {
        this.agreement = agreement;
        return this;
    }

    public void setAgreement(Agreement agreement) {
        this.agreement = agreement;
    }

    public Mflapartment getMflapartment() {
        return mflapartment;
    }

    public Apartment mflapartment(Mflapartment mflapartment) {
        this.mflapartment = mflapartment;
        return this;
    }

    public void setMflapartment(Mflapartment mflapartment) {
        this.mflapartment = mflapartment;
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
        Apartment apartment = (Apartment) o;
        if (apartment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), apartment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Apartment{" +
            "id=" + getId() +
            ", unit='" + getUnit() + "'" +
            ", type='" + getType() + "'" +
            ", unitStatus='" + getUnitStatus() + "'" +
            "}";
    }
}

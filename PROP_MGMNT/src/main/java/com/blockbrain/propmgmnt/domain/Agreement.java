package com.blockbrain.propmgmnt.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Agreement.
 */
@Entity
@Table(name = "agreement")
public class Agreement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "agreement_start_date", nullable = false)
    private LocalDate agreementStartDate;

    @NotNull
    @Column(name = "agreement_end_date", nullable = false)
    private LocalDate agreementEndDate;

    @Lob
    @Column(name = "agreement_details")
    private byte[] agreementDetails;

    @Column(name = "agreement_details_content_type")
    private String agreementDetailsContentType;

    @OneToOne
    @JoinColumn(unique = true)
    private Apartment apartment;

    @OneToOne
    @JoinColumn(unique = true)
    private Tenant tenant;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getAgreementStartDate() {
        return agreementStartDate;
    }

    public Agreement agreementStartDate(LocalDate agreementStartDate) {
        this.agreementStartDate = agreementStartDate;
        return this;
    }

    public void setAgreementStartDate(LocalDate agreementStartDate) {
        this.agreementStartDate = agreementStartDate;
    }

    public LocalDate getAgreementEndDate() {
        return agreementEndDate;
    }

    public Agreement agreementEndDate(LocalDate agreementEndDate) {
        this.agreementEndDate = agreementEndDate;
        return this;
    }

    public void setAgreementEndDate(LocalDate agreementEndDate) {
        this.agreementEndDate = agreementEndDate;
    }

    public byte[] getAgreementDetails() {
        return agreementDetails;
    }

    public Agreement agreementDetails(byte[] agreementDetails) {
        this.agreementDetails = agreementDetails;
        return this;
    }

    public void setAgreementDetails(byte[] agreementDetails) {
        this.agreementDetails = agreementDetails;
    }

    public String getAgreementDetailsContentType() {
        return agreementDetailsContentType;
    }

    public Agreement agreementDetailsContentType(String agreementDetailsContentType) {
        this.agreementDetailsContentType = agreementDetailsContentType;
        return this;
    }

    public void setAgreementDetailsContentType(String agreementDetailsContentType) {
        this.agreementDetailsContentType = agreementDetailsContentType;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public Agreement apartment(Apartment apartment) {
        this.apartment = apartment;
        return this;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public Agreement tenant(Tenant tenant) {
        this.tenant = tenant;
        return this;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
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
        Agreement agreement = (Agreement) o;
        if (agreement.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), agreement.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Agreement{" +
            "id=" + getId() +
            ", agreementStartDate='" + getAgreementStartDate() + "'" +
            ", agreementEndDate='" + getAgreementEndDate() + "'" +
            ", agreementDetails='" + getAgreementDetails() + "'" +
            ", agreementDetailsContentType='" + getAgreementDetailsContentType() + "'" +
            "}";
    }
}

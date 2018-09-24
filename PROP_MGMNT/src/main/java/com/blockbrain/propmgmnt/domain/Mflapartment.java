package com.blockbrain.propmgmnt.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Mflapartment.
 */
@Entity
@Table(name = "mflapartment")
public class Mflapartment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "owner", nullable = false)
    private String owner;

    @NotNull
    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "loan_number")
    private String loanNumber;

    @OneToMany(mappedBy = "mflapartment")
    private Set<Apartment> apartments = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Mflapartment name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public Mflapartment owner(String owner) {
        this.owner = owner;
        return this;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getAddress() {
        return address;
    }

    public Mflapartment address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLoanNumber() {
        return loanNumber;
    }

    public Mflapartment loanNumber(String loanNumber) {
        this.loanNumber = loanNumber;
        return this;
    }

    public void setLoanNumber(String loanNumber) {
        this.loanNumber = loanNumber;
    }

    public Set<Apartment> getApartments() {
        return apartments;
    }

    public Mflapartment apartments(Set<Apartment> apartments) {
        this.apartments = apartments;
        return this;
    }

    public Mflapartment addApartment(Apartment apartment) {
        this.apartments.add(apartment);
        apartment.setMflapartment(this);
        return this;
    }

    public Mflapartment removeApartment(Apartment apartment) {
        this.apartments.remove(apartment);
        apartment.setMflapartment(null);
        return this;
    }

    public void setApartments(Set<Apartment> apartments) {
        this.apartments = apartments;
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
        Mflapartment mflapartment = (Mflapartment) o;
        if (mflapartment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mflapartment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Mflapartment{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", owner='" + getOwner() + "'" +
            ", address='" + getAddress() + "'" +
            ", loanNumber='" + getLoanNumber() + "'" +
            "}";
    }
}

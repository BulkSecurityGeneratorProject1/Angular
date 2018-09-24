package com.blockbrain.propmgmnt.repository;

import com.blockbrain.propmgmnt.domain.Apartment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Apartment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApartmentRepository extends JpaRepository<Apartment, Long> {

}

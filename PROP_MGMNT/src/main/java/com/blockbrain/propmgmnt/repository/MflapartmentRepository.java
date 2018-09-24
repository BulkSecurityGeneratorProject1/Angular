package com.blockbrain.propmgmnt.repository;

import com.blockbrain.propmgmnt.domain.Mflapartment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Mflapartment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MflapartmentRepository extends JpaRepository<Mflapartment, Long> {

}

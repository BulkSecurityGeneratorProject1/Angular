package com.blockbrain.propmgmnt.repository;

import com.blockbrain.propmgmnt.domain.Deposit;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Deposit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DepositRepository extends JpaRepository<Deposit, Long> {

}

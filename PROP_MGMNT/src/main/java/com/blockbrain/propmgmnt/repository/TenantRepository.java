package com.blockbrain.propmgmnt.repository;

import com.blockbrain.propmgmnt.domain.Tenant;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Tenant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long> {

    @Query("select tenant from Tenant tenant where tenant.user.login = ?#{principal.username}")
    List<Tenant> findByUserIsCurrentUser();

}

package com.blockbrain.propmgmnt.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.blockbrain.propmgmnt.domain.Agreement;
import com.blockbrain.propmgmnt.repository.AgreementRepository;
import com.blockbrain.propmgmnt.web.rest.errors.BadRequestAlertException;
import com.blockbrain.propmgmnt.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Agreement.
 */
@RestController
@RequestMapping("/api")
public class AgreementResource {

    private final Logger log = LoggerFactory.getLogger(AgreementResource.class);

    private static final String ENTITY_NAME = "agreement";

    private final AgreementRepository agreementRepository;

    public AgreementResource(AgreementRepository agreementRepository) {
        this.agreementRepository = agreementRepository;
    }

    /**
     * POST  /agreements : Create a new agreement.
     *
     * @param agreement the agreement to create
     * @return the ResponseEntity with status 201 (Created) and with body the new agreement, or with status 400 (Bad Request) if the agreement has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/agreements")
    @Timed
    public ResponseEntity<Agreement> createAgreement(@Valid @RequestBody Agreement agreement) throws URISyntaxException {
        log.debug("REST request to save Agreement : {}", agreement);
        if (agreement.getId() != null) {
            throw new BadRequestAlertException("A new agreement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Agreement result = agreementRepository.save(agreement);
        return ResponseEntity.created(new URI("/api/agreements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /agreements : Updates an existing agreement.
     *
     * @param agreement the agreement to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated agreement,
     * or with status 400 (Bad Request) if the agreement is not valid,
     * or with status 500 (Internal Server Error) if the agreement couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/agreements")
    @Timed
    public ResponseEntity<Agreement> updateAgreement(@Valid @RequestBody Agreement agreement) throws URISyntaxException {
        log.debug("REST request to update Agreement : {}", agreement);
        if (agreement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Agreement result = agreementRepository.save(agreement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, agreement.getId().toString()))
            .body(result);
    }

    /**
     * GET  /agreements : get all the agreements.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of agreements in body
     */
    @GetMapping("/agreements")
    @Timed
    public List<Agreement> getAllAgreements() {
        log.debug("REST request to get all Agreements");
        return agreementRepository.findAll();
    }

    /**
     * GET  /agreements/:id : get the "id" agreement.
     *
     * @param id the id of the agreement to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the agreement, or with status 404 (Not Found)
     */
    @GetMapping("/agreements/{id}")
    @Timed
    public ResponseEntity<Agreement> getAgreement(@PathVariable Long id) {
        log.debug("REST request to get Agreement : {}", id);
        Optional<Agreement> agreement = agreementRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(agreement);
    }

    /**
     * DELETE  /agreements/:id : delete the "id" agreement.
     *
     * @param id the id of the agreement to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/agreements/{id}")
    @Timed
    public ResponseEntity<Void> deleteAgreement(@PathVariable Long id) {
        log.debug("REST request to delete Agreement : {}", id);

        agreementRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

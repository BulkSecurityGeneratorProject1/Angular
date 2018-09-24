package com.blockbrain.propmgmnt.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.blockbrain.propmgmnt.domain.Mflapartment;
import com.blockbrain.propmgmnt.repository.MflapartmentRepository;
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
 * REST controller for managing Mflapartment.
 */
@RestController
@RequestMapping("/api")
public class MflapartmentResource {

    private final Logger log = LoggerFactory.getLogger(MflapartmentResource.class);

    private static final String ENTITY_NAME = "mflapartment";

    private final MflapartmentRepository mflapartmentRepository;

    public MflapartmentResource(MflapartmentRepository mflapartmentRepository) {
        this.mflapartmentRepository = mflapartmentRepository;
    }

    /**
     * POST  /mflapartments : Create a new mflapartment.
     *
     * @param mflapartment the mflapartment to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mflapartment, or with status 400 (Bad Request) if the mflapartment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mflapartments")
    @Timed
    public ResponseEntity<Mflapartment> createMflapartment(@Valid @RequestBody Mflapartment mflapartment) throws URISyntaxException {
        log.debug("REST request to save Mflapartment : {}", mflapartment);
        if (mflapartment.getId() != null) {
            throw new BadRequestAlertException("A new mflapartment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Mflapartment result = mflapartmentRepository.save(mflapartment);
        return ResponseEntity.created(new URI("/api/mflapartments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mflapartments : Updates an existing mflapartment.
     *
     * @param mflapartment the mflapartment to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mflapartment,
     * or with status 400 (Bad Request) if the mflapartment is not valid,
     * or with status 500 (Internal Server Error) if the mflapartment couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mflapartments")
    @Timed
    public ResponseEntity<Mflapartment> updateMflapartment(@Valid @RequestBody Mflapartment mflapartment) throws URISyntaxException {
        log.debug("REST request to update Mflapartment : {}", mflapartment);
        if (mflapartment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Mflapartment result = mflapartmentRepository.save(mflapartment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mflapartment.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mflapartments : get all the mflapartments.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of mflapartments in body
     */
    @GetMapping("/mflapartments")
    @Timed
    public List<Mflapartment> getAllMflapartments() {
        log.debug("REST request to get all Mflapartments");
        return mflapartmentRepository.findAll();
    }

    /**
     * GET  /mflapartments/:id : get the "id" mflapartment.
     *
     * @param id the id of the mflapartment to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mflapartment, or with status 404 (Not Found)
     */
    @GetMapping("/mflapartments/{id}")
    @Timed
    public ResponseEntity<Mflapartment> getMflapartment(@PathVariable Long id) {
        log.debug("REST request to get Mflapartment : {}", id);
        Optional<Mflapartment> mflapartment = mflapartmentRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(mflapartment);
    }

    /**
     * DELETE  /mflapartments/:id : delete the "id" mflapartment.
     *
     * @param id the id of the mflapartment to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mflapartments/{id}")
    @Timed
    public ResponseEntity<Void> deleteMflapartment(@PathVariable Long id) {
        log.debug("REST request to delete Mflapartment : {}", id);

        mflapartmentRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

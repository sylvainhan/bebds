package com.acoss.webae.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.acoss.webae.domain.Taux;

import com.acoss.webae.repository.TauxRepository;
import com.acoss.webae.web.rest.errors.BadRequestAlertException;
import com.acoss.webae.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Taux.
 */
@RestController
@RequestMapping("/api")
public class TauxResource {

    private final Logger log = LoggerFactory.getLogger(TauxResource.class);

    private static final String ENTITY_NAME = "taux";

    private final TauxRepository tauxRepository;

    public TauxResource(TauxRepository tauxRepository) {
        this.tauxRepository = tauxRepository;
    }

    /**
     * POST  /tauxes : Create a new taux.
     *
     * @param taux the taux to create
     * @return the ResponseEntity with status 201 (Created) and with body the new taux, or with status 400 (Bad Request) if the taux has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tauxes")
    @Timed
    public ResponseEntity<Taux> createTaux(@RequestBody Taux taux) throws URISyntaxException {
        log.debug("REST request to save Taux : {}", taux);
        if (taux.getId() != null) {
            throw new BadRequestAlertException("A new taux cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Taux result = tauxRepository.save(taux);
        return ResponseEntity.created(new URI("/api/tauxes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tauxes : Updates an existing taux.
     *
     * @param taux the taux to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated taux,
     * or with status 400 (Bad Request) if the taux is not valid,
     * or with status 500 (Internal Server Error) if the taux couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tauxes")
    @Timed
    public ResponseEntity<Taux> updateTaux(@RequestBody Taux taux) throws URISyntaxException {
        log.debug("REST request to update Taux : {}", taux);
        if (taux.getId() == null) {
            return createTaux(taux);
        }
        Taux result = tauxRepository.save(taux);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, taux.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tauxes : get all the tauxes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tauxes in body
     */
    @GetMapping("/tauxes")
    @Timed
    public List<Taux> getAllTauxes() {
        log.debug("REST request to get all Tauxes");
        return tauxRepository.findAll();
        }

    /**
     * GET  /tauxes/:id : get the "id" taux.
     *
     * @param id the id of the taux to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the taux, or with status 404 (Not Found)
     */
    @GetMapping("/tauxes/{id}")
    @Timed
    public ResponseEntity<Taux> getTaux(@PathVariable Long id) {
        log.debug("REST request to get Taux : {}", id);
        Taux taux = tauxRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(taux));
    }

    /**
     * DELETE  /tauxes/:id : delete the "id" taux.
     *
     * @param id the id of the taux to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tauxes/{id}")
    @Timed
    public ResponseEntity<Void> deleteTaux(@PathVariable Long id) {
        log.debug("REST request to delete Taux : {}", id);
        tauxRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

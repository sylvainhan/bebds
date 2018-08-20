package com.acoss.webae.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.acoss.webae.service.DemandeService;
import com.acoss.webae.web.rest.errors.BadRequestAlertException;
import com.acoss.webae.web.rest.util.HeaderUtil;
import com.acoss.webae.service.dto.DemandeDTO;
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
 * REST controller for managing Demande.
 */
@RestController
@RequestMapping("/api/v1")
public class DemandeResource {

    private final Logger log = LoggerFactory.getLogger(DemandeResource.class);

    private static final String ENTITY_NAME = "demande";

    private final DemandeService demandeService;

    public DemandeResource(DemandeService demandeService) {
        this.demandeService = demandeService;
    }

    /**
     * POST  /demandes : Create a new demande.
     *
     * @param demandeDTO the demandeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new demandeDTO, or with status 400 (Bad Request) if the demande has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/demandes")
    @Timed
    public ResponseEntity<DemandeDTO> createDemande(@RequestBody DemandeDTO demandeDTO) throws URISyntaxException {
        log.debug("REST request to save Demande : {}", demandeDTO);
        if (demandeDTO.getId() != null) {
            throw new BadRequestAlertException("A new demande cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DemandeDTO result = demandeService.save(demandeDTO);
        return ResponseEntity.created(new URI("/api/demandes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /demandes : Updates an existing demande.
     *
     * @param demandeDTO the demandeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated demandeDTO,
     * or with status 400 (Bad Request) if the demandeDTO is not valid,
     * or with status 500 (Internal Server Error) if the demandeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/demandes")
    @Timed
    public ResponseEntity<DemandeDTO> updateDemande(@RequestBody DemandeDTO demandeDTO) throws URISyntaxException {
        log.debug("REST request to update Demande : {}", demandeDTO);
        if (demandeDTO.getId() == null) {
            return createDemande(demandeDTO);
        }
        DemandeDTO result = demandeService.save(demandeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, demandeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /demandes : get all the demandes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of demandes in body
     */
    @GetMapping("/demandes")
    @Timed
    public List<DemandeDTO> getAllDemandes() {
        log.debug("REST request to get all Demandes");
        return demandeService.findAll();
        }

    /**
     * GET  /demandes/:id : get the "id" demande.
     *
     * @param id the id of the demandeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the demandeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/demandes/{id}")
    @Timed
    public ResponseEntity<DemandeDTO> getDemande(@PathVariable Long id) {
        log.debug("REST request to get Demande : {}", id);
        DemandeDTO demandeDTO = demandeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(demandeDTO));
    }

    /**
     * DELETE  /demandes/:id : delete the "id" demande.
     *
     * @param id the id of the demandeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/demandes/{id}")
    @Timed
    public ResponseEntity<Void> deleteDemande(@PathVariable Long id) {
        log.debug("REST request to delete Demande : {}", id);
        demandeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

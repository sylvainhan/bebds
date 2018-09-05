package com.acoss.webae.web.rest;

import com.acoss.webae.service.util.MockUtil;
import com.codahale.metrics.annotation.Timed;
import com.acoss.webae.service.LastConnectionService;
import com.acoss.webae.web.rest.errors.BadRequestAlertException;
import com.acoss.webae.web.rest.util.HeaderUtil;
import com.acoss.webae.service.dto.LastConnectionDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing LastConnection.
 */
@RestController
@RequestMapping("/api/v1")
public class LastConnectionResource {

    private final Logger log = LoggerFactory.getLogger(LastConnectionResource.class);

    private static final String ENTITY_NAME = "lastConnection";

    private final LastConnectionService lastConnectionService;

    public LastConnectionResource(LastConnectionService lastConnectionService) {
        this.lastConnectionService = lastConnectionService;
    }

    /**
     * POST  /last-connections : Create a new lastConnection.
     *
     * @param lastConnectionDTO the lastConnectionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new lastConnectionDTO, or with status 400 (Bad Request) if the lastConnection has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/last-connections")
    @Timed
    public ResponseEntity<LastConnectionDTO> createLastConnection(@RequestBody LastConnectionDTO lastConnectionDTO) throws URISyntaxException {
        log.debug("REST request to save LastConnection : {}", lastConnectionDTO);
        if (lastConnectionDTO.getId() != null) {
            throw new BadRequestAlertException("A new lastConnection cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LastConnectionDTO result = lastConnectionService.save(lastConnectionDTO);
        return ResponseEntity.created(new URI("/api/last-connections/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /last-connections : Updates an existing lastConnection.
     *
     * @param lastConnectionDTO the lastConnectionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated lastConnectionDTO,
     * or with status 400 (Bad Request) if the lastConnectionDTO is not valid,
     * or with status 500 (Internal Server Error) if the lastConnectionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect

    @PutMapping("/last-connections")
    @Timed
    public ResponseEntity<LastConnectionDTO> updateLastConnection(@RequestBody LastConnectionDTO lastConnectionDTO) throws URISyntaxException {
        log.debug("REST request to update LastConnection : {}", lastConnectionDTO);
        if (lastConnectionDTO.getId() == null) {
            return createLastConnection(lastConnectionDTO);
        }
        LastConnectionDTO result = lastConnectionService.save(lastConnectionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, lastConnectionDTO.getId().toString()))
            .body(result);
    }
     */

    /**
     * GET  /last-connections : get all the lastConnections.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of lastConnections in body
     */
    @GetMapping("/last-connections/all")
    @Timed
    public List<LastConnectionDTO> getAllLastConnections() {
        log.debug("REST request to get all LastConnections");
        return lastConnectionService.findAll();
        }

    /**
     * GET  /last-connections/:id : get the "id" lastConnection.
     *
     * @param id the id of the lastConnectionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the lastConnectionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/last-connections/{id}")
    @Timed
    public ResponseEntity<LastConnectionDTO> getLastConnection(@PathVariable Long id) {
        log.debug("REST request to get LastConnection : {}", id);
        LastConnectionDTO lastConnectionDTO = lastConnectionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(lastConnectionDTO));
    }

    /**
     * DELETE  /last-connections/:id : delete the "id" lastConnection.
     *
     * @param id the id of the lastConnectionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/last-connections/{id}")
    @Timed
    public ResponseEntity<Void> deleteLastConnection(@PathVariable Long id) {
        log.debug("REST request to delete LastConnection : {}", id);
        lastConnectionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    // --------- custom methods ----------

    /**
     * GET  /last-connections : get Last Connection of User.
     *
     * @return the ResponseEntity with status 200 (OK) and the last connection information in the body
     */
    @GetMapping("/last-connections")
    @Timed
    public ResponseEntity<LastConnectionDTO> getMyLastConnection() {
        log.debug("REST request to get My LastConnection");
        LastConnectionDTO lastConnectionDTO = lastConnectionService.findOneByNumCptExt(MockUtil.NUM_COMPTE_EXT);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(lastConnectionDTO));
    }


/**
 * PUT  /last-connections : Updates an existing lastConnection.
 *
 * @return the ResponseEntity with status 200 (OK) and with body the updated lastConnectionDTO,
 * or with status 400 (Bad Request) if the lastConnectionDTO is not valid,
 * or with status 500 (Internal Server Error) if the lastConnectionDTO couldn't be updated
 * @throws URISyntaxException if the Location URI syntax is incorrect
    @PutMapping("/last-connections")
    @Timed
    */
    public ResponseEntity<LastConnectionDTO> upsertMyLastConnection() throws URISyntaxException {
        log.debug("REST request to update LastConnection ");

        ZonedDateTime zdt = ZonedDateTime.now(ZoneId.of(ZoneId.SHORT_IDS.get("ECT")));

        LastConnectionDTO lcDTO = lastConnectionService.findOneByNumCptExt(MockUtil.NUM_COMPTE_EXT);

        if(lcDTO == null) {
            lcDTO = new LastConnectionDTO();
            lcDTO.setNumCompteExterne(MockUtil.NUM_COMPTE_EXT);
            lcDTO.setLastConnection(zdt);
            lcDTO.setActuelConnection(zdt);
        } else {
            lcDTO.setLastConnection(lcDTO.getActuelConnection());
            lcDTO.setActuelConnection(zdt);
        }

        LastConnectionDTO result = lastConnectionService.saveForCurrentUser(lcDTO);
        return ResponseEntity.created(new URI("/api/last-connections/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
}

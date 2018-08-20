package com.acoss.webae.web.rest;

import com.acoss.webae.domain.enumeration.NotifChannel;
import com.acoss.webae.domain.enumeration.NotifMoment;
import com.acoss.webae.service.dto.UserPreferenceDTO;
import com.codahale.metrics.annotation.Timed;
import com.acoss.webae.service.PreferenceNotifService;
import com.acoss.webae.web.rest.errors.BadRequestAlertException;
import com.acoss.webae.web.rest.util.HeaderUtil;
import com.acoss.webae.service.dto.PreferenceNotifDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing PreferenceNotif.
 */
@RestController
@RequestMapping("/api/v1")
public class PreferenceNotifResource {

    private final Logger log = LoggerFactory.getLogger(PreferenceNotifResource.class);

    private static final String ENTITY_NAME = "preferenceNotif";

    private final PreferenceNotifService preferenceNotifService;

    public PreferenceNotifResource(PreferenceNotifService preferenceNotifService) {
        this.preferenceNotifService = preferenceNotifService;
    }

    /**
     * POST  /preference-notifs : Create a new preferenceNotif.
     *
     * @param preferenceNotifDTO the preferenceNotifDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new preferenceNotifDTO, or with status 400 (Bad Request) if the preferenceNotif has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/preference-notifs")
    @Timed
    public ResponseEntity<PreferenceNotifDTO> createPreferenceNotif(@RequestBody PreferenceNotifDTO preferenceNotifDTO) throws URISyntaxException {
        log.debug("REST request to save PreferenceNotif : {}", preferenceNotifDTO);
        if (preferenceNotifDTO.getId() != null) {
            throw new BadRequestAlertException("A new preferenceNotif cannot already have an ID", ENTITY_NAME, "idexists");
        }
        try {
            PreferenceNotifDTO result = preferenceNotifService.save(preferenceNotifDTO);
            return ResponseEntity.created(new URI("/api/preference-notifs/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
        } catch (DataIntegrityViolationException ex) {
            throw new BadRequestAlertException("the suite of 'type', 'channel' should be unique", ENTITY_NAME, "duplicate_key");
        }
    }

    /**
     * PUT  /preference-notifs : Updates an existing preferenceNotif.
     *
     * @param preferenceNotifDTO the preferenceNotifDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated preferenceNotifDTO,
     * or with status 400 (Bad Request) if the preferenceNotifDTO is not valid,
     * or with status 500 (Internal Server Error) if the preferenceNotifDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/preference-notifs")
    @Timed
    public ResponseEntity<PreferenceNotifDTO> updatePreferenceNotif(@RequestBody PreferenceNotifDTO preferenceNotifDTO) throws URISyntaxException {
        log.debug("REST request to update PreferenceNotif : {}", preferenceNotifDTO);
        if (preferenceNotifDTO.getId() == null) {
            return createPreferenceNotif(preferenceNotifDTO);
        }
        PreferenceNotifDTO result = preferenceNotifService.save(preferenceNotifDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, preferenceNotifDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /preference-notifs : get all the preferenceNotifs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of preferenceNotifs in body
     */
    @GetMapping("/preference-notifs")
    @Timed
    public List<PreferenceNotifDTO> getAllPreferenceNotifs() {
        log.debug("REST request to get all PreferenceNotifs");
        return preferenceNotifService.findAll();
        }

    /**
     * GET  /preference-notifs/:id : get the "id" preferenceNotif.
     *
     * @param id the id of the preferenceNotifDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the preferenceNotifDTO, or with status 404 (Not Found)
     */
    @GetMapping("/preference-notifs/{id}")
    @Timed
    public ResponseEntity<PreferenceNotifDTO> getPreferenceNotif(@PathVariable Long id) {
        log.debug("REST request to get PreferenceNotif : {}", id);
        PreferenceNotifDTO preferenceNotifDTO = preferenceNotifService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(preferenceNotifDTO));
    }

    /**
     * DELETE  /preference-notifs/:id : delete the "id" preferenceNotif.
     *
     * @param id the id of the preferenceNotifDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/preference-notifs/{id}")
    @Timed
    public ResponseEntity<Void> deletePreferenceNotif(@PathVariable Long id) {
        log.debug("REST request to delete PreferenceNotif : {}", id);
        preferenceNotifService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    // ------------------- Custom Methods ---------------------

    /**
     * POST  /preference-notifs : Create a new preferenceNotif.
     *
     * @param preferenceNotifDTO the preferenceNotifDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new preferenceNotifDTO, or with status 400 (Bad Request) if the preferenceNotif has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PatchMapping("/preference-notifs/{id}")
    @Timed
    public ResponseEntity<PreferenceNotifDTO> changePreferenceNotif(@RequestBody PreferenceNotifDTO preferenceNotifDTO, @PathVariable Long id) throws URISyntaxException {
        log.debug("REST request to change PreferenceNotif active status: {}", preferenceNotifDTO);
        if (preferenceNotifDTO.isActive() == null) {
            throw new BadRequestAlertException("This method could active or not the preference", ENTITY_NAME, "active");
        }

        PreferenceNotifDTO preferenceNotif = preferenceNotifService.findOne(id);
        if (preferenceNotif != null) {
            preferenceNotif.setActive(preferenceNotifDTO.isActive());

            PreferenceNotifDTO result = preferenceNotifService.save(preferenceNotif);
            return ResponseEntity.created(new URI("/api/preference-notifs/" + preferenceNotif.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
        } else {
            return ResponseUtil.wrapOrNotFound(Optional.ofNullable(preferenceNotif));
        }
    }

    /**
     * DELETE  /user-and-preferences/:id : delete the "id" userPreference.
     *
     */
    @DeleteMapping("/preference-notifs")
    @Timed
    public ResponseEntity<Void> deletePreferenceNotifByChannelEtType(@RequestParam String channel, @RequestParam String type) {
        log.debug("REST request to delete UserPreference : by channel: {} and type: {}", channel, type);
        preferenceNotifService.deleteByChannelAndType(NotifChannel.valueOf(channel), NotifMoment.valueOf(type));
        return ResponseEntity.noContent().build();
    }
}

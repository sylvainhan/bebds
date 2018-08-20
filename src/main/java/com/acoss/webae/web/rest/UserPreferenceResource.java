package com.acoss.webae.web.rest;

import com.acoss.webae.service.dto.PreferenceNotifDTO;
import com.acoss.webae.service.util.MockUtil;
import com.codahale.metrics.annotation.Timed;
import com.acoss.webae.service.UserPreferenceService;
import com.acoss.webae.web.rest.errors.BadRequestAlertException;
import com.acoss.webae.web.rest.util.HeaderUtil;
import com.acoss.webae.service.dto.UserPreferenceDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing UserPreference.
 */
@RestController
@RequestMapping("/api/v1")
public class UserPreferenceResource {

    private final Logger log = LoggerFactory.getLogger(UserPreferenceResource.class);

    private static final String ENTITY_NAME = "userPreference";

    private final UserPreferenceService userPreferenceService;

    public UserPreferenceResource(UserPreferenceService userPreferenceService) {
        this.userPreferenceService = userPreferenceService;
    }

    /**
     * POST  /user-and-preferences : Create a new userPreference.
     *
     * @param userPreferenceDTO the userPreferenceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userPreferenceDTO, or with status 400 (Bad Request) if the userPreference has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-and-preferences")
    @Timed
    public ResponseEntity<UserPreferenceDTO> createUserPreference(@RequestBody UserPreferenceDTO userPreferenceDTO) throws URISyntaxException {
        log.debug("REST request to save UserPreference : {}", userPreferenceDTO);
        if (userPreferenceDTO.getId() != null) {
            throw new BadRequestAlertException("A new userPreference cannot already have an ID", ENTITY_NAME, "idexists");
        }
        try {
            UserPreferenceDTO result = userPreferenceService.save(userPreferenceDTO);
            return ResponseEntity.created(new URI("/api/user-and-preferences/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
        } catch (DataIntegrityViolationException ex) {
            throw new BadRequestAlertException("Num compte externe should be unique.", ENTITY_NAME, "num_compte Unique");
        }
    }

    /**
     * PUT  /user-and-preferences : Updates an existing userPreference.
     *
     * @param userPreferenceDTO the userPreferenceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userPreferenceDTO,
     * or with status 400 (Bad Request) if the userPreferenceDTO is not valid,
     * or with status 500 (Internal Server Error) if the userPreferenceDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-and-preferences")
    @Timed
    public ResponseEntity<UserPreferenceDTO> updateUserPreference(@RequestBody UserPreferenceDTO userPreferenceDTO) throws URISyntaxException {
        log.debug("REST request to update UserPreference : {}", userPreferenceDTO);
        if (userPreferenceDTO.getId() == null) {
            return createUserPreference(userPreferenceDTO);
        }
        UserPreferenceDTO result = userPreferenceService.save(userPreferenceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userPreferenceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-and-preferences : get all the userPreferences.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of userPreferences in body
     */
    @GetMapping("/user-and-preferences")
    @Timed
    public List<UserPreferenceDTO> getAllUserPreferences() {
        log.debug("REST request to get all UserPreferences");
        return userPreferenceService.findAll();
        }

    /**
     * GET  /user-and-preferences/:id : get the "id" userPreference.
     *
     * @param id the id of the userPreferenceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userPreferenceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/user-and-preferences/{id}")
    @Timed
    public ResponseEntity<UserPreferenceDTO> getUserPreference(@PathVariable Long id) {
        log.debug("REST request to get UserPreference : {}", id);
        UserPreferenceDTO userPreferenceDTO = userPreferenceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userPreferenceDTO));
    }

    /**
     * DELETE  /user-and-preferences/:id : delete the "id" userPreference.
     *
     * @param id the id of the userPreferenceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-and-preferences/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserPreference(@PathVariable Long id) {
        log.debug("REST request to delete UserPreference : {}", id);
        userPreferenceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    // --------- custom methods ----------

    /**
     * GET  /user-and-preferences : get all the userPreferences.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of myPreferences in body
     */
    @GetMapping("/my-preferences")
    @Timed
    public ResponseEntity<UserPreferenceDTO> getMyPreferences() {
        log.debug("REST request to get all MyPreferences");
        UserPreferenceDTO userPreferenceDTO = userPreferenceService.findOneByNumCptExt(MockUtil.NUM_COMPTE_EXT);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userPreferenceDTO));
    }

    /**
     * GET  /user-and-preferences : get all the userPreferences.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of myPreferences in body
     */
    @PutMapping("/my-preferences")
    @Timed
    public ResponseEntity<UserPreferenceDTO> upsertUserPreference(@RequestBody UserPreferenceDTO userPreferenceDTO) throws URISyntaxException {
        log.debug("REST request to update UserPreference : {}", userPreferenceDTO);

        UserPreferenceDTO uapd = userPreferenceService.findOneByNumCptExt(MockUtil.NUM_COMPTE_EXT);

        if(uapd == null) {
            userPreferenceDTO.setId(null);
            userPreferenceDTO.setNumCompteExterne(MockUtil.NUM_COMPTE_EXT);
        } else {
            if (userPreferenceDTO.getPreferenceNotifs() != null)
            for (PreferenceNotifDTO prefNotiDTO: userPreferenceDTO.getPreferenceNotifs()) {
                prefNotiDTO.setUserPreferenceId(uapd.getId());
                prefNotiDTO.setNumCompteExterne(uapd.getNumCompteExterne());
            }
            userPreferenceDTO.setNumCompteExterne(uapd.getNumCompteExterne());
            userPreferenceDTO.setId(uapd.getId());
        }

        UserPreferenceDTO result = userPreferenceService.save(userPreferenceDTO);
        result=userPreferenceService.findOne(result.getId());
        return ResponseEntity.created(new URI("/api/user-and-preferences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
}

package com.acoss.webae.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.acoss.webae.service.UserDeviceService;
import com.acoss.webae.web.rest.errors.BadRequestAlertException;
import com.acoss.webae.web.rest.util.HeaderUtil;
import com.acoss.webae.service.dto.UserDeviceDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing UserDevice.
 */
@RestController
@RequestMapping("/api/v1")
public class UserDeviceResource {

    private final Logger log = LoggerFactory.getLogger(UserDeviceResource.class);

    private static final String ENTITY_NAME = "userDevice";

    private final UserDeviceService userDeviceService;

    public UserDeviceResource(UserDeviceService userDeviceService) {
        this.userDeviceService = userDeviceService;
    }

    /**
     * POST  /user-devices : Create a new userDevice.
     *
     * @param userDeviceDTO the userDeviceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userDeviceDTO, or with status 400 (Bad Request) if the userDevice has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-devices")
    @Timed
    public ResponseEntity<UserDeviceDTO> createUserDevice(@RequestBody UserDeviceDTO userDeviceDTO) throws URISyntaxException {
        log.debug("REST request to save UserDevice : {}", userDeviceDTO);
        if (userDeviceDTO.getId() != null) {
            throw new BadRequestAlertException("A new userDevice cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserDeviceDTO result = userDeviceService.save(userDeviceDTO);
        return ResponseEntity.created(new URI("/api/user-devices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    ///**
    // * PUT  /user-devices : Updates an existing userDevice.
    // *
    // * @param userDeviceDTO the userDeviceDTO to update
    // * @return the ResponseEntity with status 200 (OK) and with body the updated userDeviceDTO,
    // * or with status 400 (Bad Request) if the userDeviceDTO is not valid,
    // * or with status 500 (Internal Server Error) if the userDeviceDTO couldn't be updated
    // * @throws URISyntaxException if the Location URI syntax is incorrect
    // */
    //@PutMapping("/user-devices")
    //@Timed
    //public ResponseEntity<UserDeviceDTO> updateUserDevice(@RequestBody UserDeviceDTO userDeviceDTO) throws URISyntaxException {
    //    log.debug("REST request to update UserDevice : {}", userDeviceDTO);
    //    if (userDeviceDTO.getId() == null) {
    //        return createUserDevice(userDeviceDTO);
    //    }
    //    UserDeviceDTO result = userDeviceService.save(userDeviceDTO);
    //    return ResponseEntity.ok()
    //        .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userDeviceDTO.getId().toString()))
    //        .body(result);
    //}

    /**
     * GET  /user-devices : get all the userDevices.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of userDevices in body
     */
    //@GetMapping("/user-devices")
    //@Timed
    //public List<UserDeviceDTO> getAllUserDevices() {
    //    log.debug("REST request to get all UserDevices");
    //    return userDeviceService.findAll();
    //    }

    /**
     * GET  /user-devices/:id : get the "id" userDevice.
     *
     * @param id the id of the userDeviceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userDeviceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/user-devices/{id}")
    @Timed
    public ResponseEntity<UserDeviceDTO> getUserDevice(@PathVariable Long id) {
        log.debug("REST request to get UserDevice : {}", id);
        UserDeviceDTO userDeviceDTO = userDeviceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userDeviceDTO));
    }

    /**
     * DELETE  /user-devices/:id : delete the "id" userDevice.
     *
     * @param id the id of the userDeviceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-devices/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserDevice(@PathVariable Long id) {
        log.debug("REST request to delete UserDevice : {}", id);
        try {
            userDeviceService.delete(id);
            return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
        } catch (EmptyResultDataAccessException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    // -------------- Custom APIs ----------------

    /**
     *
     * @param numCompteExterne
     * @return
     */
    @GetMapping("/user-devices")
    @Timed
    public List<UserDeviceDTO> getUserDeviceByNumCptExt(@RequestParam(required = false) String numCompteExterne) {
        if (numCompteExterne != null) {
            log.debug("REST request to get UserDevice by numCompteExterne: {}", numCompteExterne);
            List<UserDeviceDTO> lUsDeDTO = userDeviceService.findAllByNumCptExt(numCompteExterne);

            if (lUsDeDTO != null) {
                return lUsDeDTO;
            } else {
                throw new BadRequestAlertException("Can't find user by this Numéro Compte Externe", ENTITY_NAME, "User not Found");
            }
        } else {
            log.debug("REST request to get all UserDevices");
            return userDeviceService.findAll();
        }
    }

    /**
     * PUT  /user-devices : Updates an existing userDevice.
     *
     * @param userDeviceDTO the userDeviceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userDeviceDTO,
     * or with status 400 (Bad Request) if the userDeviceDTO is not valid,
     * or with status 500 (Internal Server Error) if the userDeviceDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-devices")
    @Timed
    public ResponseEntity<UserDeviceDTO> updateOrInsertUserDevice(@Valid @RequestBody UserDeviceDTO userDeviceDTO) throws URISyntaxException {
        log.debug("REST request to update UserDevice : {}", userDeviceDTO);
        UserDeviceDTO result = userDeviceService.upsertUserDevice(userDeviceDTO);

        return ResponseEntity.created(new URI("/api/user-devices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
}

package com.acoss.webae.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.acoss.webae.service.NotificationService;
import com.acoss.webae.web.rest.errors.BadRequestAlertException;
import com.acoss.webae.web.rest.util.HeaderUtil;
import com.acoss.webae.service.dto.NotificationDTO;
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
 * REST controller for managing Notification.
 */
@RestController
@RequestMapping("/api/v1")
public class NotificationResource {

    private final Logger log = LoggerFactory.getLogger(NotificationResource.class);

    private static final String ENTITY_NAME = "notification";

    private final NotificationService notificationService;

    public NotificationResource(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    /**
     * POST  /notifications : Create a new notification.
     *
     * @param notificationDTO the notificationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new notificationDTO, or with status 400 (Bad Request) if the notification has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/notifications")
    @Timed
    public ResponseEntity<NotificationDTO> createNotification(@RequestBody NotificationDTO notificationDTO) throws URISyntaxException {
        log.debug("REST request to save Notification : {}", notificationDTO);
        if (notificationDTO.getId() != null) {
            throw new BadRequestAlertException("A new notification cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NotificationDTO result = notificationService.save(notificationDTO);
        return ResponseEntity.created(new URI("/api/notifications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /notifications : Updates an existing notification.
     *
     * @param notificationDTO the notificationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated notificationDTO,
     * or with status 400 (Bad Request) if the notificationDTO is not valid,
     * or with status 500 (Internal Server Error) if the notificationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/notifications")
    @Timed
    public ResponseEntity<NotificationDTO> updateNotification(@RequestBody NotificationDTO notificationDTO) throws URISyntaxException {
        log.debug("REST request to update Notification : {}", notificationDTO);
        if (notificationDTO.getId() == null) {
            return createNotification(notificationDTO);
        }
        NotificationDTO result = notificationService.save(notificationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, notificationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /notifications : get all the notifications.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of notifications in body
     */
    @GetMapping("/notifications")
    @Timed
    public List<NotificationDTO> getAllNotifications() {
        log.debug("REST request to get all Notifications");
        return notificationService.findAll();
        }

    /**
     * GET  /notifications/:id : get the "id" notification.
     *
     * @param id the id of the notificationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the notificationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/notifications/{id}")
    @Timed
    public ResponseEntity<NotificationDTO> getNotification(@PathVariable Long id) {
        log.debug("REST request to get Notification : {}", id);
        NotificationDTO notificationDTO = notificationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(notificationDTO));
    }

    /**
     * DELETE  /notifications/:id : delete the "id" notification.
     *
     * @param id the id of the notificationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/notifications/{id}")
    @Timed
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        log.debug("REST request to delete Notification : {}", id);
        notificationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

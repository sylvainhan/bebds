package com.acoss.webae.service;

import com.acoss.webae.service.dto.NotificationDTO;
import java.util.List;

/**
 * Service Interface for managing Notification.
 */
public interface NotificationService {

    /**
     * Save a notification.
     *
     * @param notificationDTO the entity to save
     * @return the persisted entity
     */
    NotificationDTO save(NotificationDTO notificationDTO);

    /**
     * Get all the notifications.
     *
     * @return the list of entities
     */
    List<NotificationDTO> findAll();

    /**
     * Get the "id" notification.
     *
     * @param id the id of the entity
     * @return the entity
     */
    NotificationDTO findOne(Long id);

    /**
     * Delete the "id" notification.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

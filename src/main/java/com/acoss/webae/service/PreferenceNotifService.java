package com.acoss.webae.service;

import com.acoss.webae.domain.enumeration.NotifChannel;
import com.acoss.webae.domain.enumeration.NotifMoment;
import com.acoss.webae.service.dto.PreferenceNotifDTO;
import java.util.List;

/**
 * Service Interface for managing PreferenceNotif.
 */
public interface PreferenceNotifService {

    /**
     * Save a preferenceNotif.
     *
     * @param preferenceNotifDTO the entity to save
     * @return the persisted entity
     */
    PreferenceNotifDTO save(PreferenceNotifDTO preferenceNotifDTO);

    /**
     * Get all the preferenceNotifs.
     *
     * @return the list of entities
     */
    List<PreferenceNotifDTO> findAll();

    /**
     * Get the "id" preferenceNotif.
     *
     * @param id the id of the entity
     * @return the entity
     */
    PreferenceNotifDTO findOne(Long id);

    /**
     * Delete the "id" preferenceNotif.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    void deleteByChannelAndType(NotifChannel channel, NotifMoment type);
}

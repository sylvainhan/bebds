package com.acoss.webae.service;

import com.acoss.webae.service.dto.UserPreferenceDTO;
import java.util.List;

/**
 * Service Interface for managing UserPreference.
 */
public interface UserPreferenceService {

    /**
     * Save a userPreference.
     *
     * @param userPreferenceDTO the entity to save
     * @return the persisted entity
     */
    UserPreferenceDTO save(UserPreferenceDTO userPreferenceDTO);

    /**
     * Get all the userPreferences.
     *
     * @return the list of entities
     */
    List<UserPreferenceDTO> findAll();

    /**
     * Get the "id" userPreference.
     *
     * @param id the id of the entity
     * @return the entity
     */
    UserPreferenceDTO findOne(Long id);

    /**
     * Delete the "id" userPreference.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    UserPreferenceDTO findOneByNumCptExt(String num_cpt_ext);
}

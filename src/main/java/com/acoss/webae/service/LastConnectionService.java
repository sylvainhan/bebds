package com.acoss.webae.service;

import com.acoss.webae.service.dto.LastConnectionDTO;
import java.util.List;

/**
 * Service Interface for managing LastConnection.
 */
public interface LastConnectionService {

    /**
     * Save a lastConnection.
     *
     * @param lastConnectionDTO the entity to save
     * @return the persisted entity
     */
    LastConnectionDTO save(LastConnectionDTO lastConnectionDTO);

    /**
     * Get all the lastConnections.
     *
     * @return the list of entities
     */
    List<LastConnectionDTO> findAll();

    /**
     * Get the "id" lastConnection.
     *
     * @param id the id of the entity
     * @return the entity
     */
    LastConnectionDTO findOne(Long id);

    /**
     * Delete the "id" lastConnection.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

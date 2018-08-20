package com.acoss.webae.service;

import com.acoss.webae.service.dto.TauxDTO;
import java.util.List;

/**
 * Service Interface for managing Taux.
 */
public interface TauxService {

    /**
     * Save a taux.
     *
     * @param tauxDTO the entity to save
     * @return the persisted entity
     */
    TauxDTO save(TauxDTO tauxDTO);

    /**
     * Get all the tauxes.
     *
     * @return the list of entities
     */
    List<TauxDTO> findAll();

    /**
     * Get the "id" taux.
     *
     * @param id the id of the entity
     * @return the entity
     */
    TauxDTO findOne(Long id);

    /**
     * Delete the "id" taux.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

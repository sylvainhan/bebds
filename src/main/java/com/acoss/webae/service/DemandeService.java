package com.acoss.webae.service;

import com.acoss.webae.service.dto.DemandeDTO;
import java.util.List;

/**
 * Service Interface for managing Demande.
 */
public interface DemandeService {

    /**
     * Save a demande.
     *
     * @param demandeDTO the entity to save
     * @return the persisted entity
     */
    DemandeDTO save(DemandeDTO demandeDTO);

    /**
     * Get all the demandes.
     *
     * @return the list of entities
     */
    List<DemandeDTO> findAll();

    /**
     * Get the "id" demande.
     *
     * @param id the id of the entity
     * @return the entity
     */
    DemandeDTO findOne(Long id);

    /**
     * Delete the "id" demande.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

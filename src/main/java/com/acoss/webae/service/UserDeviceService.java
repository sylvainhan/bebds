package com.acoss.webae.service;

import com.acoss.webae.service.dto.UserDeviceDTO;
import java.util.List;

/**
 * Service Interface for managing UserDevice.
 */
public interface UserDeviceService {

    /**
     * Save a userDevice.
     *
     * @param userDeviceDTO the entity to save
     * @return the persisted entity
     */
    UserDeviceDTO save(UserDeviceDTO userDeviceDTO);

    /**
     * Get all the userDevices.
     *
     * @return the list of entities
     */
    List<UserDeviceDTO> findAll();

    /**
     * Get the "id" userDevice.
     *
     * @param id the id of the entity
     * @return the entity
     */
    UserDeviceDTO findOne(Long id);

    /**
     * Delete the "id" userDevice.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    List<UserDeviceDTO> findAllByNumCptExt(String numCptExt);

    UserDeviceDTO upsertUserDevice(UserDeviceDTO userDeviceDTO);
}

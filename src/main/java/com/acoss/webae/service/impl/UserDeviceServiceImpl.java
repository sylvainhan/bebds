package com.acoss.webae.service.impl;

import com.acoss.webae.domain.UserPreference;
import com.acoss.webae.domain.enumeration.TypeDevice;
import com.acoss.webae.repository.UserPreferenceRepository;
import com.acoss.webae.service.UserDeviceService;
import com.acoss.webae.domain.UserDevice;
import com.acoss.webae.repository.UserDeviceRepository;
import com.acoss.webae.service.dto.UserDeviceDTO;
import com.acoss.webae.service.mapper.UserDeviceMapper;
import com.acoss.webae.service.util.MockUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing UserDevice.
 */
@Service
@Transactional
public class UserDeviceServiceImpl implements UserDeviceService {

    private final Logger log = LoggerFactory.getLogger(UserDeviceServiceImpl.class);

    private final UserDeviceRepository userDeviceRepository;

    private final UserDeviceMapper userDeviceMapper;

    private final UserPreferenceRepository userPreferenceRepository;

    public UserDeviceServiceImpl(UserDeviceRepository userDeviceRepository, UserDeviceMapper userDeviceMapper, UserPreferenceRepository userPreferenceRepository) {
        this.userDeviceRepository = userDeviceRepository;
        this.userDeviceMapper = userDeviceMapper;
        this.userPreferenceRepository = userPreferenceRepository;
    }

    /**
     * Save a userDevice.
     *
     * @param userDeviceDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public UserDeviceDTO save(UserDeviceDTO userDeviceDTO) {
        log.debug("Request to save UserDevice : {}", userDeviceDTO);
        UserDevice userDevice = userDeviceMapper.toEntity(userDeviceDTO);
        userDevice = userDeviceRepository.save(userDevice);
        return userDeviceMapper.toDto(userDevice);
    }

    /**
     * Get all the userDevices.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<UserDeviceDTO> findAll() {
        log.debug("Request to get all UserDevices");
        return userDeviceRepository.findAll().stream()
            .map(userDeviceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one userDevice by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public UserDeviceDTO findOne(Long id) {
        log.debug("Request to get UserDevice : {}", id);
        UserDevice userDevice = userDeviceRepository.findOne(id);
        return userDeviceMapper.toDto(userDevice);
    }

    /**
     * Delete the userDevice by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserDevice : {}", id);
        userDeviceRepository.delete(id);
    }

    @Override
    public List<UserDeviceDTO> findAllByNumCptExt(String numCptExt) {
        log.debug("Request to get UserDevice by Numero Compte Externe : {}", numCptExt);
        UserPreference userPreference = userPreferenceRepository.findOneByNumCompteExterne(numCptExt);
        log.debug("Request to get All UserDevice by numCptExt : {}", numCptExt);

        if (userPreference != null) {
            List<UserDevice> userDevices = userDeviceRepository.findAllByUserPreferenceId(userPreference.getId());
            return userDeviceMapper.toDto(userDevices);
        } else {
            return null;
        }
    }

    @Override
    public UserDeviceDTO upsertUserDevice(UserDeviceDTO userDeviceDTO) {
        List<UserDevice> lUserDevices = userDeviceRepository.findAllByDeviceToken(userDeviceDTO.getDeviceToken());
        UserPreference user = userPreferenceRepository.findOneByNumCompteExterne(MockUtil.NUM_COMPTE_EXT);

        int userDeviceSize = lUserDevices.size();

        UserDevice userDevice = new UserDevice();
        if (userDeviceSize > 0) {
            userDevice = lUserDevices.get(0);
            for (int i=1; i<userDeviceSize; i++) {
                this.delete(lUserDevices.get(0).getId());
            }
        } else {
            userDevice.deviceToken(userDeviceDTO.getDeviceToken());
        }
        userDevice.typeDevice(TypeDevice.MOBILE);
        userDevice.userPreference(user);

        userDevice = userDeviceRepository.save(userDevice);

        return userDeviceMapper.toDto(userDevice);
    }

    public List<UserDeviceDTO> findAllByDeviceToken(String deviceToken) {
        log.debug("Request to get UserDevice by Device Token: {}", deviceToken);
        List<UserDevice> userDevices = userDeviceRepository.findAllByDeviceToken(deviceToken);
        return userDeviceMapper.toDto(userDevices);
    }
}

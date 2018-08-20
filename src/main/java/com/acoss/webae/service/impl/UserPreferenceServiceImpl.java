package com.acoss.webae.service.impl;

import com.acoss.webae.domain.PreferenceNotif;
import com.acoss.webae.service.UserPreferenceService;
import com.acoss.webae.domain.UserPreference;
import com.acoss.webae.repository.UserPreferenceRepository;
import com.acoss.webae.service.dto.UserPreferenceDTO;
import com.acoss.webae.service.mapper.UserPreferenceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing UserPreference.
 */
@Service
@Transactional
public class UserPreferenceServiceImpl implements UserPreferenceService {

    private final Logger log = LoggerFactory.getLogger(UserPreferenceServiceImpl.class);

    private final UserPreferenceRepository userPreferenceRepository;

    private final UserPreferenceMapper userPreferenceMapper;

    public UserPreferenceServiceImpl(UserPreferenceRepository userPreferenceRepository, UserPreferenceMapper userPreferenceMapper) {
        this.userPreferenceRepository = userPreferenceRepository;
        this.userPreferenceMapper = userPreferenceMapper;
    }

    /**
     * Save a userPreference.
     *
     * @param userPreferenceDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public UserPreferenceDTO save(UserPreferenceDTO userPreferenceDTO) {
        log.debug("Request to save UserPreference : {}", userPreferenceDTO);
        UserPreference userPreference = userPreferenceMapper.toEntity(userPreferenceDTO);
//        for(PreferenceNotif pNotif: userPreference.getPreferenceNotifs()){
//            pNotif.setUserPreference();
//        }
        userPreference = userPreferenceRepository.save(userPreference);
        return userPreferenceMapper.toDto(userPreference);
    }

    /**
     * Get all the userPreferences.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<UserPreferenceDTO> findAll() {
        log.debug("Request to get all UserPreferences");
        return userPreferenceRepository.findAll().stream()
            .map(userPreferenceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one userPreference by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public UserPreferenceDTO findOne(Long id) {
        log.debug("Request to get UserPreference : {}", id);
        UserPreference userPreference = userPreferenceRepository.findOne(id);
        return userPreferenceMapper.toDto(userPreference);
    }

    /**
     * Delete the userPreference by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserPreference : {}", id);
        userPreferenceRepository.delete(id);
    }

    @Override
    public UserPreferenceDTO findOneByNumCptExt(String num_cpt_ext) {
        log.debug("Request to get UserPreference by Num Compte Externe: {}", num_cpt_ext);
        UserPreference userPreference = userPreferenceRepository.findOneByNumCompteExterne(num_cpt_ext);
        return userPreferenceMapper.toDto(userPreference);
    }
}

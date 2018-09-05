package com.acoss.webae.service.impl;

import com.acoss.webae.domain.UserPreference;
import com.acoss.webae.domain.enumeration.NotifChannel;
import com.acoss.webae.domain.enumeration.NotifMoment;
import com.acoss.webae.repository.UserPreferenceRepository;
import com.acoss.webae.service.PreferenceNotifService;
import com.acoss.webae.domain.PreferenceNotif;
import com.acoss.webae.repository.PreferenceNotifRepository;
import com.acoss.webae.service.dto.PreferenceNotifDTO;
import com.acoss.webae.service.dto.UserPreferenceDTO;
import com.acoss.webae.service.mapper.PreferenceNotifMapper;
import com.acoss.webae.service.util.MockUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing PreferenceNotif.
 */
@Service
@Transactional
public class PreferenceNotifServiceImpl implements PreferenceNotifService {

    private final Logger log = LoggerFactory.getLogger(PreferenceNotifServiceImpl.class);

    private final PreferenceNotifRepository preferenceNotifRepository;

    private final PreferenceNotifMapper preferenceNotifMapper;

    private final UserPreferenceRepository userPreferenceRepository;

    public PreferenceNotifServiceImpl(PreferenceNotifRepository preferenceNotifRepository, PreferenceNotifMapper preferenceNotifMapper, UserPreferenceRepository userPreferenceRepository) {
        this.preferenceNotifRepository = preferenceNotifRepository;
        this.preferenceNotifMapper = preferenceNotifMapper;
        this.userPreferenceRepository=userPreferenceRepository;
    }

    /**
     * Save a preferenceNotif.
     *
     * @param preferenceNotifDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PreferenceNotifDTO save(PreferenceNotifDTO preferenceNotifDTO) {
        UserPreference uapd = userPreferenceRepository.findOneByNumCompteExterne(MockUtil.NUM_COMPTE_EXT);
        log.debug("Request to save PreferenceNotif : {}", preferenceNotifDTO);
        PreferenceNotif preferenceNotif = preferenceNotifMapper.toEntity(preferenceNotifDTO);
        preferenceNotif.setUserPreference(uapd);

        preferenceNotif = preferenceNotifRepository.save(preferenceNotif);
        return preferenceNotifMapper.toDto(preferenceNotif);
    }

    /**
     * Get all the preferenceNotifs.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<PreferenceNotifDTO> findAll() {
        log.debug("Request to get all PreferenceNotifs");
        return preferenceNotifRepository.findAll().stream()
            .map(preferenceNotifMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one preferenceNotif by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PreferenceNotifDTO findOne(Long id) {
        log.debug("Request to get PreferenceNotif : {}", id);
        PreferenceNotif preferenceNotif = preferenceNotifRepository.findOne(id);
        return preferenceNotifMapper.toDto(preferenceNotif);
    }

    /**
     * Delete the preferenceNotif by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PreferenceNotif : {}", id);
        preferenceNotifRepository.delete(id);
    }

    @Override
    public void deleteByChannelAndType(NotifChannel channel, NotifMoment type) {
        UserPreference uapd = userPreferenceRepository.findOneByNumCompteExterne(MockUtil.NUM_COMPTE_EXT);

        if (uapd != null) {
            preferenceNotifRepository.deleteByUserPreferenceIdAndChannelAndMoment(uapd.getId(), channel, type);
        }
    }
}

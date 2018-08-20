package com.acoss.webae.service.impl;

import com.acoss.webae.service.LastConnectionService;
import com.acoss.webae.domain.LastConnection;
import com.acoss.webae.repository.LastConnectionRepository;
import com.acoss.webae.service.dto.LastConnectionDTO;
import com.acoss.webae.service.mapper.LastConnectionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing LastConnection.
 */
@Service
@Transactional
public class LastConnectionServiceImpl implements LastConnectionService {

    private final Logger log = LoggerFactory.getLogger(LastConnectionServiceImpl.class);

    private final LastConnectionRepository lastConnectionRepository;

    private final LastConnectionMapper lastConnectionMapper;

    public LastConnectionServiceImpl(LastConnectionRepository lastConnectionRepository, LastConnectionMapper lastConnectionMapper) {
        this.lastConnectionRepository = lastConnectionRepository;
        this.lastConnectionMapper = lastConnectionMapper;
    }

    /**
     * Save a lastConnection.
     *
     * @param lastConnectionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public LastConnectionDTO save(LastConnectionDTO lastConnectionDTO) {
        log.debug("Request to save LastConnection : {}", lastConnectionDTO);
        LastConnection lastConnection = lastConnectionMapper.toEntity(lastConnectionDTO);
        lastConnection = lastConnectionRepository.save(lastConnection);
        return lastConnectionMapper.toDto(lastConnection);
    }

    /**
     * Get all the lastConnections.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<LastConnectionDTO> findAll() {
        log.debug("Request to get all LastConnections");
        return lastConnectionRepository.findAll().stream()
            .map(lastConnectionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one lastConnection by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public LastConnectionDTO findOne(Long id) {
        log.debug("Request to get LastConnection : {}", id);
        LastConnection lastConnection = lastConnectionRepository.findOne(id);
        return lastConnectionMapper.toDto(lastConnection);
    }

    /**
     * Delete the lastConnection by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete LastConnection : {}", id);
        lastConnectionRepository.delete(id);
    }
}

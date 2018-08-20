package com.acoss.webae.service.impl;

import com.acoss.webae.service.TauxService;
import com.acoss.webae.domain.Taux;
import com.acoss.webae.repository.TauxRepository;
import com.acoss.webae.service.dto.TauxDTO;
import com.acoss.webae.service.mapper.TauxMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Taux.
 */
@Service
@Transactional
public class TauxServiceImpl implements TauxService {

    private final Logger log = LoggerFactory.getLogger(TauxServiceImpl.class);

    private final TauxRepository tauxRepository;

    private final TauxMapper tauxMapper;

    public TauxServiceImpl(TauxRepository tauxRepository, TauxMapper tauxMapper) {
        this.tauxRepository = tauxRepository;
        this.tauxMapper = tauxMapper;
    }

    /**
     * Save a taux.
     *
     * @param tauxDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TauxDTO save(TauxDTO tauxDTO) {
        log.debug("Request to save Taux : {}", tauxDTO);
        Taux taux = tauxMapper.toEntity(tauxDTO);
        taux = tauxRepository.save(taux);
        return tauxMapper.toDto(taux);
    }

    /**
     * Get all the tauxes.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<TauxDTO> findAll() {
        log.debug("Request to get all Tauxes");
        return tauxRepository.findAll().stream()
            .map(tauxMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one taux by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TauxDTO findOne(Long id) {
        log.debug("Request to get Taux : {}", id);
        Taux taux = tauxRepository.findOne(id);
        return tauxMapper.toDto(taux);
    }

    /**
     * Delete the taux by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Taux : {}", id);
        tauxRepository.delete(id);
    }
}

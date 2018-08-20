package com.acoss.webae.service.impl;

import com.acoss.webae.service.DemandeService;
import com.acoss.webae.domain.Demande;
import com.acoss.webae.repository.DemandeRepository;
import com.acoss.webae.service.dto.DemandeDTO;
import com.acoss.webae.service.mapper.DemandeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Demande.
 */
@Service
@Transactional
public class DemandeServiceImpl implements DemandeService {

    private final Logger log = LoggerFactory.getLogger(DemandeServiceImpl.class);

    private final DemandeRepository demandeRepository;

    private final DemandeMapper demandeMapper;

    public DemandeServiceImpl(DemandeRepository demandeRepository, DemandeMapper demandeMapper) {
        this.demandeRepository = demandeRepository;
        this.demandeMapper = demandeMapper;
    }

    /**
     * Save a demande.
     *
     * @param demandeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DemandeDTO save(DemandeDTO demandeDTO) {
        log.debug("Request to save Demande : {}", demandeDTO);
        Demande demande = demandeMapper.toEntity(demandeDTO);
        demande = demandeRepository.save(demande);
        return demandeMapper.toDto(demande);
    }

    /**
     * Get all the demandes.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<DemandeDTO> findAll() {
        log.debug("Request to get all Demandes");
        return demandeRepository.findAll().stream()
            .map(demandeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one demande by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public DemandeDTO findOne(Long id) {
        log.debug("Request to get Demande : {}", id);
        Demande demande = demandeRepository.findOne(id);
        return demandeMapper.toDto(demande);
    }

    /**
     * Delete the demande by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Demande : {}", id);
        demandeRepository.delete(id);
    }
}

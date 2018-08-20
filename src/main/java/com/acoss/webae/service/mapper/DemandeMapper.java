package com.acoss.webae.service.mapper;

import com.acoss.webae.domain.*;
import com.acoss.webae.service.dto.DemandeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Demande and its DTO DemandeDTO.
 */
@Mapper(componentModel = "spring", uses = {UserPreferenceMapper.class})
public interface DemandeMapper extends EntityMapper<DemandeDTO, Demande> {

    @Mapping(source = "userPreference.id", target = "userPreferenceId")
    @Mapping(source = "userPreference.numCompteExterne", target = "userPreferenceNumCompteExterne")
    DemandeDTO toDto(Demande demande);

    @Mapping(source = "userPreferenceId", target = "userPreference")
    Demande toEntity(DemandeDTO demandeDTO);

    default Demande fromId(Long id) {
        if (id == null) {
            return null;
        }
        Demande demande = new Demande();
        demande.setId(id);
        return demande;
    }
}

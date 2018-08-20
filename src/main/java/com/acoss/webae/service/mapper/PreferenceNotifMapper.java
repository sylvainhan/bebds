package com.acoss.webae.service.mapper;

import com.acoss.webae.domain.*;
import com.acoss.webae.service.dto.PreferenceNotifDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PreferenceNotif and its DTO PreferenceNotifDTO.
 */
@Mapper(componentModel = "spring", uses = {UserPreferenceMapper.class})
public interface PreferenceNotifMapper extends EntityMapper<PreferenceNotifDTO, PreferenceNotif> {

    @Mapping(source = "userPreference.id", target = "userPreferenceId")
    @Mapping(source = "userPreference.numCompteExterne", target = "numCompteExterne")
    PreferenceNotifDTO toDto(PreferenceNotif preferenceNotif);

    @Mapping(source = "userPreferenceId", target = "userPreference.id")
    PreferenceNotif toEntity(PreferenceNotifDTO preferenceNotifDTO);

    default PreferenceNotif fromId(Long id) {
        if (id == null) {
            return null;
        }
        PreferenceNotif preferenceNotif = new PreferenceNotif();
        preferenceNotif.setId(id);
        return preferenceNotif;
    }
}

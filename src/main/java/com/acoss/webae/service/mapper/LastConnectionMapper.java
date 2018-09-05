package com.acoss.webae.service.mapper;

import com.acoss.webae.domain.*;
import com.acoss.webae.service.dto.LastConnectionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity LastConnection and its DTO LastConnectionDTO.
 */
@Mapper(componentModel = "spring", uses = {UserPreferenceMapper.class})
public interface LastConnectionMapper extends EntityMapper<LastConnectionDTO, LastConnection> {

    @Mapping(source = "userPreference.id", target = "userPreferenceId")
    @Mapping(source = "userPreference.numCompteExterne", target = "numCompteExterne")
    LastConnectionDTO toDto(LastConnection lastConnection);

    @Mapping(source = "userPreferenceId", target = "userPreference")
    LastConnection toEntity(LastConnectionDTO lastConnectionDTO);

    default LastConnection fromId(Long id) {
        if (id == null) {
            return null;
        }
        LastConnection lastConnection = new LastConnection();
        lastConnection.setId(id);
        return lastConnection;
    }
}

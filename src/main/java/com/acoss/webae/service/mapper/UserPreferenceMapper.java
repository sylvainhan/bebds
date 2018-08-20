package com.acoss.webae.service.mapper;

import com.acoss.webae.domain.*;
import com.acoss.webae.service.dto.UserPreferenceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity UserPreference and its DTO UserPreferenceDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UserPreferenceMapper extends EntityMapper<UserPreferenceDTO, UserPreference> {

    @Mapping(target = "userDevices", ignore = true)
    @Mapping(target = "lastConnections", ignore = true)
    @Mapping(target = "demandes", ignore = true)
    @Mapping(target = "notifications", ignore = true)
    @Mapping(target = "preferenceNotifs", ignore = true)
    UserPreference toEntity(UserPreferenceDTO userPreferenceDTO);

    default UserPreference fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserPreference userPreference = new UserPreference();
        userPreference.setId(id);
        return userPreference;
    }
}

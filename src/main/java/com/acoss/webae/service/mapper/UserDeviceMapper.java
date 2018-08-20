package com.acoss.webae.service.mapper;

import com.acoss.webae.domain.*;
import com.acoss.webae.service.dto.UserDeviceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity UserDevice and its DTO UserDeviceDTO.
 */
@Mapper(componentModel = "spring", uses = {UserPreferenceMapper.class})
public interface UserDeviceMapper extends EntityMapper<UserDeviceDTO, UserDevice> {

    @Mapping(source = "userPreference.id", target = "userPreferenceId")
    @Mapping(source = "userPreference.numCompteExterne", target = "numCompteExterne")
    UserDeviceDTO toDto(UserDevice userDevice);

    @Mapping(source = "userPreferenceId", target = "userPreference")
    UserDevice toEntity(UserDeviceDTO userDeviceDTO);

    default UserDevice fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserDevice userDevice = new UserDevice();
        userDevice.setId(id);
        return userDevice;
    }
}

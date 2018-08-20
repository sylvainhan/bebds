package com.acoss.webae.service.mapper;

import com.acoss.webae.domain.*;
import com.acoss.webae.service.dto.NotificationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Notification and its DTO NotificationDTO.
 */
@Mapper(componentModel = "spring", uses = {UserPreferenceMapper.class})
public interface NotificationMapper extends EntityMapper<NotificationDTO, Notification> {

    @Mapping(source = "userPreference.id", target = "userPreferenceId")
    @Mapping(source = "userPreference.numCompteExterne", target = "userPreferenceNumCompteExterne")
    NotificationDTO toDto(Notification notification);

    @Mapping(source = "userPreferenceId", target = "userPreference")
    Notification toEntity(NotificationDTO notificationDTO);

    default Notification fromId(Long id) {
        if (id == null) {
            return null;
        }
        Notification notification = new Notification();
        notification.setId(id);
        return notification;
    }
}

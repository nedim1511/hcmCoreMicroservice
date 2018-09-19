package ba.infostudio.com.service.mapper;

import ba.infostudio.com.domain.*;
import ba.infostudio.com.service.dto.UserNotificationsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity UserNotifications and its DTO UserNotificationsDTO.
 */
@Mapper(componentModel = "spring", uses = {NotificationTemplatesMapper.class})
public interface UserNotificationsMapper extends EntityMapper<UserNotificationsDTO, UserNotifications> {

    @Mapping(source = "notificationTemplates.id", target = "notification_templatesId")
    UserNotificationsDTO toDto(UserNotifications userNotifications);

    @Mapping(source = "notification_templatesId", target = "notificationTemplates")
    UserNotifications toEntity(UserNotificationsDTO userNotificationsDTO);

    default UserNotifications fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserNotifications userNotifications = new UserNotifications();
        userNotifications.setId(id);
        return userNotifications;
    }
}

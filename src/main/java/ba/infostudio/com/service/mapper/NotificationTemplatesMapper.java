package ba.infostudio.com.service.mapper;

import ba.infostudio.com.domain.*;
import ba.infostudio.com.service.dto.NotificationTemplatesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity NotificationTemplates and its DTO NotificationTemplatesDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface NotificationTemplatesMapper extends EntityMapper<NotificationTemplatesDTO, NotificationTemplates> {



    default NotificationTemplates fromId(Long id) {
        if (id == null) {
            return null;
        }
        NotificationTemplates notificationTemplates = new NotificationTemplates();
        notificationTemplates.setId(id);
        return notificationTemplates;
    }
}

package ba.infostudio.com.service.mapper;

import ba.infostudio.com.domain.*;
import ba.infostudio.com.service.dto.CoAnnouncementsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CoAnnouncements and its DTO CoAnnouncementsDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CoAnnouncementsMapper extends EntityMapper<CoAnnouncementsDTO, CoAnnouncements> {



    default CoAnnouncements fromId(Long id) {
        if (id == null) {
            return null;
        }
        CoAnnouncements coAnnouncements = new CoAnnouncements();
        coAnnouncements.setId(id);
        return coAnnouncements;
    }
}

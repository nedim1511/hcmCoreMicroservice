package ba.infostudio.com.service.mapper;

import ba.infostudio.com.domain.*;
import ba.infostudio.com.service.dto.RgQualificationsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RgQualifications and its DTO RgQualificationsDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RgQualificationsMapper extends EntityMapper<RgQualificationsDTO, RgQualifications> {



    default RgQualifications fromId(Long id) {
        if (id == null) {
            return null;
        }
        RgQualifications rgQualifications = new RgQualifications();
        rgQualifications.setId(id);
        return rgQualifications;
    }
}

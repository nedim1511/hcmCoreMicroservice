package ba.infostudio.com.service.mapper;

import ba.infostudio.com.domain.*;
import ba.infostudio.com.service.dto.RgHolidaysDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RgHolidays and its DTO RgHolidaysDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RgHolidaysMapper extends EntityMapper<RgHolidaysDTO, RgHolidays> {



    default RgHolidays fromId(Long id) {
        if (id == null) {
            return null;
        }
        RgHolidays rgHolidays = new RgHolidays();
        rgHolidays.setId(id);
        return rgHolidays;
    }
}

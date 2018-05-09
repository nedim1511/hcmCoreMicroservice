package ba.infostudio.com.service.mapper;

import ba.infostudio.com.domain.*;
import ba.infostudio.com.service.dto.RgCurrencyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RgCurrency and its DTO RgCurrencyDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RgCurrencyMapper extends EntityMapper<RgCurrencyDTO, RgCurrency> {



    default RgCurrency fromId(Long id) {
        if (id == null) {
            return null;
        }
        RgCurrency rgCurrency = new RgCurrency();
        rgCurrency.setId(id);
        return rgCurrency;
    }
}

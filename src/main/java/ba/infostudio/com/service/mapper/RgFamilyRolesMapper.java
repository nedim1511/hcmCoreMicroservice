package ba.infostudio.com.service.mapper;

import ba.infostudio.com.domain.*;
import ba.infostudio.com.service.dto.RgFamilyRolesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RgFamilyRoles and its DTO RgFamilyRolesDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RgFamilyRolesMapper extends EntityMapper<RgFamilyRolesDTO, RgFamilyRoles> {



    default RgFamilyRoles fromId(Long id) {
        if (id == null) {
            return null;
        }
        RgFamilyRoles rgFamilyRoles = new RgFamilyRoles();
        rgFamilyRoles.setId(id);
        return rgFamilyRoles;
    }
}

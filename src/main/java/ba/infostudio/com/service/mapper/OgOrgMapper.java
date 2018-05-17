package ba.infostudio.com.service.mapper;

import ba.infostudio.com.domain.*;
import ba.infostudio.com.service.dto.OgOrgDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity OgOrg and its DTO OgOrgDTO.
 */
@Mapper(componentModel = "spring", uses = {OgOrgTypesMapper.class, LeLegalEntitiesMapper.class})
public interface OgOrgMapper extends EntityMapper<OgOrgDTO, OgOrg> {

    @Mapping(source = "idOrganizationTypeId.id", target = "idOrganizationTypeIdId")
    @Mapping(source = "idOrganizationTypeId.name", target = "idOrganizationTypeIdName")
    @Mapping(source = "idParentId.id", target = "idParentIdId")
    @Mapping(source = "idParentId.name", target = "idParentIdName")
    @Mapping(source = "idLegalEntityId.id", target = "idLegalEntityIdId")
    @Mapping(source = "idLegalEntityId.name", target = "idLegalEntityIdName")
    OgOrgDTO toDto(OgOrg ogOrg);

    @Mapping(source = "idOrganizationTypeIdId", target = "idOrganizationTypeId")
    @Mapping(source = "idParentIdId", target = "idParentId")
    @Mapping(source = "idLegalEntityIdId", target = "idLegalEntityId")
    OgOrg toEntity(OgOrgDTO ogOrgDTO);

    default OgOrg fromId(Long id) {
        if (id == null) {
            return null;
        }
        OgOrg ogOrg = new OgOrg();
        ogOrg.setId(id);
        return ogOrg;
    }
}

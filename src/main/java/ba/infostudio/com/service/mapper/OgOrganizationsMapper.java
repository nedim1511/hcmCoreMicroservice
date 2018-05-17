package ba.infostudio.com.service.mapper;

import ba.infostudio.com.domain.*;
import ba.infostudio.com.service.dto.OgOrganizationsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity OgOrganizations and its DTO OgOrganizationsDTO.
 */
@Mapper(componentModel = "spring", uses = {LeLegalEntitiesMapper.class, OgOrgTypesMapper.class})
public interface OgOrganizationsMapper extends EntityMapper<OgOrganizationsDTO, OgOrganizations> {

    @Mapping(source = "idLegalEntity.id", target = "idLegalEntityId")
    @Mapping(source = "idLegalEntity.name", target = "idLegalEntityName")
    @Mapping(source = "idParent.id", target = "idParentId")
    @Mapping(source = "idParent.name", target = "idParentName")
    @Mapping(source = "idOrganizationType.id", target = "idOrganizationTypeId")
    @Mapping(source = "idOrganizationType.name", target = "idOrganizationTypeName")
    OgOrganizationsDTO toDto(OgOrganizations ogOrganizations);

    @Mapping(source = "idLegalEntityId", target = "idLegalEntity")
    @Mapping(source = "idParentId", target = "idParent")
    @Mapping(source = "idOrganizationTypeId", target = "idOrganizationType")
    OgOrganizations toEntity(OgOrganizationsDTO ogOrganizationsDTO);

    default OgOrganizations fromId(Long id) {
        if (id == null) {
            return null;
        }
        OgOrganizations ogOrganizations = new OgOrganizations();
        ogOrganizations.setId(id);
        return ogOrganizations;
    }
}

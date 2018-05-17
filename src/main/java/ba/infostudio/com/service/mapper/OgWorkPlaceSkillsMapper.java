package ba.infostudio.com.service.mapper;

import ba.infostudio.com.domain.*;
import ba.infostudio.com.service.dto.OgWorkPlaceSkillsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity OgWorkPlaceSkills and its DTO OgWorkPlaceSkillsDTO.
 */
@Mapper(componentModel = "spring", uses = {OgWorkPlacesMapper.class, RgSkillsMapper.class, RgSkillGradesMapper.class})
public interface OgWorkPlaceSkillsMapper extends EntityMapper<OgWorkPlaceSkillsDTO, OgWorkPlaceSkills> {

    @Mapping(source = "idWorkPlace.id", target = "idWorkPlaceId")
    @Mapping(source = "idWorkPlace.name", target = "idWorkPlaceName")
    @Mapping(source = "idSkill.id", target = "idSkillId")
    @Mapping(source = "idSkill.name", target = "idSkillName")
    @Mapping(source = "idGrade.id", target = "idGradeId")
    @Mapping(source = "idGrade.name", target = "idGradeName")
    OgWorkPlaceSkillsDTO toDto(OgWorkPlaceSkills ogWorkPlaceSkills);

    @Mapping(source = "idWorkPlaceId", target = "idWorkPlace")
    @Mapping(source = "idSkillId", target = "idSkill")
    @Mapping(source = "idGradeId", target = "idGrade")
    OgWorkPlaceSkills toEntity(OgWorkPlaceSkillsDTO ogWorkPlaceSkillsDTO);

    default OgWorkPlaceSkills fromId(Long id) {
        if (id == null) {
            return null;
        }
        OgWorkPlaceSkills ogWorkPlaceSkills = new OgWorkPlaceSkills();
        ogWorkPlaceSkills.setId(id);
        return ogWorkPlaceSkills;
    }
}

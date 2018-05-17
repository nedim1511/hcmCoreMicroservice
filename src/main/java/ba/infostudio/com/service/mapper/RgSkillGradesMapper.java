package ba.infostudio.com.service.mapper;

import ba.infostudio.com.domain.*;
import ba.infostudio.com.service.dto.RgSkillGradesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RgSkillGrades and its DTO RgSkillGradesDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RgSkillGradesMapper extends EntityMapper<RgSkillGradesDTO, RgSkillGrades> {



    default RgSkillGrades fromId(Long id) {
        if (id == null) {
            return null;
        }
        RgSkillGrades rgSkillGrades = new RgSkillGrades();
        rgSkillGrades.setId(id);
        return rgSkillGrades;
    }
}

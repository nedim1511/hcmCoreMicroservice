package ba.infostudio.com.service.mapper;

import ba.infostudio.com.domain.*;
import ba.infostudio.com.service.dto.RgSkillsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RgSkills and its DTO RgSkillsDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RgSkillsMapper extends EntityMapper<RgSkillsDTO, RgSkills> {



    default RgSkills fromId(Long id) {
        if (id == null) {
            return null;
        }
        RgSkills rgSkills = new RgSkills();
        rgSkills.setId(id);
        return rgSkills;
    }
}

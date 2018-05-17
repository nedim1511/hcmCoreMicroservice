package ba.infostudio.com.repository;

import ba.infostudio.com.domain.RgSkills;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RgSkills entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RgSkillsRepository extends JpaRepository<RgSkills, Long> {

}

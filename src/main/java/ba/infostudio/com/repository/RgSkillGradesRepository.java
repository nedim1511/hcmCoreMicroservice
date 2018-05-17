package ba.infostudio.com.repository;

import ba.infostudio.com.domain.RgSkillGrades;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RgSkillGrades entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RgSkillGradesRepository extends JpaRepository<RgSkillGrades, Long> {

}

package ba.infostudio.com.repository;

import ba.infostudio.com.domain.RgSchools;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RgSchools entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RgSchoolsRepository extends JpaRepository<RgSchools, Long> {

}

package ba.infostudio.com.repository;

import ba.infostudio.com.domain.RgQualifications;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RgQualifications entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RgQualificationsRepository extends JpaRepository<RgQualifications, Long> {

}

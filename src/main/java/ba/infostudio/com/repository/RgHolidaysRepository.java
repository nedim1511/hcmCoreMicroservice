package ba.infostudio.com.repository;

import ba.infostudio.com.domain.RgHolidays;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RgHolidays entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RgHolidaysRepository extends JpaRepository<RgHolidays, Long> {

}

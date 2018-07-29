package ba.infostudio.com.repository;

import ba.infostudio.com.domain.CoAnnouncements;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.time.LocalDate;
import java.util.List;


/**
 * Spring Data JPA repository for the CoAnnouncements entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CoAnnouncementsRepository extends JpaRepository<CoAnnouncements, Long> {
    List<CoAnnouncements> findAllByValidToGreaterThanEqualAndValidFromLessThanEqual(LocalDate currentDate, LocalDate currentDate2);
}

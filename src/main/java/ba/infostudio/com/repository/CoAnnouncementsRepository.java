package ba.infostudio.com.repository;

import ba.infostudio.com.domain.CoAnnouncements;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CoAnnouncements entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CoAnnouncementsRepository extends JpaRepository<CoAnnouncements, Long> {

}

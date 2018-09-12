package ba.infostudio.com.repository;

import ba.infostudio.com.domain.NotificationTemplates;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the NotificationTemplates entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NotificationTemplatesRepository extends JpaRepository<NotificationTemplates, Long> {

}

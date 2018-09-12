package ba.infostudio.com.repository;

import ba.infostudio.com.domain.UserNotifications;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the UserNofitifications entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserNotificationsRepository extends JpaRepository<UserNotifications, Long> {

}

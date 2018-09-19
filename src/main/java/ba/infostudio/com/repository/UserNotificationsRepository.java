package ba.infostudio.com.repository;

import ba.infostudio.com.domain.UserNotifications;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the UserNotifications entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserNotificationsRepository extends JpaRepository<UserNotifications, Long> {
    List<UserNotifications> findByIdUser(Long idUser);
}

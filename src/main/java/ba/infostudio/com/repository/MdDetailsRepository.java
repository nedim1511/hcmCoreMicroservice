package ba.infostudio.com.repository;

import ba.infostudio.com.domain.MdDetails;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MdDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MdDetailsRepository extends JpaRepository<MdDetails, Long> {

}

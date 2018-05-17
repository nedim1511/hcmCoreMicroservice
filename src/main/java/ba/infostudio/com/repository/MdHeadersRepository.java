package ba.infostudio.com.repository;

import ba.infostudio.com.domain.MdHeaders;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MdHeaders entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MdHeadersRepository extends JpaRepository<MdHeaders, Long> {

}

package ba.infostudio.com.repository;

import ba.infostudio.com.domain.MdDetailValue;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MdDetailValue entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MdDetailValueRepository extends JpaRepository<MdDetailValue, Long> {

}

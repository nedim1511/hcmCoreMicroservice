package ba.infostudio.com.repository;

import ba.infostudio.com.domain.LeLegalEntities;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the LeLegalEntities entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LeLegalEntitiesRepository extends JpaRepository<LeLegalEntities, Long> {
    List<LeLegalEntities> findByIdEntityTypeId(Long id);
}

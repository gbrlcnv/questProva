package it.ec.quest.repository;

import it.ec.quest.domain.Schema1;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Schema1 entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Schema1Repository extends JpaRepository<Schema1, Long> {

}

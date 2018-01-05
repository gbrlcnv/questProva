package it.ec.quest.repository;

import it.ec.quest.domain.SchemaField;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SchemaField entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SchemaFieldRepository extends JpaRepository<SchemaField, Long> {

}

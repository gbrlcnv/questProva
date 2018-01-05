package it.ec.quest.repository;

import it.ec.quest.domain.SchemaAnswer;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SchemaAnswer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SchemaAnswerRepository extends JpaRepository<SchemaAnswer, Long> {

}

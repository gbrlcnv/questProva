package it.ec.quest.service;

import it.ec.quest.domain.SchemaAnswer;
import it.ec.quest.repository.SchemaAnswerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing SchemaAnswer.
 */
@Service
@Transactional
public class SchemaAnswerService {

    private final Logger log = LoggerFactory.getLogger(SchemaAnswerService.class);

    private final SchemaAnswerRepository schemaAnswerRepository;

    public SchemaAnswerService(SchemaAnswerRepository schemaAnswerRepository) {
        this.schemaAnswerRepository = schemaAnswerRepository;
    }

    /**
     * Save a schemaAnswer.
     *
     * @param schemaAnswer the entity to save
     * @return the persisted entity
     */
    public SchemaAnswer save(SchemaAnswer schemaAnswer) {
        log.debug("Request to save SchemaAnswer : {}", schemaAnswer);
        return schemaAnswerRepository.save(schemaAnswer);
    }

    /**
     * Get all the schemaAnswers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SchemaAnswer> findAll(Pageable pageable) {
        log.debug("Request to get all SchemaAnswers");
        return schemaAnswerRepository.findAll(pageable);
    }

    /**
     * Get one schemaAnswer by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public SchemaAnswer findOne(Long id) {
        log.debug("Request to get SchemaAnswer : {}", id);
        return schemaAnswerRepository.findOne(id);
    }

    /**
     * Delete the schemaAnswer by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete SchemaAnswer : {}", id);
        schemaAnswerRepository.delete(id);
    }
}

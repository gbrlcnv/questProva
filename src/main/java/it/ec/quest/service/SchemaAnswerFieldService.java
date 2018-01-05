package it.ec.quest.service;

import it.ec.quest.domain.SchemaAnswerField;
import it.ec.quest.repository.SchemaAnswerFieldRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing SchemaAnswerField.
 */
@Service
@Transactional
public class SchemaAnswerFieldService {

    private final Logger log = LoggerFactory.getLogger(SchemaAnswerFieldService.class);

    private final SchemaAnswerFieldRepository schemaAnswerFieldRepository;

    public SchemaAnswerFieldService(SchemaAnswerFieldRepository schemaAnswerFieldRepository) {
        this.schemaAnswerFieldRepository = schemaAnswerFieldRepository;
    }

    /**
     * Save a schemaAnswerField.
     *
     * @param schemaAnswerField the entity to save
     * @return the persisted entity
     */
    public SchemaAnswerField save(SchemaAnswerField schemaAnswerField) {
        log.debug("Request to save SchemaAnswerField : {}", schemaAnswerField);
        return schemaAnswerFieldRepository.save(schemaAnswerField);
    }

    /**
     * Get all the schemaAnswerFields.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SchemaAnswerField> findAll(Pageable pageable) {
        log.debug("Request to get all SchemaAnswerFields");
        return schemaAnswerFieldRepository.findAll(pageable);
    }

    /**
     * Get one schemaAnswerField by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public SchemaAnswerField findOne(Long id) {
        log.debug("Request to get SchemaAnswerField : {}", id);
        return schemaAnswerFieldRepository.findOne(id);
    }

    /**
     * Delete the schemaAnswerField by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete SchemaAnswerField : {}", id);
        schemaAnswerFieldRepository.delete(id);
    }
}

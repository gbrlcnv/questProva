package it.ec.quest.service;

import it.ec.quest.domain.SchemaField;
import it.ec.quest.repository.SchemaFieldRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing SchemaField.
 */
@Service
@Transactional
public class SchemaFieldService {

    private final Logger log = LoggerFactory.getLogger(SchemaFieldService.class);

    private final SchemaFieldRepository schemaFieldRepository;

    public SchemaFieldService(SchemaFieldRepository schemaFieldRepository) {
        this.schemaFieldRepository = schemaFieldRepository;
    }

    /**
     * Save a schemaField.
     *
     * @param schemaField the entity to save
     * @return the persisted entity
     */
    public SchemaField save(SchemaField schemaField) {
        log.debug("Request to save SchemaField : {}", schemaField);
        return schemaFieldRepository.save(schemaField);
    }

    /**
     * Get all the schemaFields.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SchemaField> findAll(Pageable pageable) {
        log.debug("Request to get all SchemaFields");
        return schemaFieldRepository.findAll(pageable);
    }

    /**
     * Get one schemaField by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public SchemaField findOne(Long id) {
        log.debug("Request to get SchemaField : {}", id);
        return schemaFieldRepository.findOne(id);
    }

    /**
     * Delete the schemaField by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete SchemaField : {}", id);
        schemaFieldRepository.delete(id);
    }
}

package it.ec.quest.service;

import it.ec.quest.domain.Schema1;
import it.ec.quest.repository.Schema1Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Schema1.
 */
@Service
@Transactional
public class Schema1Service {

    private final Logger log = LoggerFactory.getLogger(Schema1Service.class);

    private final Schema1Repository schema1Repository;

    public Schema1Service(Schema1Repository schema1Repository) {
        this.schema1Repository = schema1Repository;
    }

    /**
     * Save a schema1.
     *
     * @param schema1 the entity to save
     * @return the persisted entity
     */
    public Schema1 save(Schema1 schema1) {
        log.debug("Request to save Schema1 : {}", schema1);
        return schema1Repository.save(schema1);
    }

    /**
     * Get all the schema1S.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Schema1> findAll(Pageable pageable) {
        log.debug("Request to get all Schema1S");
        return schema1Repository.findAll(pageable);
    }

    /**
     * Get one schema1 by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Schema1 findOne(Long id) {
        log.debug("Request to get Schema1 : {}", id);
        return schema1Repository.findOne(id);
    }

    /**
     * Delete the schema1 by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Schema1 : {}", id);
        schema1Repository.delete(id);
    }
}

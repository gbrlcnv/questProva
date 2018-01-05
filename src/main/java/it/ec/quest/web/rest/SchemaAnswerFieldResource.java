package it.ec.quest.web.rest;

import com.codahale.metrics.annotation.Timed;
import it.ec.quest.domain.SchemaAnswerField;
import it.ec.quest.service.SchemaAnswerFieldService;
import it.ec.quest.web.rest.errors.BadRequestAlertException;
import it.ec.quest.web.rest.util.HeaderUtil;
import it.ec.quest.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing SchemaAnswerField.
 */
@RestController
@RequestMapping("/api")
public class SchemaAnswerFieldResource {

    private final Logger log = LoggerFactory.getLogger(SchemaAnswerFieldResource.class);

    private static final String ENTITY_NAME = "schemaAnswerField";

    private final SchemaAnswerFieldService schemaAnswerFieldService;

    public SchemaAnswerFieldResource(SchemaAnswerFieldService schemaAnswerFieldService) {
        this.schemaAnswerFieldService = schemaAnswerFieldService;
    }

    /**
     * POST  /schema-answer-fields : Create a new schemaAnswerField.
     *
     * @param schemaAnswerField the schemaAnswerField to create
     * @return the ResponseEntity with status 201 (Created) and with body the new schemaAnswerField, or with status 400 (Bad Request) if the schemaAnswerField has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/schema-answer-fields")
    @Timed
    public ResponseEntity<SchemaAnswerField> createSchemaAnswerField(@RequestBody SchemaAnswerField schemaAnswerField) throws URISyntaxException {
        log.debug("REST request to save SchemaAnswerField : {}", schemaAnswerField);
        if (schemaAnswerField.getId() != null) {
            throw new BadRequestAlertException("A new schemaAnswerField cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SchemaAnswerField result = schemaAnswerFieldService.save(schemaAnswerField);
        return ResponseEntity.created(new URI("/api/schema-answer-fields/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /schema-answer-fields : Updates an existing schemaAnswerField.
     *
     * @param schemaAnswerField the schemaAnswerField to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated schemaAnswerField,
     * or with status 400 (Bad Request) if the schemaAnswerField is not valid,
     * or with status 500 (Internal Server Error) if the schemaAnswerField couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/schema-answer-fields")
    @Timed
    public ResponseEntity<SchemaAnswerField> updateSchemaAnswerField(@RequestBody SchemaAnswerField schemaAnswerField) throws URISyntaxException {
        log.debug("REST request to update SchemaAnswerField : {}", schemaAnswerField);
        if (schemaAnswerField.getId() == null) {
            return createSchemaAnswerField(schemaAnswerField);
        }
        SchemaAnswerField result = schemaAnswerFieldService.save(schemaAnswerField);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, schemaAnswerField.getId().toString()))
            .body(result);
    }

    /**
     * GET  /schema-answer-fields : get all the schemaAnswerFields.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of schemaAnswerFields in body
     */
    @GetMapping("/schema-answer-fields")
    @Timed
    public ResponseEntity<List<SchemaAnswerField>> getAllSchemaAnswerFields(Pageable pageable) {
        log.debug("REST request to get a page of SchemaAnswerFields");
        Page<SchemaAnswerField> page = schemaAnswerFieldService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/schema-answer-fields");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /schema-answer-fields/:id : get the "id" schemaAnswerField.
     *
     * @param id the id of the schemaAnswerField to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the schemaAnswerField, or with status 404 (Not Found)
     */
    @GetMapping("/schema-answer-fields/{id}")
    @Timed
    public ResponseEntity<SchemaAnswerField> getSchemaAnswerField(@PathVariable Long id) {
        log.debug("REST request to get SchemaAnswerField : {}", id);
        SchemaAnswerField schemaAnswerField = schemaAnswerFieldService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(schemaAnswerField));
    }

    /**
     * DELETE  /schema-answer-fields/:id : delete the "id" schemaAnswerField.
     *
     * @param id the id of the schemaAnswerField to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/schema-answer-fields/{id}")
    @Timed
    public ResponseEntity<Void> deleteSchemaAnswerField(@PathVariable Long id) {
        log.debug("REST request to delete SchemaAnswerField : {}", id);
        schemaAnswerFieldService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

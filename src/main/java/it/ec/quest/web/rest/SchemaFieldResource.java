package it.ec.quest.web.rest;

import com.codahale.metrics.annotation.Timed;
import it.ec.quest.domain.SchemaField;
import it.ec.quest.service.SchemaFieldService;
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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing SchemaField.
 */
@RestController
@RequestMapping("/api")
public class SchemaFieldResource {

    private final Logger log = LoggerFactory.getLogger(SchemaFieldResource.class);

    private static final String ENTITY_NAME = "schemaField";

    private final SchemaFieldService schemaFieldService;

    public SchemaFieldResource(SchemaFieldService schemaFieldService) {
        this.schemaFieldService = schemaFieldService;
    }

    /**
     * POST  /schema-fields : Create a new schemaField.
     *
     * @param schemaField the schemaField to create
     * @return the ResponseEntity with status 201 (Created) and with body the new schemaField, or with status 400 (Bad Request) if the schemaField has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/schema-fields")
    @Timed
    public ResponseEntity<SchemaField> createSchemaField(@Valid @RequestBody SchemaField schemaField) throws URISyntaxException {
        log.debug("REST request to save SchemaField : {}", schemaField);
        if (schemaField.getId() != null) {
            throw new BadRequestAlertException("A new schemaField cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SchemaField result = schemaFieldService.save(schemaField);
        return ResponseEntity.created(new URI("/api/schema-fields/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /schema-fields : Updates an existing schemaField.
     *
     * @param schemaField the schemaField to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated schemaField,
     * or with status 400 (Bad Request) if the schemaField is not valid,
     * or with status 500 (Internal Server Error) if the schemaField couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/schema-fields")
    @Timed
    public ResponseEntity<SchemaField> updateSchemaField(@Valid @RequestBody SchemaField schemaField) throws URISyntaxException {
        log.debug("REST request to update SchemaField : {}", schemaField);
        if (schemaField.getId() == null) {
            return createSchemaField(schemaField);
        }
        SchemaField result = schemaFieldService.save(schemaField);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, schemaField.getId().toString()))
            .body(result);
    }

    /**
     * GET  /schema-fields : get all the schemaFields.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of schemaFields in body
     */
    @GetMapping("/schema-fields")
    @Timed
    public ResponseEntity<List<SchemaField>> getAllSchemaFields(Pageable pageable) {
        log.debug("REST request to get a page of SchemaFields");
        Page<SchemaField> page = schemaFieldService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/schema-fields");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /schema-fields/:id : get the "id" schemaField.
     *
     * @param id the id of the schemaField to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the schemaField, or with status 404 (Not Found)
     */
    @GetMapping("/schema-fields/{id}")
    @Timed
    public ResponseEntity<SchemaField> getSchemaField(@PathVariable Long id) {
        log.debug("REST request to get SchemaField : {}", id);
        SchemaField schemaField = schemaFieldService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(schemaField));
    }

    /**
     * DELETE  /schema-fields/:id : delete the "id" schemaField.
     *
     * @param id the id of the schemaField to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/schema-fields/{id}")
    @Timed
    public ResponseEntity<Void> deleteSchemaField(@PathVariable Long id) {
        log.debug("REST request to delete SchemaField : {}", id);
        schemaFieldService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

package it.ec.quest.web.rest;

import com.codahale.metrics.annotation.Timed;
import it.ec.quest.domain.Schema1;
import it.ec.quest.service.Schema1Service;
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
 * REST controller for managing Schema1.
 */
@RestController
@RequestMapping("/api")
public class Schema1Resource {

    private final Logger log = LoggerFactory.getLogger(Schema1Resource.class);

    private static final String ENTITY_NAME = "schema1";

    private final Schema1Service schema1Service;

    public Schema1Resource(Schema1Service schema1Service) {
        this.schema1Service = schema1Service;
    }

    /**
     * POST  /schema-1-s : Create a new schema1.
     *
     * @param schema1 the schema1 to create
     * @return the ResponseEntity with status 201 (Created) and with body the new schema1, or with status 400 (Bad Request) if the schema1 has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/schema-1-s")
    @Timed
    public ResponseEntity<Schema1> createSchema1(@Valid @RequestBody Schema1 schema1) throws URISyntaxException {
        log.debug("REST request to save Schema1 : {}", schema1);
        if (schema1.getId() != null) {
            throw new BadRequestAlertException("A new schema1 cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Schema1 result = schema1Service.save(schema1);
        return ResponseEntity.created(new URI("/api/schema-1-s/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /schema-1-s : Updates an existing schema1.
     *
     * @param schema1 the schema1 to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated schema1,
     * or with status 400 (Bad Request) if the schema1 is not valid,
     * or with status 500 (Internal Server Error) if the schema1 couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/schema-1-s")
    @Timed
    public ResponseEntity<Schema1> updateSchema1(@Valid @RequestBody Schema1 schema1) throws URISyntaxException {
        log.debug("REST request to update Schema1 : {}", schema1);
        if (schema1.getId() == null) {
            return createSchema1(schema1);
        }
        Schema1 result = schema1Service.save(schema1);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, schema1.getId().toString()))
            .body(result);
    }

    /**
     * GET  /schema-1-s : get all the schema1S.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of schema1S in body
     */
    @GetMapping("/schema-1-s")
    @Timed
    public ResponseEntity<List<Schema1>> getAllSchema1S(Pageable pageable) {
        log.debug("REST request to get a page of Schema1S");
        Page<Schema1> page = schema1Service.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/schema-1-s");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /schema-1-s/:id : get the "id" schema1.
     *
     * @param id the id of the schema1 to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the schema1, or with status 404 (Not Found)
     */
    @GetMapping("/schema-1-s/{id}")
    @Timed
    public ResponseEntity<Schema1> getSchema1(@PathVariable Long id) {
        log.debug("REST request to get Schema1 : {}", id);
        Schema1 schema1 = schema1Service.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(schema1));
    }

    /**
     * DELETE  /schema-1-s/:id : delete the "id" schema1.
     *
     * @param id the id of the schema1 to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/schema-1-s/{id}")
    @Timed
    public ResponseEntity<Void> deleteSchema1(@PathVariable Long id) {
        log.debug("REST request to delete Schema1 : {}", id);
        schema1Service.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

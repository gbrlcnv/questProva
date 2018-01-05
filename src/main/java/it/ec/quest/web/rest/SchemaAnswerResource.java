package it.ec.quest.web.rest;

import com.codahale.metrics.annotation.Timed;
import it.ec.quest.domain.SchemaAnswer;
import it.ec.quest.service.SchemaAnswerService;
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
 * REST controller for managing SchemaAnswer.
 */
@RestController
@RequestMapping("/api")
public class SchemaAnswerResource {

    private final Logger log = LoggerFactory.getLogger(SchemaAnswerResource.class);

    private static final String ENTITY_NAME = "schemaAnswer";

    private final SchemaAnswerService schemaAnswerService;

    public SchemaAnswerResource(SchemaAnswerService schemaAnswerService) {
        this.schemaAnswerService = schemaAnswerService;
    }

    /**
     * POST  /schema-answers : Create a new schemaAnswer.
     *
     * @param schemaAnswer the schemaAnswer to create
     * @return the ResponseEntity with status 201 (Created) and with body the new schemaAnswer, or with status 400 (Bad Request) if the schemaAnswer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/schema-answers")
    @Timed
    public ResponseEntity<SchemaAnswer> createSchemaAnswer(@Valid @RequestBody SchemaAnswer schemaAnswer) throws URISyntaxException {
        log.debug("REST request to save SchemaAnswer : {}", schemaAnswer);
        if (schemaAnswer.getId() != null) {
            throw new BadRequestAlertException("A new schemaAnswer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SchemaAnswer result = schemaAnswerService.save(schemaAnswer);
        return ResponseEntity.created(new URI("/api/schema-answers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /schema-answers : Updates an existing schemaAnswer.
     *
     * @param schemaAnswer the schemaAnswer to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated schemaAnswer,
     * or with status 400 (Bad Request) if the schemaAnswer is not valid,
     * or with status 500 (Internal Server Error) if the schemaAnswer couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/schema-answers")
    @Timed
    public ResponseEntity<SchemaAnswer> updateSchemaAnswer(@Valid @RequestBody SchemaAnswer schemaAnswer) throws URISyntaxException {
        log.debug("REST request to update SchemaAnswer : {}", schemaAnswer);
        if (schemaAnswer.getId() == null) {
            return createSchemaAnswer(schemaAnswer);
        }
        SchemaAnswer result = schemaAnswerService.save(schemaAnswer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, schemaAnswer.getId().toString()))
            .body(result);
    }

    /**
     * GET  /schema-answers : get all the schemaAnswers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of schemaAnswers in body
     */
    @GetMapping("/schema-answers")
    @Timed
    public ResponseEntity<List<SchemaAnswer>> getAllSchemaAnswers(Pageable pageable) {
        log.debug("REST request to get a page of SchemaAnswers");
        Page<SchemaAnswer> page = schemaAnswerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/schema-answers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /schema-answers/:id : get the "id" schemaAnswer.
     *
     * @param id the id of the schemaAnswer to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the schemaAnswer, or with status 404 (Not Found)
     */
    @GetMapping("/schema-answers/{id}")
    @Timed
    public ResponseEntity<SchemaAnswer> getSchemaAnswer(@PathVariable Long id) {
        log.debug("REST request to get SchemaAnswer : {}", id);
        SchemaAnswer schemaAnswer = schemaAnswerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(schemaAnswer));
    }

    /**
     * DELETE  /schema-answers/:id : delete the "id" schemaAnswer.
     *
     * @param id the id of the schemaAnswer to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/schema-answers/{id}")
    @Timed
    public ResponseEntity<Void> deleteSchemaAnswer(@PathVariable Long id) {
        log.debug("REST request to delete SchemaAnswer : {}", id);
        schemaAnswerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

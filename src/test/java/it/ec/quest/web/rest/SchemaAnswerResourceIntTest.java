package it.ec.quest.web.rest;

import it.ec.quest.QuestProvaApp;

import it.ec.quest.domain.SchemaAnswer;
import it.ec.quest.repository.SchemaAnswerRepository;
import it.ec.quest.service.SchemaAnswerService;
import it.ec.quest.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static it.ec.quest.web.rest.TestUtil.sameInstant;
import static it.ec.quest.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SchemaAnswerResource REST controller.
 *
 * @see SchemaAnswerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = QuestProvaApp.class)
public class SchemaAnswerResourceIntTest {

    private static final ZonedDateTime DEFAULT_CREATION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATION_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_COMPLETION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_COMPLETION_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_FLAG_COMPLETE = "AAAAAAAAAA";
    private static final String UPDATED_FLAG_COMPLETE = "BBBBBBBBBB";

    @Autowired
    private SchemaAnswerRepository schemaAnswerRepository;

    @Autowired
    private SchemaAnswerService schemaAnswerService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSchemaAnswerMockMvc;

    private SchemaAnswer schemaAnswer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SchemaAnswerResource schemaAnswerResource = new SchemaAnswerResource(schemaAnswerService);
        this.restSchemaAnswerMockMvc = MockMvcBuilders.standaloneSetup(schemaAnswerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SchemaAnswer createEntity(EntityManager em) {
        SchemaAnswer schemaAnswer = new SchemaAnswer()
            .creationDate(DEFAULT_CREATION_DATE)
            .completionDate(DEFAULT_COMPLETION_DATE)
            .flagComplete(DEFAULT_FLAG_COMPLETE);
        return schemaAnswer;
    }

    @Before
    public void initTest() {
        schemaAnswer = createEntity(em);
    }

    @Test
    @Transactional
    public void createSchemaAnswer() throws Exception {
        int databaseSizeBeforeCreate = schemaAnswerRepository.findAll().size();

        // Create the SchemaAnswer
        restSchemaAnswerMockMvc.perform(post("/api/schema-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schemaAnswer)))
            .andExpect(status().isCreated());

        // Validate the SchemaAnswer in the database
        List<SchemaAnswer> schemaAnswerList = schemaAnswerRepository.findAll();
        assertThat(schemaAnswerList).hasSize(databaseSizeBeforeCreate + 1);
        SchemaAnswer testSchemaAnswer = schemaAnswerList.get(schemaAnswerList.size() - 1);
        assertThat(testSchemaAnswer.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testSchemaAnswer.getCompletionDate()).isEqualTo(DEFAULT_COMPLETION_DATE);
        assertThat(testSchemaAnswer.getFlagComplete()).isEqualTo(DEFAULT_FLAG_COMPLETE);
    }

    @Test
    @Transactional
    public void createSchemaAnswerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = schemaAnswerRepository.findAll().size();

        // Create the SchemaAnswer with an existing ID
        schemaAnswer.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSchemaAnswerMockMvc.perform(post("/api/schema-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schemaAnswer)))
            .andExpect(status().isBadRequest());

        // Validate the SchemaAnswer in the database
        List<SchemaAnswer> schemaAnswerList = schemaAnswerRepository.findAll();
        assertThat(schemaAnswerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCreationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = schemaAnswerRepository.findAll().size();
        // set the field null
        schemaAnswer.setCreationDate(null);

        // Create the SchemaAnswer, which fails.

        restSchemaAnswerMockMvc.perform(post("/api/schema-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schemaAnswer)))
            .andExpect(status().isBadRequest());

        List<SchemaAnswer> schemaAnswerList = schemaAnswerRepository.findAll();
        assertThat(schemaAnswerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSchemaAnswers() throws Exception {
        // Initialize the database
        schemaAnswerRepository.saveAndFlush(schemaAnswer);

        // Get all the schemaAnswerList
        restSchemaAnswerMockMvc.perform(get("/api/schema-answers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(schemaAnswer.getId().intValue())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(sameInstant(DEFAULT_CREATION_DATE))))
            .andExpect(jsonPath("$.[*].completionDate").value(hasItem(sameInstant(DEFAULT_COMPLETION_DATE))))
            .andExpect(jsonPath("$.[*].flagComplete").value(hasItem(DEFAULT_FLAG_COMPLETE.toString())));
    }

    @Test
    @Transactional
    public void getSchemaAnswer() throws Exception {
        // Initialize the database
        schemaAnswerRepository.saveAndFlush(schemaAnswer);

        // Get the schemaAnswer
        restSchemaAnswerMockMvc.perform(get("/api/schema-answers/{id}", schemaAnswer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(schemaAnswer.getId().intValue()))
            .andExpect(jsonPath("$.creationDate").value(sameInstant(DEFAULT_CREATION_DATE)))
            .andExpect(jsonPath("$.completionDate").value(sameInstant(DEFAULT_COMPLETION_DATE)))
            .andExpect(jsonPath("$.flagComplete").value(DEFAULT_FLAG_COMPLETE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSchemaAnswer() throws Exception {
        // Get the schemaAnswer
        restSchemaAnswerMockMvc.perform(get("/api/schema-answers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSchemaAnswer() throws Exception {
        // Initialize the database
        schemaAnswerService.save(schemaAnswer);

        int databaseSizeBeforeUpdate = schemaAnswerRepository.findAll().size();

        // Update the schemaAnswer
        SchemaAnswer updatedSchemaAnswer = schemaAnswerRepository.findOne(schemaAnswer.getId());
        // Disconnect from session so that the updates on updatedSchemaAnswer are not directly saved in db
        em.detach(updatedSchemaAnswer);
        updatedSchemaAnswer
            .creationDate(UPDATED_CREATION_DATE)
            .completionDate(UPDATED_COMPLETION_DATE)
            .flagComplete(UPDATED_FLAG_COMPLETE);

        restSchemaAnswerMockMvc.perform(put("/api/schema-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSchemaAnswer)))
            .andExpect(status().isOk());

        // Validate the SchemaAnswer in the database
        List<SchemaAnswer> schemaAnswerList = schemaAnswerRepository.findAll();
        assertThat(schemaAnswerList).hasSize(databaseSizeBeforeUpdate);
        SchemaAnswer testSchemaAnswer = schemaAnswerList.get(schemaAnswerList.size() - 1);
        assertThat(testSchemaAnswer.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testSchemaAnswer.getCompletionDate()).isEqualTo(UPDATED_COMPLETION_DATE);
        assertThat(testSchemaAnswer.getFlagComplete()).isEqualTo(UPDATED_FLAG_COMPLETE);
    }

    @Test
    @Transactional
    public void updateNonExistingSchemaAnswer() throws Exception {
        int databaseSizeBeforeUpdate = schemaAnswerRepository.findAll().size();

        // Create the SchemaAnswer

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSchemaAnswerMockMvc.perform(put("/api/schema-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schemaAnswer)))
            .andExpect(status().isCreated());

        // Validate the SchemaAnswer in the database
        List<SchemaAnswer> schemaAnswerList = schemaAnswerRepository.findAll();
        assertThat(schemaAnswerList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSchemaAnswer() throws Exception {
        // Initialize the database
        schemaAnswerService.save(schemaAnswer);

        int databaseSizeBeforeDelete = schemaAnswerRepository.findAll().size();

        // Get the schemaAnswer
        restSchemaAnswerMockMvc.perform(delete("/api/schema-answers/{id}", schemaAnswer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SchemaAnswer> schemaAnswerList = schemaAnswerRepository.findAll();
        assertThat(schemaAnswerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SchemaAnswer.class);
        SchemaAnswer schemaAnswer1 = new SchemaAnswer();
        schemaAnswer1.setId(1L);
        SchemaAnswer schemaAnswer2 = new SchemaAnswer();
        schemaAnswer2.setId(schemaAnswer1.getId());
        assertThat(schemaAnswer1).isEqualTo(schemaAnswer2);
        schemaAnswer2.setId(2L);
        assertThat(schemaAnswer1).isNotEqualTo(schemaAnswer2);
        schemaAnswer1.setId(null);
        assertThat(schemaAnswer1).isNotEqualTo(schemaAnswer2);
    }
}

package it.ec.quest.web.rest;

import it.ec.quest.QuestProvaApp;

import it.ec.quest.domain.SchemaAnswerField;
import it.ec.quest.repository.SchemaAnswerFieldRepository;
import it.ec.quest.service.SchemaAnswerFieldService;
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
import java.util.List;

import static it.ec.quest.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SchemaAnswerFieldResource REST controller.
 *
 * @see SchemaAnswerFieldResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = QuestProvaApp.class)
public class SchemaAnswerFieldResourceIntTest {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private SchemaAnswerFieldRepository schemaAnswerFieldRepository;

    @Autowired
    private SchemaAnswerFieldService schemaAnswerFieldService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSchemaAnswerFieldMockMvc;

    private SchemaAnswerField schemaAnswerField;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SchemaAnswerFieldResource schemaAnswerFieldResource = new SchemaAnswerFieldResource(schemaAnswerFieldService);
        this.restSchemaAnswerFieldMockMvc = MockMvcBuilders.standaloneSetup(schemaAnswerFieldResource)
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
    public static SchemaAnswerField createEntity(EntityManager em) {
        SchemaAnswerField schemaAnswerField = new SchemaAnswerField()
            .value(DEFAULT_VALUE);
        return schemaAnswerField;
    }

    @Before
    public void initTest() {
        schemaAnswerField = createEntity(em);
    }

    @Test
    @Transactional
    public void createSchemaAnswerField() throws Exception {
        int databaseSizeBeforeCreate = schemaAnswerFieldRepository.findAll().size();

        // Create the SchemaAnswerField
        restSchemaAnswerFieldMockMvc.perform(post("/api/schema-answer-fields")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schemaAnswerField)))
            .andExpect(status().isCreated());

        // Validate the SchemaAnswerField in the database
        List<SchemaAnswerField> schemaAnswerFieldList = schemaAnswerFieldRepository.findAll();
        assertThat(schemaAnswerFieldList).hasSize(databaseSizeBeforeCreate + 1);
        SchemaAnswerField testSchemaAnswerField = schemaAnswerFieldList.get(schemaAnswerFieldList.size() - 1);
        assertThat(testSchemaAnswerField.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createSchemaAnswerFieldWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = schemaAnswerFieldRepository.findAll().size();

        // Create the SchemaAnswerField with an existing ID
        schemaAnswerField.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSchemaAnswerFieldMockMvc.perform(post("/api/schema-answer-fields")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schemaAnswerField)))
            .andExpect(status().isBadRequest());

        // Validate the SchemaAnswerField in the database
        List<SchemaAnswerField> schemaAnswerFieldList = schemaAnswerFieldRepository.findAll();
        assertThat(schemaAnswerFieldList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSchemaAnswerFields() throws Exception {
        // Initialize the database
        schemaAnswerFieldRepository.saveAndFlush(schemaAnswerField);

        // Get all the schemaAnswerFieldList
        restSchemaAnswerFieldMockMvc.perform(get("/api/schema-answer-fields?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(schemaAnswerField.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }

    @Test
    @Transactional
    public void getSchemaAnswerField() throws Exception {
        // Initialize the database
        schemaAnswerFieldRepository.saveAndFlush(schemaAnswerField);

        // Get the schemaAnswerField
        restSchemaAnswerFieldMockMvc.perform(get("/api/schema-answer-fields/{id}", schemaAnswerField.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(schemaAnswerField.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSchemaAnswerField() throws Exception {
        // Get the schemaAnswerField
        restSchemaAnswerFieldMockMvc.perform(get("/api/schema-answer-fields/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSchemaAnswerField() throws Exception {
        // Initialize the database
        schemaAnswerFieldService.save(schemaAnswerField);

        int databaseSizeBeforeUpdate = schemaAnswerFieldRepository.findAll().size();

        // Update the schemaAnswerField
        SchemaAnswerField updatedSchemaAnswerField = schemaAnswerFieldRepository.findOne(schemaAnswerField.getId());
        // Disconnect from session so that the updates on updatedSchemaAnswerField are not directly saved in db
        em.detach(updatedSchemaAnswerField);
        updatedSchemaAnswerField
            .value(UPDATED_VALUE);

        restSchemaAnswerFieldMockMvc.perform(put("/api/schema-answer-fields")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSchemaAnswerField)))
            .andExpect(status().isOk());

        // Validate the SchemaAnswerField in the database
        List<SchemaAnswerField> schemaAnswerFieldList = schemaAnswerFieldRepository.findAll();
        assertThat(schemaAnswerFieldList).hasSize(databaseSizeBeforeUpdate);
        SchemaAnswerField testSchemaAnswerField = schemaAnswerFieldList.get(schemaAnswerFieldList.size() - 1);
        assertThat(testSchemaAnswerField.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingSchemaAnswerField() throws Exception {
        int databaseSizeBeforeUpdate = schemaAnswerFieldRepository.findAll().size();

        // Create the SchemaAnswerField

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSchemaAnswerFieldMockMvc.perform(put("/api/schema-answer-fields")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schemaAnswerField)))
            .andExpect(status().isCreated());

        // Validate the SchemaAnswerField in the database
        List<SchemaAnswerField> schemaAnswerFieldList = schemaAnswerFieldRepository.findAll();
        assertThat(schemaAnswerFieldList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSchemaAnswerField() throws Exception {
        // Initialize the database
        schemaAnswerFieldService.save(schemaAnswerField);

        int databaseSizeBeforeDelete = schemaAnswerFieldRepository.findAll().size();

        // Get the schemaAnswerField
        restSchemaAnswerFieldMockMvc.perform(delete("/api/schema-answer-fields/{id}", schemaAnswerField.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SchemaAnswerField> schemaAnswerFieldList = schemaAnswerFieldRepository.findAll();
        assertThat(schemaAnswerFieldList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SchemaAnswerField.class);
        SchemaAnswerField schemaAnswerField1 = new SchemaAnswerField();
        schemaAnswerField1.setId(1L);
        SchemaAnswerField schemaAnswerField2 = new SchemaAnswerField();
        schemaAnswerField2.setId(schemaAnswerField1.getId());
        assertThat(schemaAnswerField1).isEqualTo(schemaAnswerField2);
        schemaAnswerField2.setId(2L);
        assertThat(schemaAnswerField1).isNotEqualTo(schemaAnswerField2);
        schemaAnswerField1.setId(null);
        assertThat(schemaAnswerField1).isNotEqualTo(schemaAnswerField2);
    }
}

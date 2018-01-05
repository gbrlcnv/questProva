package it.ec.quest.web.rest;

import it.ec.quest.QuestProvaApp;

import it.ec.quest.domain.SchemaField;
import it.ec.quest.repository.SchemaFieldRepository;
import it.ec.quest.service.SchemaFieldService;
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

import it.ec.quest.domain.enumeration.TypeEnum;
/**
 * Test class for the SchemaFieldResource REST controller.
 *
 * @see SchemaFieldResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = QuestProvaApp.class)
public class SchemaFieldResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_ORDER = 1;
    private static final Integer UPDATED_ORDER = 2;

    private static final TypeEnum DEFAULT_TYPE = TypeEnum.CHAR;
    private static final TypeEnum UPDATED_TYPE = TypeEnum.INTG;

    private static final String DEFAULT_VALUE_LIST_CODE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE_LIST_CODE = "BBBBBBBBBB";

    @Autowired
    private SchemaFieldRepository schemaFieldRepository;

    @Autowired
    private SchemaFieldService schemaFieldService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSchemaFieldMockMvc;

    private SchemaField schemaField;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SchemaFieldResource schemaFieldResource = new SchemaFieldResource(schemaFieldService);
        this.restSchemaFieldMockMvc = MockMvcBuilders.standaloneSetup(schemaFieldResource)
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
    public static SchemaField createEntity(EntityManager em) {
        SchemaField schemaField = new SchemaField()
            .code(DEFAULT_CODE)
            .description(DEFAULT_DESCRIPTION)
            .order(DEFAULT_ORDER)
            .type(DEFAULT_TYPE)
            .valueListCode(DEFAULT_VALUE_LIST_CODE);
        return schemaField;
    }

    @Before
    public void initTest() {
        schemaField = createEntity(em);
    }

    @Test
    @Transactional
    public void createSchemaField() throws Exception {
        int databaseSizeBeforeCreate = schemaFieldRepository.findAll().size();

        // Create the SchemaField
        restSchemaFieldMockMvc.perform(post("/api/schema-fields")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schemaField)))
            .andExpect(status().isCreated());

        // Validate the SchemaField in the database
        List<SchemaField> schemaFieldList = schemaFieldRepository.findAll();
        assertThat(schemaFieldList).hasSize(databaseSizeBeforeCreate + 1);
        SchemaField testSchemaField = schemaFieldList.get(schemaFieldList.size() - 1);
        assertThat(testSchemaField.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testSchemaField.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSchemaField.getOrder()).isEqualTo(DEFAULT_ORDER);
        assertThat(testSchemaField.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testSchemaField.getValueListCode()).isEqualTo(DEFAULT_VALUE_LIST_CODE);
    }

    @Test
    @Transactional
    public void createSchemaFieldWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = schemaFieldRepository.findAll().size();

        // Create the SchemaField with an existing ID
        schemaField.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSchemaFieldMockMvc.perform(post("/api/schema-fields")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schemaField)))
            .andExpect(status().isBadRequest());

        // Validate the SchemaField in the database
        List<SchemaField> schemaFieldList = schemaFieldRepository.findAll();
        assertThat(schemaFieldList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = schemaFieldRepository.findAll().size();
        // set the field null
        schemaField.setCode(null);

        // Create the SchemaField, which fails.

        restSchemaFieldMockMvc.perform(post("/api/schema-fields")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schemaField)))
            .andExpect(status().isBadRequest());

        List<SchemaField> schemaFieldList = schemaFieldRepository.findAll();
        assertThat(schemaFieldList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = schemaFieldRepository.findAll().size();
        // set the field null
        schemaField.setDescription(null);

        // Create the SchemaField, which fails.

        restSchemaFieldMockMvc.perform(post("/api/schema-fields")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schemaField)))
            .andExpect(status().isBadRequest());

        List<SchemaField> schemaFieldList = schemaFieldRepository.findAll();
        assertThat(schemaFieldList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOrderIsRequired() throws Exception {
        int databaseSizeBeforeTest = schemaFieldRepository.findAll().size();
        // set the field null
        schemaField.setOrder(null);

        // Create the SchemaField, which fails.

        restSchemaFieldMockMvc.perform(post("/api/schema-fields")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schemaField)))
            .andExpect(status().isBadRequest());

        List<SchemaField> schemaFieldList = schemaFieldRepository.findAll();
        assertThat(schemaFieldList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = schemaFieldRepository.findAll().size();
        // set the field null
        schemaField.setType(null);

        // Create the SchemaField, which fails.

        restSchemaFieldMockMvc.perform(post("/api/schema-fields")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schemaField)))
            .andExpect(status().isBadRequest());

        List<SchemaField> schemaFieldList = schemaFieldRepository.findAll();
        assertThat(schemaFieldList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSchemaFields() throws Exception {
        // Initialize the database
        schemaFieldRepository.saveAndFlush(schemaField);

        // Get all the schemaFieldList
        restSchemaFieldMockMvc.perform(get("/api/schema-fields?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(schemaField.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].valueListCode").value(hasItem(DEFAULT_VALUE_LIST_CODE.toString())));
    }

    @Test
    @Transactional
    public void getSchemaField() throws Exception {
        // Initialize the database
        schemaFieldRepository.saveAndFlush(schemaField);

        // Get the schemaField
        restSchemaFieldMockMvc.perform(get("/api/schema-fields/{id}", schemaField.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(schemaField.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.order").value(DEFAULT_ORDER))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.valueListCode").value(DEFAULT_VALUE_LIST_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSchemaField() throws Exception {
        // Get the schemaField
        restSchemaFieldMockMvc.perform(get("/api/schema-fields/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSchemaField() throws Exception {
        // Initialize the database
        schemaFieldService.save(schemaField);

        int databaseSizeBeforeUpdate = schemaFieldRepository.findAll().size();

        // Update the schemaField
        SchemaField updatedSchemaField = schemaFieldRepository.findOne(schemaField.getId());
        // Disconnect from session so that the updates on updatedSchemaField are not directly saved in db
        em.detach(updatedSchemaField);
        updatedSchemaField
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .order(UPDATED_ORDER)
            .type(UPDATED_TYPE)
            .valueListCode(UPDATED_VALUE_LIST_CODE);

        restSchemaFieldMockMvc.perform(put("/api/schema-fields")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSchemaField)))
            .andExpect(status().isOk());

        // Validate the SchemaField in the database
        List<SchemaField> schemaFieldList = schemaFieldRepository.findAll();
        assertThat(schemaFieldList).hasSize(databaseSizeBeforeUpdate);
        SchemaField testSchemaField = schemaFieldList.get(schemaFieldList.size() - 1);
        assertThat(testSchemaField.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSchemaField.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSchemaField.getOrder()).isEqualTo(UPDATED_ORDER);
        assertThat(testSchemaField.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testSchemaField.getValueListCode()).isEqualTo(UPDATED_VALUE_LIST_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingSchemaField() throws Exception {
        int databaseSizeBeforeUpdate = schemaFieldRepository.findAll().size();

        // Create the SchemaField

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSchemaFieldMockMvc.perform(put("/api/schema-fields")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schemaField)))
            .andExpect(status().isCreated());

        // Validate the SchemaField in the database
        List<SchemaField> schemaFieldList = schemaFieldRepository.findAll();
        assertThat(schemaFieldList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSchemaField() throws Exception {
        // Initialize the database
        schemaFieldService.save(schemaField);

        int databaseSizeBeforeDelete = schemaFieldRepository.findAll().size();

        // Get the schemaField
        restSchemaFieldMockMvc.perform(delete("/api/schema-fields/{id}", schemaField.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SchemaField> schemaFieldList = schemaFieldRepository.findAll();
        assertThat(schemaFieldList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SchemaField.class);
        SchemaField schemaField1 = new SchemaField();
        schemaField1.setId(1L);
        SchemaField schemaField2 = new SchemaField();
        schemaField2.setId(schemaField1.getId());
        assertThat(schemaField1).isEqualTo(schemaField2);
        schemaField2.setId(2L);
        assertThat(schemaField1).isNotEqualTo(schemaField2);
        schemaField1.setId(null);
        assertThat(schemaField1).isNotEqualTo(schemaField2);
    }
}

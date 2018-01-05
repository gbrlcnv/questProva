package it.ec.quest.web.rest;

import it.ec.quest.QuestProvaApp;

import it.ec.quest.domain.Schema1;
import it.ec.quest.repository.Schema1Repository;
import it.ec.quest.service.Schema1Service;
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
 * Test class for the Schema1Resource REST controller.
 *
 * @see Schema1Resource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = QuestProvaApp.class)
public class Schema1ResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_VERSION = 1;
    private static final Integer UPDATED_VERSION = 2;

    private static final ZonedDateTime DEFAULT_VERSION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_VERSION_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private Schema1Repository schema1Repository;

    @Autowired
    private Schema1Service schema1Service;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSchema1MockMvc;

    private Schema1 schema1;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final Schema1Resource schema1Resource = new Schema1Resource(schema1Service);
        this.restSchema1MockMvc = MockMvcBuilders.standaloneSetup(schema1Resource)
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
    public static Schema1 createEntity(EntityManager em) {
        Schema1 schema1 = new Schema1()
            .code(DEFAULT_CODE)
            .version(DEFAULT_VERSION)
            .versionDate(DEFAULT_VERSION_DATE)
            .description(DEFAULT_DESCRIPTION);
        return schema1;
    }

    @Before
    public void initTest() {
        schema1 = createEntity(em);
    }

    @Test
    @Transactional
    public void createSchema1() throws Exception {
        int databaseSizeBeforeCreate = schema1Repository.findAll().size();

        // Create the Schema1
        restSchema1MockMvc.perform(post("/api/schema-1-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schema1)))
            .andExpect(status().isCreated());

        // Validate the Schema1 in the database
        List<Schema1> schema1List = schema1Repository.findAll();
        assertThat(schema1List).hasSize(databaseSizeBeforeCreate + 1);
        Schema1 testSchema1 = schema1List.get(schema1List.size() - 1);
        assertThat(testSchema1.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testSchema1.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testSchema1.getVersionDate()).isEqualTo(DEFAULT_VERSION_DATE);
        assertThat(testSchema1.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createSchema1WithExistingId() throws Exception {
        int databaseSizeBeforeCreate = schema1Repository.findAll().size();

        // Create the Schema1 with an existing ID
        schema1.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSchema1MockMvc.perform(post("/api/schema-1-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schema1)))
            .andExpect(status().isBadRequest());

        // Validate the Schema1 in the database
        List<Schema1> schema1List = schema1Repository.findAll();
        assertThat(schema1List).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = schema1Repository.findAll().size();
        // set the field null
        schema1.setCode(null);

        // Create the Schema1, which fails.

        restSchema1MockMvc.perform(post("/api/schema-1-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schema1)))
            .andExpect(status().isBadRequest());

        List<Schema1> schema1List = schema1Repository.findAll();
        assertThat(schema1List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = schema1Repository.findAll().size();
        // set the field null
        schema1.setVersion(null);

        // Create the Schema1, which fails.

        restSchema1MockMvc.perform(post("/api/schema-1-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schema1)))
            .andExpect(status().isBadRequest());

        List<Schema1> schema1List = schema1Repository.findAll();
        assertThat(schema1List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVersionDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = schema1Repository.findAll().size();
        // set the field null
        schema1.setVersionDate(null);

        // Create the Schema1, which fails.

        restSchema1MockMvc.perform(post("/api/schema-1-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schema1)))
            .andExpect(status().isBadRequest());

        List<Schema1> schema1List = schema1Repository.findAll();
        assertThat(schema1List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = schema1Repository.findAll().size();
        // set the field null
        schema1.setDescription(null);

        // Create the Schema1, which fails.

        restSchema1MockMvc.perform(post("/api/schema-1-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schema1)))
            .andExpect(status().isBadRequest());

        List<Schema1> schema1List = schema1Repository.findAll();
        assertThat(schema1List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSchema1S() throws Exception {
        // Initialize the database
        schema1Repository.saveAndFlush(schema1);

        // Get all the schema1List
        restSchema1MockMvc.perform(get("/api/schema-1-s?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(schema1.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)))
            .andExpect(jsonPath("$.[*].versionDate").value(hasItem(sameInstant(DEFAULT_VERSION_DATE))))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getSchema1() throws Exception {
        // Initialize the database
        schema1Repository.saveAndFlush(schema1);

        // Get the schema1
        restSchema1MockMvc.perform(get("/api/schema-1-s/{id}", schema1.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(schema1.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION))
            .andExpect(jsonPath("$.versionDate").value(sameInstant(DEFAULT_VERSION_DATE)))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSchema1() throws Exception {
        // Get the schema1
        restSchema1MockMvc.perform(get("/api/schema-1-s/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSchema1() throws Exception {
        // Initialize the database
        schema1Service.save(schema1);

        int databaseSizeBeforeUpdate = schema1Repository.findAll().size();

        // Update the schema1
        Schema1 updatedSchema1 = schema1Repository.findOne(schema1.getId());
        // Disconnect from session so that the updates on updatedSchema1 are not directly saved in db
        em.detach(updatedSchema1);
        updatedSchema1
            .code(UPDATED_CODE)
            .version(UPDATED_VERSION)
            .versionDate(UPDATED_VERSION_DATE)
            .description(UPDATED_DESCRIPTION);

        restSchema1MockMvc.perform(put("/api/schema-1-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSchema1)))
            .andExpect(status().isOk());

        // Validate the Schema1 in the database
        List<Schema1> schema1List = schema1Repository.findAll();
        assertThat(schema1List).hasSize(databaseSizeBeforeUpdate);
        Schema1 testSchema1 = schema1List.get(schema1List.size() - 1);
        assertThat(testSchema1.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSchema1.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testSchema1.getVersionDate()).isEqualTo(UPDATED_VERSION_DATE);
        assertThat(testSchema1.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingSchema1() throws Exception {
        int databaseSizeBeforeUpdate = schema1Repository.findAll().size();

        // Create the Schema1

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSchema1MockMvc.perform(put("/api/schema-1-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schema1)))
            .andExpect(status().isCreated());

        // Validate the Schema1 in the database
        List<Schema1> schema1List = schema1Repository.findAll();
        assertThat(schema1List).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSchema1() throws Exception {
        // Initialize the database
        schema1Service.save(schema1);

        int databaseSizeBeforeDelete = schema1Repository.findAll().size();

        // Get the schema1
        restSchema1MockMvc.perform(delete("/api/schema-1-s/{id}", schema1.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Schema1> schema1List = schema1Repository.findAll();
        assertThat(schema1List).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Schema1.class);
        Schema1 schema11 = new Schema1();
        schema11.setId(1L);
        Schema1 schema12 = new Schema1();
        schema12.setId(schema11.getId());
        assertThat(schema11).isEqualTo(schema12);
        schema12.setId(2L);
        assertThat(schema11).isNotEqualTo(schema12);
        schema11.setId(null);
        assertThat(schema11).isNotEqualTo(schema12);
    }
}

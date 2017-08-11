package io.cisa.taxiiserver.web.rest;

import static io.cisa.taxiiserver.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

import io.cisa.taxiiserver.JTaxiiServerApp;
import io.cisa.taxiiserver.domain.Discovery;
import io.cisa.taxiiserver.repository.DiscoveryRepository;
import io.cisa.taxiiserver.web.rest.DiscoveryResource;
import io.cisa.taxiiserver.web.rest.errors.ExceptionTranslator;

/**
 * Test class for the DiscoveryResource REST controller.
 *
 * @see DiscoveryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JTaxiiServerApp.class)
public class DiscoveryResourceIntTest {

    private static final String DEFAULT_DISPLAY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DISPLAY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT = "BBBBBBBBBB";

    private static final String DEFAULT_DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_DEFAULT_URL = "BBBBBBBBBB";

    private static final Set<String> DEFAULT_API_ROOTS = new HashSet<>();
    private static final Set<String> UPDATED_API_ROOTS = new HashSet<>();

    private static final ZonedDateTime DEFAULT_LAST_MODIFIED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_MODIFIED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private DiscoveryRepository discoveryRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restDiscoveryMockMvc;

    private Discovery discovery;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DiscoveryResource discoveryResource = new DiscoveryResource(discoveryRepository);
        this.restDiscoveryMockMvc = MockMvcBuilders.standaloneSetup(discoveryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Discovery createEntity() {
        Discovery discovery = new Discovery()
            .displayName(DEFAULT_DISPLAY_NAME)
            .description(DEFAULT_DESCRIPTION)
            .contact(DEFAULT_CONTACT)
            .defaultURL(DEFAULT_DEFAULT_URL)
            .apiRoots(DEFAULT_API_ROOTS)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .createdDate(DEFAULT_CREATED_DATE);
        return discovery;
    }

    @Before
    public void initTest() {
        discoveryRepository.deleteAll();
        discovery = createEntity();
    }

    @Test
    public void createDiscovery() throws Exception {
        int databaseSizeBeforeCreate = discoveryRepository.findAll().size();

        // Create the Discovery
        restDiscoveryMockMvc.perform(post("/api/discoveries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(discovery)))
            .andExpect(status().isCreated());

        // Validate the Discovery in the database
        List<Discovery> discoveryList = discoveryRepository.findAll();
        assertThat(discoveryList).hasSize(databaseSizeBeforeCreate + 1);
        Discovery testDiscovery = discoveryList.get(discoveryList.size() - 1);
        assertThat(testDiscovery.getDisplayName()).isEqualTo(DEFAULT_DISPLAY_NAME);
        assertThat(testDiscovery.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDiscovery.getContact()).isEqualTo(DEFAULT_CONTACT);
        assertThat(testDiscovery.getDefaultURL()).isEqualTo(DEFAULT_DEFAULT_URL);
        assertThat(testDiscovery.getApiRoots()).isEqualTo(DEFAULT_API_ROOTS);
        assertThat(testDiscovery.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testDiscovery.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    public void createDiscoveryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = discoveryRepository.findAll().size();

        // Create the Discovery with an existing ID
        discovery.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restDiscoveryMockMvc.perform(post("/api/discoveries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(discovery)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Discovery> discoveryList = discoveryRepository.findAll();
        assertThat(discoveryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkContactIsRequired() throws Exception {
        int databaseSizeBeforeTest = discoveryRepository.findAll().size();
        // set the field null
        discovery.setContact(null);

        // Create the Discovery, which fails.

        restDiscoveryMockMvc.perform(post("/api/discoveries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(discovery)))
            .andExpect(status().isBadRequest());

        List<Discovery> discoveryList = discoveryRepository.findAll();
        assertThat(discoveryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkDefaultURLIsRequired() throws Exception {
        int databaseSizeBeforeTest = discoveryRepository.findAll().size();
        // set the field null
        discovery.setDefaultURL(null);

        // Create the Discovery, which fails.

        restDiscoveryMockMvc.perform(post("/api/discoveries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(discovery)))
            .andExpect(status().isBadRequest());

        List<Discovery> discoveryList = discoveryRepository.findAll();
        assertThat(discoveryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllDiscoveries() throws Exception {
        // Initialize the database
        discoveryRepository.save(discovery);

        // Get all the discoveryList
        restDiscoveryMockMvc.perform(get("/api/discoveries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(discovery.getId())))
            .andExpect(jsonPath("$.[*].displayName").value(hasItem(DEFAULT_DISPLAY_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].contact").value(hasItem(DEFAULT_CONTACT.toString())))
            .andExpect(jsonPath("$.[*].defaultURL").value(hasItem(DEFAULT_DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].apiRoots").value(hasItem(DEFAULT_API_ROOTS.toString())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(sameInstant(DEFAULT_LAST_MODIFIED))))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))));
    }

    @Test
    public void getDiscovery() throws Exception {
        // Initialize the database
        discoveryRepository.save(discovery);

        // Get the discovery
        restDiscoveryMockMvc.perform(get("/api/discoveries/{id}", discovery.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(discovery.getId()))
            .andExpect(jsonPath("$.displayName").value(DEFAULT_DISPLAY_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.contact").value(DEFAULT_CONTACT.toString()))
            .andExpect(jsonPath("$.defaultURL").value(DEFAULT_DEFAULT_URL.toString()))
            .andExpect(jsonPath("$.apiRoots").value(DEFAULT_API_ROOTS.toString()))
            .andExpect(jsonPath("$.lastModified").value(sameInstant(DEFAULT_LAST_MODIFIED)))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)));
    }

    @Test
    public void getNonExistingDiscovery() throws Exception {
        // Get the discovery
        restDiscoveryMockMvc.perform(get("/api/discoveries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateDiscovery() throws Exception {
        // Initialize the database
        discoveryRepository.save(discovery);
        int databaseSizeBeforeUpdate = discoveryRepository.findAll().size();

        // Update the discovery
        Discovery updatedDiscovery = discoveryRepository.findOne(discovery.getId());
        updatedDiscovery
            .displayName(UPDATED_DISPLAY_NAME)
            .description(UPDATED_DESCRIPTION)
            .contact(UPDATED_CONTACT)
            .defaultURL(UPDATED_DEFAULT_URL)
            .apiRoots(UPDATED_API_ROOTS)
            .lastModified(UPDATED_LAST_MODIFIED)
            .createdDate(UPDATED_CREATED_DATE);

        restDiscoveryMockMvc.perform(put("/api/discoveries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDiscovery)))
            .andExpect(status().isOk());

        // Validate the Discovery in the database
        List<Discovery> discoveryList = discoveryRepository.findAll();
        assertThat(discoveryList).hasSize(databaseSizeBeforeUpdate);
        Discovery testDiscovery = discoveryList.get(discoveryList.size() - 1);
        assertThat(testDiscovery.getDisplayName()).isEqualTo(UPDATED_DISPLAY_NAME);
        assertThat(testDiscovery.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDiscovery.getContact()).isEqualTo(UPDATED_CONTACT);
        assertThat(testDiscovery.getDefaultURL()).isEqualTo(UPDATED_DEFAULT_URL);
        assertThat(testDiscovery.getApiRoots()).isEqualTo(UPDATED_API_ROOTS);
        assertThat(testDiscovery.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testDiscovery.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    public void updateNonExistingDiscovery() throws Exception {
        int databaseSizeBeforeUpdate = discoveryRepository.findAll().size();

        // Create the Discovery

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDiscoveryMockMvc.perform(put("/api/discoveries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(discovery)))
            .andExpect(status().isCreated());

        // Validate the Discovery in the database
        List<Discovery> discoveryList = discoveryRepository.findAll();
        assertThat(discoveryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteDiscovery() throws Exception {
        // Initialize the database
        discoveryRepository.save(discovery);
        int databaseSizeBeforeDelete = discoveryRepository.findAll().size();

        // Get the discovery
        restDiscoveryMockMvc.perform(delete("/api/discoveries/{id}", discovery.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Discovery> discoveryList = discoveryRepository.findAll();
        assertThat(discoveryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Discovery.class);
        Discovery discovery1 = new Discovery();
        discovery1.setId("id1");
        Discovery discovery2 = new Discovery();
        discovery2.setId(discovery1.getId());
        assertThat(discovery1).isEqualTo(discovery2);
        discovery2.setId("id2");
        assertThat(discovery1).isNotEqualTo(discovery2);
        discovery1.setId(null);
        assertThat(discovery1).isNotEqualTo(discovery2);
    }
}

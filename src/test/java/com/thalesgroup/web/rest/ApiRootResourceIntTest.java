package com.thalesgroup.web.rest;

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

import com.thalesgroup.taxiiserver.JTaxiiServerApp;
import com.thalesgroup.taxiiserver.domain.ApiRoot;
import com.thalesgroup.taxiiserver.domain.Channel;
import com.thalesgroup.taxiiserver.domain.Collection;
import com.thalesgroup.taxiiserver.repository.ApiRootRepository;
import com.thalesgroup.taxiiserver.web.rest.ApiRootResource;
import com.thalesgroup.taxiiserver.web.rest.errors.ExceptionTranslator;

/**
 * Test class for the ApiRootResource REST controller.
 *
 * @see ApiRootResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JTaxiiServerApp.class)
public class ApiRootResourceIntTest {

    private static final String DEFAULT_DISPLAY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DISPLAY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Set<String> DEFAULT_VERSIONS = new HashSet<>();
    private static final Set<String> UPDATED_VERSIONS = new HashSet<>();

    private static final Set<Channel> DEFAULT_CHANNELS = new HashSet<>();
    private static final Set<Channel> UPDATED_CHANNELS = new HashSet<>();

    private static final Set<Collection> DEFAULT_COLLECTIONS = new HashSet<>();
    private static final Set<Collection> UPDATED_COLLECTIONS = new HashSet<>();

    private static final Long DEFAULT_MAX_CONTENT_LENGTH = 1L;
    private static final Long UPDATED_MAX_CONTENT_LENGTH = 2L;

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_LAST_MODIFIED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_MODIFIED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);


    @Autowired
    private ApiRootRepository apiRootRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restApiRootMockMvc;

    private ApiRoot apiRoot;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ApiRootResource apiRootResource = new ApiRootResource(apiRootRepository);
        this.restApiRootMockMvc = MockMvcBuilders.standaloneSetup(apiRootResource)
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
    public static ApiRoot createEntity() {
        ApiRoot apiRoot = new ApiRoot()
            .displayName(DEFAULT_DISPLAY_NAME)
            .description(DEFAULT_DESCRIPTION)
            .versions(DEFAULT_VERSIONS)
            .channels(DEFAULT_CHANNELS)
            .collections(DEFAULT_COLLECTIONS)
            .maxContentLength(DEFAULT_MAX_CONTENT_LENGTH)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return apiRoot;
    }

    @Before
    public void initTest() {
        apiRootRepository.deleteAll();
        apiRoot = createEntity();
    }

    @Test
    public void createApiRoot() throws Exception {
        int databaseSizeBeforeCreate = apiRootRepository.findAll().size();

        // Create the ApiRoot
        restApiRootMockMvc.perform(post("/api/api-roots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiRoot)))
            .andExpect(status().isCreated());

        // Validate the ApiRoot in the database
        List<ApiRoot> apiRootList = apiRootRepository.findAll();
        assertThat(apiRootList).hasSize(databaseSizeBeforeCreate + 1);
        ApiRoot testApiRoot = apiRootList.get(apiRootList.size() - 1);
        assertThat(testApiRoot.getDisplayName()).isEqualTo(DEFAULT_DISPLAY_NAME);
        assertThat(testApiRoot.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testApiRoot.getVersions()).isEqualTo(DEFAULT_VERSIONS);
        assertThat(testApiRoot.getChannels()).isEqualTo(DEFAULT_CHANNELS);
        assertThat(testApiRoot.getCollections()).isEqualTo(DEFAULT_COLLECTIONS);
        assertThat(testApiRoot.getMaxContentLength()).isEqualTo(DEFAULT_MAX_CONTENT_LENGTH);
        assertThat(testApiRoot.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testApiRoot.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    public void createApiRootWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = apiRootRepository.findAll().size();

        // Create the ApiRoot with an existing ID
        apiRoot.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restApiRootMockMvc.perform(post("/api/api-roots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiRoot)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ApiRoot> apiRootList = apiRootRepository.findAll();
        assertThat(apiRootList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkVersionsIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiRootRepository.findAll().size();
        // set the field null
        apiRoot.setVersions(null);

        // Create the ApiRoot, which fails.

        restApiRootMockMvc.perform(post("/api/api-roots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiRoot)))
            .andExpect(status().isBadRequest());

        List<ApiRoot> apiRootList = apiRootRepository.findAll();
        assertThat(apiRootList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllApiRoots() throws Exception {
        // Initialize the database
        apiRootRepository.save(apiRoot);

        // Get all the apiRootList
        restApiRootMockMvc.perform(get("/api/api-roots?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(apiRoot.getId())))
            .andExpect(jsonPath("$.[*].displayName").value(hasItem(DEFAULT_DISPLAY_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].versions").value(hasItem(DEFAULT_VERSIONS.toString())))
            .andExpect(jsonPath("$.[*].channels").value(hasItem(DEFAULT_CHANNELS.toString())))
            .andExpect(jsonPath("$.[*].collections").value(hasItem(DEFAULT_COLLECTIONS.toString())))
            .andExpect(jsonPath("$.[*].maxContentLength").value(hasItem(DEFAULT_MAX_CONTENT_LENGTH.intValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem((DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem((DEFAULT_LAST_MODIFIED_DATE))));
    }

    @Test
    public void getApiRoot() throws Exception {
        // Initialize the database
        apiRootRepository.save(apiRoot);

        // Get the apiRoot
        restApiRootMockMvc.perform(get("/api/api-roots/{id}", apiRoot.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(apiRoot.getId()))
            .andExpect(jsonPath("$.displayName").value(DEFAULT_DISPLAY_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.versions").value(DEFAULT_VERSIONS.toString()))
            .andExpect(jsonPath("$.channels").value(DEFAULT_CHANNELS.toString()))
            .andExpect(jsonPath("$.collections").value(DEFAULT_COLLECTIONS.toString()))
            .andExpect(jsonPath("$.maxContentLength").value(DEFAULT_MAX_CONTENT_LENGTH.intValue()))
            .andExpect(jsonPath("$.createdDate").value((DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.lastModifiedDate").value((DEFAULT_LAST_MODIFIED_DATE)));
    }

    @Test
    public void getNonExistingApiRoot() throws Exception {
        // Get the apiRoot
        restApiRootMockMvc.perform(get("/api/api-roots/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateApiRoot() throws Exception {
        // Initialize the database
        apiRootRepository.save(apiRoot);
        int databaseSizeBeforeUpdate = apiRootRepository.findAll().size();

        // Update the apiRoot
        ApiRoot updatedApiRoot = apiRootRepository.findOne(apiRoot.getId());
        updatedApiRoot
            .displayName(UPDATED_DISPLAY_NAME)
            .description(UPDATED_DESCRIPTION)
            .versions(UPDATED_VERSIONS)
            .channels(UPDATED_CHANNELS)
            .collections(UPDATED_COLLECTIONS)
            .maxContentLength(UPDATED_MAX_CONTENT_LENGTH)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restApiRootMockMvc.perform(put("/api/api-roots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedApiRoot)))
            .andExpect(status().isOk());

        // Validate the ApiRoot in the database
        List<ApiRoot> apiRootList = apiRootRepository.findAll();
        assertThat(apiRootList).hasSize(databaseSizeBeforeUpdate);
        ApiRoot testApiRoot = apiRootList.get(apiRootList.size() - 1);
        assertThat(testApiRoot.getDisplayName()).isEqualTo(UPDATED_DISPLAY_NAME);
        assertThat(testApiRoot.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testApiRoot.getVersions()).isEqualTo(UPDATED_VERSIONS);
        assertThat(testApiRoot.getChannels()).isEqualTo(UPDATED_CHANNELS);
        assertThat(testApiRoot.getCollections()).isEqualTo(UPDATED_COLLECTIONS);
        assertThat(testApiRoot.getMaxContentLength()).isEqualTo(UPDATED_MAX_CONTENT_LENGTH);
        assertThat(testApiRoot.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testApiRoot.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    public void updateNonExistingApiRoot() throws Exception {
        int databaseSizeBeforeUpdate = apiRootRepository.findAll().size();

        // Create the ApiRoot

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restApiRootMockMvc.perform(put("/api/api-roots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiRoot)))
            .andExpect(status().isCreated());

        // Validate the ApiRoot in the database
        List<ApiRoot> apiRootList = apiRootRepository.findAll();
        assertThat(apiRootList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteApiRoot() throws Exception {
        // Initialize the database
        apiRootRepository.save(apiRoot);
        int databaseSizeBeforeDelete = apiRootRepository.findAll().size();

        // Get the apiRoot
        restApiRootMockMvc.perform(delete("/api/api-roots/{id}", apiRoot.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ApiRoot> apiRootList = apiRootRepository.findAll();
        assertThat(apiRootList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApiRoot.class);
        ApiRoot apiRoot1 = new ApiRoot();
        apiRoot1.setId("id1");
        ApiRoot apiRoot2 = new ApiRoot();
        apiRoot2.setId(apiRoot1.getId());
        assertThat(apiRoot1).isEqualTo(apiRoot2);
        apiRoot2.setId("id2");
        assertThat(apiRoot1).isNotEqualTo(apiRoot2);
        apiRoot1.setId(null);
        assertThat(apiRoot1).isNotEqualTo(apiRoot2);
    }
}

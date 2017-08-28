package io.cisa.taxiiserver.web.rest;

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
import java.time.temporal.ChronoUnit;
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
import io.cisa.taxiiserver.domain.status.Status;
import io.cisa.taxiiserver.domain.status.StatusFailure;
import io.cisa.taxiiserver.domain.status.StatusSuccess;
import io.cisa.taxiiserver.repository.StatusRepository;
import io.cisa.taxiiserver.web.rest.errors.ExceptionTranslator;

/**
 * Test class for the StatusResource REST controller.
 *
 * @see StatusResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JTaxiiServerApp.class)
public class StatusResourceIntTest {

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_REQUEST_URL = "AAAAAAAAAA";
    private static final String UPDATED_REQUEST_URL = "BBBBBBBBBB";

    private static final Instant DEFAULT_REQUEST_TIMESTAMP = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_REQUEST_TIMESTAMP = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_TOTAL_OBJECTS = 1;
    private static final Integer UPDATED_TOTAL_OBJECTS = 2;

    private static final Integer DEFAULT_SUCCESS_COUNT = 1;
    private static final Integer UPDATED_SUCCESS_COUNT = 2;

    private static final Set<StatusSuccess> DEFAULT_SUCCESSES = new HashSet<>();
    private static final Set<StatusSuccess> UPDATED_SUCCESSES = new HashSet<>();

    private static final Integer DEFAULT_FAILURE_COUNT = 1;
    private static final Integer UPDATED_FAILURE_COUNT = 2;

    private static final Set<StatusFailure> DEFAULT_FAILURES = new HashSet<>();
    private static final Set<StatusFailure> UPDATED_FAILURES = new HashSet<>();

    private static final Integer DEFAULT_PENDING_COUNT = 1;
    private static final Integer UPDATED_PENDING_COUNT = 2;

    private static final Set<String> DEFAULT_PENDINGS = new HashSet<>();
    private static final Set<String> UPDATED_PENDINGS = new HashSet<>();

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restStatusMockMvc;

    private Status status;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        StatusResource statusResource = new StatusResource(statusRepository);
        this.restStatusMockMvc = MockMvcBuilders.standaloneSetup(statusResource)
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
    public static Status createEntity() {
        Status status = new Status()
            .url(DEFAULT_URL)
            .status(DEFAULT_STATUS)
            .requestUrl(DEFAULT_REQUEST_URL)
            .requestTimestamp(DEFAULT_REQUEST_TIMESTAMP)
            .totalObjects(DEFAULT_TOTAL_OBJECTS)
            .successes(DEFAULT_SUCCESSES)
            .failureCount(DEFAULT_FAILURE_COUNT)
            .failures(DEFAULT_FAILURES)
            .pendingCount(DEFAULT_PENDING_COUNT)
            .pendings(DEFAULT_PENDINGS);
        return status;
    }

    @Before
    public void initTest() {
        statusRepository.deleteAll();
        status = createEntity();
    }

    @Test
    public void createStatus() throws Exception {
        int databaseSizeBeforeCreate = statusRepository.findAll().size();

        // Create the Status
        restStatusMockMvc.perform(post("/api/statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(status)))
            .andExpect(status().isCreated());

        // Validate the Status in the database
        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeCreate + 1);
        Status testStatus = statusList.get(statusList.size() - 1);
        assertThat(testStatus.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testStatus.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testStatus.getRequestUrl()).isEqualTo(DEFAULT_REQUEST_URL);
        assertThat(testStatus.getRequestTimestamp()).isEqualTo(DEFAULT_REQUEST_TIMESTAMP);
        assertThat(testStatus.getTotalObjects()).isEqualTo(DEFAULT_TOTAL_OBJECTS);
        assertThat(testStatus.getSuccessCount()).isEqualTo(DEFAULT_SUCCESS_COUNT);
        assertThat(testStatus.getSuccesses()).isEqualTo(DEFAULT_SUCCESSES);
        assertThat(testStatus.getFailureCount()).isEqualTo(DEFAULT_FAILURE_COUNT);
        assertThat(testStatus.getFailures()).isEqualTo(DEFAULT_FAILURES);
        assertThat(testStatus.getPendingCount()).isEqualTo(DEFAULT_PENDING_COUNT);
        assertThat(testStatus.getPendings()).isEqualTo(DEFAULT_PENDINGS);
    }

    @Test
    public void createStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = statusRepository.findAll().size();

        // Create the Status with an existing ID
        status.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restStatusMockMvc.perform(post("/api/statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(status)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllStatuses() throws Exception {
        // Initialize the database
        statusRepository.save(status);

        // Get all the statusList
        restStatusMockMvc.perform(get("/api/statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(status.getId())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].requestUrl").value(hasItem(DEFAULT_REQUEST_URL.toString())))
            .andExpect(jsonPath("$.[*].requestTimestamp").value(hasItem(DEFAULT_REQUEST_TIMESTAMP.toString())))
            .andExpect(jsonPath("$.[*].totalObjects").value(hasItem(DEFAULT_TOTAL_OBJECTS)))
            .andExpect(jsonPath("$.[*].successCount").value(hasItem(DEFAULT_SUCCESS_COUNT)))
            .andExpect(jsonPath("$.[*].successes").value(hasItem(DEFAULT_SUCCESSES.toString())))
            .andExpect(jsonPath("$.[*].failureCount").value(hasItem(DEFAULT_FAILURE_COUNT)))
            .andExpect(jsonPath("$.[*].failures").value(hasItem(DEFAULT_FAILURES.toString())))
            .andExpect(jsonPath("$.[*].pendingCount").value(hasItem(DEFAULT_PENDING_COUNT)))
            .andExpect(jsonPath("$.[*].pendings").value(hasItem(DEFAULT_PENDINGS.toString())));
    }

    @Test
    public void getStatus() throws Exception {
        // Initialize the database
        statusRepository.save(status);

        // Get the status
        restStatusMockMvc.perform(get("/api/statuses/{id}", status.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(status.getId()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.requestUrl").value(DEFAULT_REQUEST_URL.toString()))
            .andExpect(jsonPath("$.requestTimestamp").value(DEFAULT_REQUEST_TIMESTAMP.toString()))
            .andExpect(jsonPath("$.totalObjects").value(DEFAULT_TOTAL_OBJECTS))
            .andExpect(jsonPath("$.successCount").value(DEFAULT_SUCCESS_COUNT))
            .andExpect(jsonPath("$.successes").value(DEFAULT_SUCCESSES.toString()))
            .andExpect(jsonPath("$.failureCount").value(DEFAULT_FAILURE_COUNT))
            .andExpect(jsonPath("$.failures").value(DEFAULT_FAILURES.toString()))
            .andExpect(jsonPath("$.pendingCount").value(DEFAULT_PENDING_COUNT))
            .andExpect(jsonPath("$.pendings").value(DEFAULT_PENDINGS.toString()));
    }

    @Test
    public void getNonExistingStatus() throws Exception {
        // Get the status
        restStatusMockMvc.perform(get("/api/statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateStatus() throws Exception {
        // Initialize the database
        statusRepository.save(status);
        int databaseSizeBeforeUpdate = statusRepository.findAll().size();

        // Update the status
        Status updatedStatus = statusRepository.findOne(status.getId());
        updatedStatus
            .url(UPDATED_URL)
            .status(UPDATED_STATUS)
            .requestUrl(UPDATED_REQUEST_URL)
            .requestTimestamp(UPDATED_REQUEST_TIMESTAMP)
            .totalObjects(UPDATED_TOTAL_OBJECTS)
            .successes(UPDATED_SUCCESSES)
            .failureCount(UPDATED_FAILURE_COUNT)
            .failures(UPDATED_FAILURES)
            .pendingCount(UPDATED_PENDING_COUNT)
            .pendings(UPDATED_PENDINGS);

        restStatusMockMvc.perform(put("/api/statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedStatus)))
            .andExpect(status().isOk());

        // Validate the Status in the database
        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeUpdate);
        Status testStatus = statusList.get(statusList.size() - 1);
        assertThat(testStatus.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testStatus.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testStatus.getRequestUrl()).isEqualTo(UPDATED_REQUEST_URL);
        assertThat(testStatus.getRequestTimestamp()).isEqualTo(UPDATED_REQUEST_TIMESTAMP);
        assertThat(testStatus.getTotalObjects()).isEqualTo(UPDATED_TOTAL_OBJECTS);
        assertThat(testStatus.getSuccessCount()).isEqualTo(UPDATED_SUCCESS_COUNT);
        assertThat(testStatus.getSuccesses()).isEqualTo(UPDATED_SUCCESSES);
        assertThat(testStatus.getFailureCount()).isEqualTo(UPDATED_FAILURE_COUNT);
        assertThat(testStatus.getFailures()).isEqualTo(UPDATED_FAILURES);
        assertThat(testStatus.getPendingCount()).isEqualTo(UPDATED_PENDING_COUNT);
        assertThat(testStatus.getPendings()).isEqualTo(UPDATED_PENDINGS);
    }

    @Test
    public void updateNonExistingStatus() throws Exception {
        int databaseSizeBeforeUpdate = statusRepository.findAll().size();

        // Create the Status

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restStatusMockMvc.perform(put("/api/statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(status)))
            .andExpect(status().isCreated());

        // Validate the Status in the database
        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteStatus() throws Exception {
        // Initialize the database
        statusRepository.save(status);
        int databaseSizeBeforeDelete = statusRepository.findAll().size();

        // Get the status
        restStatusMockMvc.perform(delete("/api/statuses/{id}", status.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Status.class);
        Status status1 = new Status();
        status1.setId("id1");
        Status status2 = new Status();
        status2.setId(status1.getId());
        assertThat(status1).isEqualTo(status2);
        status2.setId("id2");
        assertThat(status1).isNotEqualTo(status2);
        status1.setId(null);
        assertThat(status1).isNotEqualTo(status2);
    }
}

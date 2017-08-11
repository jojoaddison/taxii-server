package com.thalesgroup.web.rest;

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
import io.cisa.taxiiserver.domain.Collection;
import io.cisa.taxiiserver.repository.CollectionRepository;
import io.cisa.taxiiserver.web.rest.CollectionResource;
import io.cisa.taxiiserver.web.rest.errors.ExceptionTranslator;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CollectionResource REST controller.
 *
 * @see CollectionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JTaxiiServerApp.class)
public class CollectionResourceIntTest {

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final String DEFAULT_DISPLAY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DISPLAY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CAN_READ = false;
    private static final Boolean UPDATED_CAN_READ = true;

    private static final Boolean DEFAULT_CAN_WRITE = false;
    private static final Boolean UPDATED_CAN_WRITE = true;

    private static final String DEFAULT_MEDIA_TYPES = "AAAAAAAAAA";
    private static final String UPDATED_MEDIA_TYPES = "BBBBBBBBBB";

    private static final Integer DEFAULT_OBJECTS_COUNT = 1;
    private static final Integer UPDATED_OBJECTS_COUNT = 2;

    @Autowired
    private CollectionRepository collectionRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restCollectionMockMvc;

    private Collection collection;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CollectionResource collectionResource = new CollectionResource(collectionRepository);
        this.restCollectionMockMvc = MockMvcBuilders.standaloneSetup(collectionResource)
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
    public static Collection createEntity() {
        Collection collection = new Collection()
            .url(DEFAULT_URL)
            .displayName(DEFAULT_DISPLAY_NAME)
            .description(DEFAULT_DESCRIPTION)
            .canRead(DEFAULT_CAN_READ)
            .canWrite(DEFAULT_CAN_WRITE)
            .mediaTypes(DEFAULT_MEDIA_TYPES)
            .objectsCount(DEFAULT_OBJECTS_COUNT);
        return collection;
    }

    @Before
    public void initTest() {
        collectionRepository.deleteAll();
        collection = createEntity();
    }

    @Test
    public void createCollection() throws Exception {
        int databaseSizeBeforeCreate = collectionRepository.findAll().size();

        // Create the Collection
        restCollectionMockMvc.perform(post("/api/collections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collection)))
            .andExpect(status().isCreated());

        // Validate the Collection in the database
        List<Collection> collectionList = collectionRepository.findAll();
        assertThat(collectionList).hasSize(databaseSizeBeforeCreate + 1);
        Collection testCollection = collectionList.get(collectionList.size() - 1);
        assertThat(testCollection.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testCollection.getDisplayName()).isEqualTo(DEFAULT_DISPLAY_NAME);
        assertThat(testCollection.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCollection.isCanRead()).isEqualTo(DEFAULT_CAN_READ);
        assertThat(testCollection.isCanWrite()).isEqualTo(DEFAULT_CAN_WRITE);
        assertThat(testCollection.getMediaTypes()).isEqualTo(DEFAULT_MEDIA_TYPES);
        assertThat(testCollection.getObjectsCount()).isEqualTo(DEFAULT_OBJECTS_COUNT);
    }

    @Test
    public void createCollectionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = collectionRepository.findAll().size();

        // Create the Collection with an existing ID
        collection.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restCollectionMockMvc.perform(post("/api/collections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collection)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Collection> collectionList = collectionRepository.findAll();
        assertThat(collectionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllCollections() throws Exception {
        // Initialize the database
        collectionRepository.save(collection);

        // Get all the collectionList
        restCollectionMockMvc.perform(get("/api/collections?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(collection.getId())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].displayName").value(hasItem(DEFAULT_DISPLAY_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].canRead").value(hasItem(DEFAULT_CAN_READ.booleanValue())))
            .andExpect(jsonPath("$.[*].canWrite").value(hasItem(DEFAULT_CAN_WRITE.booleanValue())))
            .andExpect(jsonPath("$.[*].mediaTypes").value(hasItem(DEFAULT_MEDIA_TYPES.toString())))
            .andExpect(jsonPath("$.[*].objectsCount").value(hasItem(DEFAULT_OBJECTS_COUNT)));
    }

    @Test
    public void getCollection() throws Exception {
        // Initialize the database
        collectionRepository.save(collection);

        // Get the collection
        restCollectionMockMvc.perform(get("/api/collections/{id}", collection.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(collection.getId()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()))
            .andExpect(jsonPath("$.displayName").value(DEFAULT_DISPLAY_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.canRead").value(DEFAULT_CAN_READ.booleanValue()))
            .andExpect(jsonPath("$.canWrite").value(DEFAULT_CAN_WRITE.booleanValue()))
            .andExpect(jsonPath("$.mediaTypes").value(DEFAULT_MEDIA_TYPES.toString()))
            .andExpect(jsonPath("$.objectsCount").value(DEFAULT_OBJECTS_COUNT));
    }

    @Test
    public void getNonExistingCollection() throws Exception {
        // Get the collection
        restCollectionMockMvc.perform(get("/api/collections/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateCollection() throws Exception {
        // Initialize the database
        collectionRepository.save(collection);
        int databaseSizeBeforeUpdate = collectionRepository.findAll().size();

        // Update the collection
        Collection updatedCollection = collectionRepository.findOne(collection.getId());
        updatedCollection
            .url(UPDATED_URL)
            .displayName(UPDATED_DISPLAY_NAME)
            .description(UPDATED_DESCRIPTION)
            .canRead(UPDATED_CAN_READ)
            .canWrite(UPDATED_CAN_WRITE)
            .mediaTypes(UPDATED_MEDIA_TYPES)
            .objectsCount(UPDATED_OBJECTS_COUNT);

        restCollectionMockMvc.perform(put("/api/collections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCollection)))
            .andExpect(status().isOk());

        // Validate the Collection in the database
        List<Collection> collectionList = collectionRepository.findAll();
        assertThat(collectionList).hasSize(databaseSizeBeforeUpdate);
        Collection testCollection = collectionList.get(collectionList.size() - 1);
        assertThat(testCollection.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testCollection.getDisplayName()).isEqualTo(UPDATED_DISPLAY_NAME);
        assertThat(testCollection.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCollection.isCanRead()).isEqualTo(UPDATED_CAN_READ);
        assertThat(testCollection.isCanWrite()).isEqualTo(UPDATED_CAN_WRITE);
        assertThat(testCollection.getMediaTypes()).isEqualTo(UPDATED_MEDIA_TYPES);
        assertThat(testCollection.getObjectsCount()).isEqualTo(UPDATED_OBJECTS_COUNT);
    }

    @Test
    public void updateNonExistingCollection() throws Exception {
        int databaseSizeBeforeUpdate = collectionRepository.findAll().size();

        // Create the Collection

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCollectionMockMvc.perform(put("/api/collections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collection)))
            .andExpect(status().isCreated());

        // Validate the Collection in the database
        List<Collection> collectionList = collectionRepository.findAll();
        assertThat(collectionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteCollection() throws Exception {
        // Initialize the database
        collectionRepository.save(collection);
        int databaseSizeBeforeDelete = collectionRepository.findAll().size();

        // Get the collection
        restCollectionMockMvc.perform(delete("/api/collections/{id}", collection.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Collection> collectionList = collectionRepository.findAll();
        assertThat(collectionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Collection.class);
        Collection collection1 = new Collection();
        collection1.setId("id1");
        Collection collection2 = new Collection();
        collection2.setId(collection1.getId());
        assertThat(collection1).isEqualTo(collection2);
        collection2.setId("id2");
        assertThat(collection1).isNotEqualTo(collection2);
        collection1.setId(null);
        assertThat(collection1).isNotEqualTo(collection2);
    }
}

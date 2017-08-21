package io.cisa.taxiiserver.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import io.cisa.taxiiserver.domain.Collection;
import io.cisa.taxiiserver.repository.CollectionRepository;
import io.cisa.taxiiserver.web.rest.util.HeaderUtil;
import io.cisa.taxiiserver.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;

/**
 * REST controller for managing Collection.
 */
@RestController
@RequestMapping("/api")
public class CollectionResource {

    private final Logger log = LoggerFactory.getLogger(CollectionResource.class);

    private static final String ENTITY_NAME = "collection";

    private final CollectionRepository collectionRepository;

    public CollectionResource(CollectionRepository collectionRepository) {
        this.collectionRepository = collectionRepository;
    }

    /**
     * POST  /collections : Create a new collection.
     *
     * @param collection the collection to create
     * @return the ResponseEntity with status 201 (Created) and with body the new collection, or with status 400 (Bad Request) if the collection has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/collections")
    @Timed
    public ResponseEntity<Collection> createCollection(@RequestBody Collection collection) throws URISyntaxException {
        log.debug("REST request to save Collection : {}", collection);
        if (collection.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new collection cannot already have an ID")).body(null);
        }
        Collection result = collectionRepository.save(collection);
        return ResponseEntity.created(new URI("/api/collections/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /collections : Updates an existing collection.
     *
     * @param collection the collection to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated collection,
     * or with status 400 (Bad Request) if the collection is not valid,
     * or with status 500 (Internal Server Error) if the collection couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/collections")
    @Timed
    public ResponseEntity<Collection> updateCollection(@RequestBody Collection collection) throws URISyntaxException {
        log.debug("REST request to update Collection : {}", collection);
        if (collection.getId() == null) {
            return createCollection(collection);
        }
        Collection result = collectionRepository.save(collection);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, collection.getId().toString()))
            .body(result);
    }

    /**
     * GET  /collections : get all the collections.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of collections in body
     */
    @GetMapping("/collections")
    @Timed
    public ResponseEntity<List<Collection>> getAllCollections(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Collections");
        Page<Collection> page = collectionRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/collections");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /collections/:id : get the "id" collection.
     *
     * @param id the id of the collection to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the collection, or with status 404 (Not Found)
     */
    @GetMapping("/collections/{id}")
    @Timed
    public ResponseEntity<Collection> getCollection(@PathVariable String id) {
        log.debug("REST request to get Collection : {}", id);
        Collection collection = collectionRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(collection));
    }

    /**
     * DELETE  /collections/:id : delete the "id" collection.
     *
     * @param id the id of the collection to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/collections/{id}")
    @Timed
    public ResponseEntity<Void> deleteCollection(@PathVariable String id) {
        log.debug("REST request to delete Collection : {}", id);
        collectionRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}

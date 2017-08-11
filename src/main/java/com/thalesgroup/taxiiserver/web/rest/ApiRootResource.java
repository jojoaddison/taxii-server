package com.thalesgroup.taxiiserver.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.thalesgroup.taxiiserver.domain.ApiRoot;
import com.thalesgroup.taxiiserver.repository.ApiRootRepository;
import com.thalesgroup.taxiiserver.web.rest.util.HeaderUtil;

import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ApiRoot.
 */
@RestController
@RequestMapping("/api")
public class ApiRootResource {

    private final Logger log = LoggerFactory.getLogger(ApiRootResource.class);

    private static final String ENTITY_NAME = "apiRoot";

    private final ApiRootRepository apiRootRepository;

    public ApiRootResource(ApiRootRepository apiRootRepository) {
        this.apiRootRepository = apiRootRepository;
    }

    /**
     * POST  /api-roots : Create a new apiRoot.
     *
     * @param apiRoot the apiRoot to create
     * @return the ResponseEntity with status 201 (Created) and with body the new apiRoot, or with status 400 (Bad Request) if the apiRoot has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/api-roots")
    @Timed
    public ResponseEntity<ApiRoot> createApiRoot(@Valid @RequestBody ApiRoot apiRoot) throws URISyntaxException {
        log.debug("REST request to save ApiRoot : {}", apiRoot);
        if (apiRoot.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new apiRoot cannot already have an ID")).body(null);
        }
        ApiRoot result = apiRootRepository.save(apiRoot);
        return ResponseEntity.created(new URI("/api/api-roots/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /api-roots : Updates an existing apiRoot.
     *
     * @param apiRoot the apiRoot to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated apiRoot,
     * or with status 400 (Bad Request) if the apiRoot is not valid,
     * or with status 500 (Internal Server Error) if the apiRoot couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/api-roots")
    @Timed
    public ResponseEntity<ApiRoot> updateApiRoot(@Valid @RequestBody ApiRoot apiRoot) throws URISyntaxException {
        log.debug("REST request to update ApiRoot : {}", apiRoot);
        if (apiRoot.getId() == null) {
            return createApiRoot(apiRoot);
        }
        ApiRoot result = apiRootRepository.save(apiRoot);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, apiRoot.getId().toString()))
            .body(result);
    }

    /**
     * GET  /api-roots : get all the apiRoots.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of apiRoots in body
     */
    @GetMapping("/api-roots")
    @Timed
    public List<ApiRoot> getAllApiRoots() {
        log.debug("REST request to get all ApiRoots");
        return apiRootRepository.findAll();
    }

    /**
     * GET  /api-roots/:id : get the "id" apiRoot.
     *
     * @param id the id of the apiRoot to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the apiRoot, or with status 404 (Not Found)
     */
    @GetMapping("/api-roots/{id}")
    @Timed
    public ResponseEntity<ApiRoot> getApiRoot(@PathVariable String id) {
        log.debug("REST request to get ApiRoot : {}", id);
        ApiRoot apiRoot = apiRootRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(apiRoot));
    }

    /**
     * DELETE  /api-roots/:id : delete the "id" apiRoot.
     *
     * @param id the id of the apiRoot to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/api-roots/{id}")
    @Timed
    public ResponseEntity<Void> deleteApiRoot(@PathVariable String id) {
        log.debug("REST request to delete ApiRoot : {}", id);
        apiRootRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}

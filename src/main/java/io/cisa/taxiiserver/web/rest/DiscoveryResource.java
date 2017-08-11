package io.cisa.taxiiserver.web.rest;

import com.codahale.metrics.annotation.Timed;

import io.swagger.annotations.ApiParam;
import io.cisa.taxiiserver.domain.Discovery;
import io.cisa.taxiiserver.repository.DiscoveryRepository;
import io.cisa.taxiiserver.web.rest.util.HeaderUtil;
import io.cisa.taxiiserver.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Discovery.
 */
@RestController
@RequestMapping("/api")
public class DiscoveryResource {

    private final Logger log = LoggerFactory.getLogger(DiscoveryResource.class);

    private static final String ENTITY_NAME = "discovery";

    private final DiscoveryRepository discoveryRepository;

    public DiscoveryResource(DiscoveryRepository discoveryRepository) {
        this.discoveryRepository = discoveryRepository;
    }

    /**
     * POST  /discoveries : Create a new discovery.
     *
     * @param discovery the discovery to create
     * @return the ResponseEntity with status 201 (Created) and with body the new discovery, or with status 400 (Bad Request) if the discovery has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/discoveries")
    @Timed
    public ResponseEntity<Discovery> createDiscovery(@Valid @RequestBody Discovery discovery) throws URISyntaxException {
        log.debug("REST request to save Discovery : {}", discovery);
        if (discovery.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new discovery cannot already have an ID")).body(null);
        }
        Discovery result = discoveryRepository.save(discovery);
        return ResponseEntity.created(new URI("/api/discoveries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /discoveries : Updates an existing discovery.
     *
     * @param discovery the discovery to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated discovery,
     * or with status 400 (Bad Request) if the discovery is not valid,
     * or with status 500 (Internal Server Error) if the discovery couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/discoveries")
    @Timed
    public ResponseEntity<Discovery> updateDiscovery(@Valid @RequestBody Discovery discovery) throws URISyntaxException {
        log.debug("REST request to update Discovery : {}", discovery);
        if (discovery.getId() == null) {
            return createDiscovery(discovery);
        }
        Discovery result = discoveryRepository.save(discovery);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, discovery.getId().toString()))
            .body(result);
    }

    /**
     * GET  /discoveries : get all the discoveries.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of discoveries in body
     */
    @GetMapping("/discoveries")
    @Timed
    public ResponseEntity<List<Discovery>> getAllDiscoveries(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Discoveries");
        Page<Discovery> page = discoveryRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/discoveries");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /discoveries/:id : get the "id" discovery.
     *
     * @param id the id of the discovery to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the discovery, or with status 404 (Not Found)
     */
    @GetMapping("/discoveries/{id}")
    @Timed
    public ResponseEntity<Discovery> getDiscovery(@PathVariable String id) {
        log.debug("REST request to get Discovery : {}", id);
        Discovery discovery = discoveryRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(discovery));
    }

    /**
     * DELETE  /discoveries/:id : delete the "id" discovery.
     *
     * @param id the id of the discovery to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/discoveries/{id}")
    @Timed
    public ResponseEntity<Void> deleteDiscovery(@PathVariable String id) {
        log.debug("REST request to delete Discovery : {}", id);
        discoveryRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}

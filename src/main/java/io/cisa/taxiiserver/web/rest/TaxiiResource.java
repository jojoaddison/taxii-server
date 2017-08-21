package io.cisa.taxiiserver.web.rest;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.cisa.taxiiserver.config.Constants;
import io.cisa.taxiiserver.domain.ApiRoot;
import io.cisa.taxiiserver.domain.Collection;
import io.cisa.taxiiserver.domain.Discovery;
import io.cisa.taxiiserver.domain.Manifest;
import io.cisa.taxiiserver.domain.Status;
import io.cisa.taxiiserver.domain.StixBundle;
import io.cisa.taxiiserver.repository.ApiRootRepository;
import io.cisa.taxiiserver.repository.CollectionRepository;
import io.cisa.taxiiserver.repository.DiscoveryRepository;
import io.cisa.taxiiserver.repository.StatusRepository;
import io.cisa.taxiiserver.repository.StixBundleRepository;
import io.cisa.taxiiserver.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;

/**
 * REST controller for managing Discovery.
 */
@RestController
public class TaxiiResource {

    private final Logger log = LoggerFactory.getLogger(TaxiiResource.class);

    private static final String ENTITY_NAME = "discovery";
    private final DiscoveryRepository discoveryRepository;
    private final StatusRepository statusRepository;
    private final ApiRootRepository apiRootRepository;
    private final CollectionRepository collectionRepository;
    private final StixBundleRepository stixBundleRepository;

    
    public TaxiiResource(DiscoveryRepository discoveryRepository, StixBundleRepository stixBundleRepository,
    		StatusRepository statusRepository, ApiRootRepository apiRootRepository,
    		CollectionRepository collectionRepository) {
        this.discoveryRepository = discoveryRepository;
        this.statusRepository = statusRepository;
        this.apiRootRepository = apiRootRepository;
        this.collectionRepository = collectionRepository;
        this.stixBundleRepository = stixBundleRepository;
    }

    
    /**
     * GET  / : get default taxii discovery
     *
     * @param none expected
     * @return the ResponseEntity with status 200 (OK) and the discovery in body
     */
    @GetMapping(value="/", consumes=Constants.ACCEPT_TAXII_HEADER, produces=Constants.ACCEPT_TAXII_HEADER)
    public ResponseEntity<Discovery> getDefaultDiscovery() {
        log.debug("REST request to get the taxii Discovery");
        Discovery discovery = discoveryRepository.findOneByDefaultURL("taxii");
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(discovery));
    }
    
    
    /**
     * GET  / : get api-root discovery by default name
     *
     * @param none expected
     * @return the ResponseEntity with status 200 (OK) and the discovery in body
     */
    @GetMapping(value="/{apiRoot}", consumes=Constants.ACCEPT_TAXII_HEADER, produces=Constants.ACCEPT_TAXII_HEADER)
    public ResponseEntity<Discovery> getDiscovery(@PathVariable String apiRoot) {
        log.debug("REST request to get the {} Discovery", apiRoot);
        Discovery discovery = discoveryRepository.findOneByDefaultURL(apiRoot);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(discovery));
    }

    /**
     * GET  / : get api-root discovery by default name
     *
     * @param none expected
     * @return the ResponseEntity with status 200 (OK) and the discovery in body
     */
    @GetMapping(value="/{apiRoot}/status/{statusId}", consumes=Constants.ACCEPT_TAXII_HEADER, produces=Constants.ACCEPT_TAXII_HEADER)
    public ResponseEntity<Status> getStatus(@PathVariable String apiRoot, @PathVariable String statusId) {
        log.debug("REST request to get the Discovery: {}  status ID: {}", apiRoot, statusId);
        Discovery discovery = discoveryRepository.findOneByDefaultURL(apiRoot);
        if(Optional.ofNullable(discovery).isPresent()) {
        	Status status = statusRepository.findOne(statusId);
            return ResponseUtil.wrapOrNotFound(Optional.ofNullable(status));
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * GET  / : get api-root collections by api-root name
     *
     * @param none expected
     * @return the ResponseEntity with status 200 (OK) and the list of collections in body
     */
    @GetMapping(value="/{apiRoot}/collections", consumes=Constants.ACCEPT_TAXII_HEADER, produces=Constants.ACCEPT_TAXII_HEADER)
    public ResponseEntity<Set<Collection>> getCollections(@PathVariable String apiRoot) {
        log.debug("REST request to get the Collections from API-ROOT: {}", apiRoot);
        ApiRoot apiRootDocument = apiRootRepository.findByUrl(apiRoot);
        if(Optional.ofNullable(apiRootDocument).isPresent()) {
        	Set<Collection> collections = apiRootDocument.getCollections();
            return ResponseUtil.wrapOrNotFound(Optional.ofNullable(collections));
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    

    /**
     * GET  / : get api-root collection by collection url
     *
     * @param none expected
     * @return the ResponseEntity with status 200 (OK) and the list of collections in body
     */
    @GetMapping(value="/{apiRoot}/collections/{url}", consumes=Constants.ACCEPT_TAXII_HEADER, produces=Constants.ACCEPT_TAXII_HEADER)
    public ResponseEntity<Collection> getCollection(@PathVariable String apiRoot, @PathVariable String url) {
        log.debug("REST request to get a Collection from API-ROOT: {} with the name: {}", apiRoot);
        ApiRoot apiRootDocument = apiRootRepository.findByUrl(apiRoot);
        if(Optional.ofNullable(apiRootDocument).isPresent()) {
        	Set<Collection> collections = apiRootDocument.getCollections();
            return ResponseUtil.wrapOrNotFound(collections.stream().filter((Collection c) -> c.getUrl().equals(url)).findFirst());
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    

    /**
     * GET  / : get manifest of a collection by api-root and collection url
     *
     * @param none expected
     * @return the ResponseEntity with status 200 (OK) and the list of collections in body
     */
    @GetMapping(value="/{apiRoot}/collections/{url}/manifest", consumes=Constants.ACCEPT_TAXII_HEADER, produces=Constants.ACCEPT_TAXII_HEADER)
    public ResponseEntity<Set<Manifest>> getManifest(@PathVariable String apiRoot, @PathVariable String url) {
        log.debug("REST request to get Manifests from API-ROOT: {} with the url: {}", apiRoot, url);
        ApiRoot apiRootDocument = apiRootRepository.findByUrl(apiRoot);
        if(Optional.ofNullable(apiRootDocument).isPresent()) {
        	Set<Collection> collections = apiRootDocument.getCollections();
        	Optional<Collection> collection = collections.stream().filter((Collection c) -> c.getUrl().equals(url)).findFirst();
        	if(collection.isPresent()) {
        		Collection item = collection.get();
        		 return ResponseUtil.wrapOrNotFound(Optional.ofNullable(item.getManifest()));
        	}
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * GET  / : get manifest of a collection by api-root and collection url
     *
     * @param none expected
     * @return the ResponseEntity with status 200 (OK) and the list of collections in body
     */
    @GetMapping(value="/{apiRoot}/collections/{url}/objects", consumes=Constants.ACCEPT_STIX_HEADER, produces=Constants.ACCEPT_STIX_HEADER)
    public ResponseEntity<List<StixBundle>> getObjectst(@PathVariable String apiRoot, @PathVariable String url, @ApiParam Pageable pageable) {
        log.debug("REST request to get Objects from API-ROOT: {} with the url: {}", apiRoot, url);
        ApiRoot apiRootDocument = apiRootRepository.findByUrl(apiRoot);
        String header = ("/").concat(apiRoot).concat("/collections/").concat(url).concat("/objects");
        if(Optional.ofNullable(apiRootDocument).isPresent()) {
        	Set<Collection> collections = apiRootDocument.getCollections();
        	Optional<Collection> collection = collections.stream().filter((Collection c) -> c.getUrl().equals(url)).findFirst();
        	if(collection.isPresent()) {
        			Page<StixBundle> page = stixBundleRepository.findAll(pageable);
        	        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, header);
        	        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
        	}
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

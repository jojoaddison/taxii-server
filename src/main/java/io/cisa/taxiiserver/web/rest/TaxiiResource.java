package io.cisa.taxiiserver.web.rest;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import io.cisa.taxiiserver.config.Constants;
import io.cisa.taxiiserver.domain.ApiRoot;
import io.cisa.taxiiserver.domain.Collection;
import io.cisa.taxiiserver.domain.Discovery;
import io.cisa.taxiiserver.domain.Manifest;
import io.cisa.taxiiserver.domain.status.Status;
import io.cisa.taxiiserver.domain.stix.StixBundle;
import io.cisa.taxiiserver.domain.stix.StixObject;
import io.cisa.taxiiserver.repository.ApiRootRepository;
import io.cisa.taxiiserver.repository.CollectionRepository;
import io.cisa.taxiiserver.repository.DiscoveryRepository;
import io.cisa.taxiiserver.repository.StatusRepository;
import io.cisa.taxiiserver.repository.StixBundleRepository;
import io.cisa.taxiiserver.repository.StixObjectRepository;
import io.cisa.taxiiserver.service.TaxiiService;
import io.cisa.taxiiserver.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;

/**
 * REST controller for managing Discovery.
 */
@RestController
public class TaxiiResource {

	private final Logger log = LoggerFactory.getLogger(TaxiiResource.class);

	private final DiscoveryRepository discoveryRepository;
	private final StatusRepository statusRepository;
	private final ApiRootRepository apiRootRepository;
	private final CollectionRepository collectionRepository;
	private final StixBundleRepository stixBundleRepository;
	private final StixObjectRepository stixObjectRepository;
	private final TaxiiService taxiiService;

	public TaxiiResource(DiscoveryRepository discoveryRepository, StixBundleRepository stixBundleRepository,
			StatusRepository statusRepository, ApiRootRepository apiRootRepository,
			StixObjectRepository stixObjectRepository, CollectionRepository collectionRepository,
			TaxiiService taxiiService) {
		this.discoveryRepository = discoveryRepository;
		this.statusRepository = statusRepository;
		this.apiRootRepository = apiRootRepository;
		this.collectionRepository = collectionRepository;
		this.stixBundleRepository = stixBundleRepository;
		this.stixObjectRepository = stixObjectRepository;
		this.taxiiService = taxiiService;
	}

	/**
	 * GET / : get default taxii discovery
	 *
	 * @param none
	 *            expected
	 * @return the ResponseEntity with status 200 (OK) and the discovery in body
	 */
	@GetMapping(value = "/", consumes = Constants.ACCEPT_TAXII_HEADER, produces = Constants.ACCEPT_TAXII_HEADER)
	public ResponseEntity<Discovery> getDefaultDiscovery() {
		log.debug("REST request to get the taxii Discovery");
		Discovery discovery = discoveryRepository.findOneByDefaultURL("taxii");
		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(discovery));
	}

	/**
	 * GET / : get api-root discovery by default name
	 *
	 * @param none
	 *            expected
	 * @return the ResponseEntity with status 200 (OK) and the discovery in body
	 */
	@GetMapping(value = "/{apiRoot}", consumes = Constants.ACCEPT_TAXII_HEADER, produces = Constants.ACCEPT_TAXII_HEADER)
	public ResponseEntity<Discovery> getDiscovery(@PathVariable String apiRoot) {
		log.debug("REST request to get the {} Discovery", apiRoot);
		Discovery discovery = discoveryRepository.findOneByDefaultURL(apiRoot);
		if (!Optional.ofNullable(discovery).isPresent()) {
			ApiRoot apiRootDocument = apiRootRepository.findByUrl(apiRoot);
			if (Optional.ofNullable(apiRootDocument).isPresent()) {
				discovery = discoveryRepository.findOneByDefaultURL("taxii");
			}
		}
		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(discovery));
	}

	/**
	 * GET / : get api-root discovery by default name
	 *
	 * @param none
	 *            expected
	 * @return the ResponseEntity with status 200 (OK) and the discovery in body
	 */
	@GetMapping(value = "/{apiRoot}/status/{statusId}", consumes = Constants.ACCEPT_TAXII_HEADER, produces = Constants.ACCEPT_TAXII_HEADER)
	public ResponseEntity<Status> getStatus(@PathVariable String apiRoot, @PathVariable String statusId) {
		log.debug("REST request to get the Discovery: {}  status ID: {}", apiRoot, statusId);
		Discovery discovery = discoveryRepository.findOneByDefaultURL(apiRoot);
		if (Optional.ofNullable(discovery).isPresent()) {
			Status status = statusRepository.findOne(statusId);
			return ResponseUtil.wrapOrNotFound(Optional.ofNullable(status));
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	/**
	 * GET / : get api-root collections by api-root name
	 *
	 * @param none
	 *            expected
	 * @return the ResponseEntity with status 200 (OK) and the list of collections
	 *         in body
	 */
	@GetMapping(value = "/{apiRoot}/collections", consumes = Constants.ACCEPT_TAXII_HEADER, produces = Constants.ACCEPT_TAXII_HEADER)
	public ResponseEntity<Set<Collection>> getCollections(@PathVariable String apiRoot) {
		log.debug("REST request to get the Collections from API-ROOT: {}", apiRoot);
		ApiRoot apiRootDocument = apiRootRepository.findByUrl(apiRoot);
		if (Optional.ofNullable(apiRootDocument).isPresent()) {
			Set<Collection> collections = apiRootDocument.getCollections();
			return ResponseUtil.wrapOrNotFound(Optional.ofNullable(collections));
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	/**
	 * GET / : get api-root collection by collection url
	 *
	 * @param none
	 *            expected
	 * @return the ResponseEntity with status 200 (OK) and the list of collections
	 *         in body
	 */
	@GetMapping(value = "/{apiRoot}/collections/{url}", consumes = Constants.ACCEPT_TAXII_HEADER, produces = Constants.ACCEPT_TAXII_HEADER)
	public ResponseEntity<Collection> getCollection(@PathVariable String apiRoot, @PathVariable String url) {
		log.debug("REST request to get a Collection from API-ROOT: {} with the name: {}", apiRoot);
		ApiRoot apiRootDocument = apiRootRepository.findByUrl(apiRoot);
		if (Optional.ofNullable(apiRootDocument).isPresent()) {
			Set<Collection> collections = apiRootDocument.getCollections();
			return ResponseUtil
					.wrapOrNotFound(collections.stream().filter((Collection c) -> c.getUrl().equals(url)).findFirst());
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	/**
	 * GET / : get manifest of a collection by api-root and collection url
	 *
	 * @param none
	 *            expected
	 * @return the ResponseEntity with status 200 (OK) and the list of collections
	 *         in body
	 */
	@GetMapping(value = "/{apiRoot}/collections/{url}/manifest", consumes = Constants.ACCEPT_TAXII_HEADER, produces = Constants.ACCEPT_TAXII_HEADER)
	public ResponseEntity<Set<Manifest>> getManifest(@PathVariable String apiRoot, @PathVariable String url) {
		log.debug("REST request to get Manifests from API-ROOT: {} with the url: {}", apiRoot, url);
		ApiRoot apiRootDocument = apiRootRepository.findByUrl(apiRoot);
		if (Optional.ofNullable(apiRootDocument).isPresent()) {
			Set<Collection> collections = apiRootDocument.getCollections();
			Optional<Collection> collection = collections.stream().filter((Collection c) -> c.getUrl().equals(url))
					.findFirst();
			if (collection.isPresent()) {
				Collection item = collection.get();
				return ResponseUtil.wrapOrNotFound(Optional.ofNullable(item.getManifest()));
			}
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	/**
	 * GET / : get manifest of a collection by api-root and collection url
	 *
	 * @param none
	 *            expected
	 * @return the ResponseEntity with status 200 (OK) and the list of collections
	 *         in body
	 */
	@GetMapping(value = "/{apiRoot}/collections/{url}/objects", consumes = Constants.ACCEPT_STIX_HEADER, produces = Constants.ACCEPT_STIX_HEADER)
	public ResponseEntity<StixBundle> getObjectst(@PathVariable String apiRoot, @PathVariable String url,
			@ApiParam Pageable pageable) {
		log.debug("REST request to get Objects from API-ROOT: {} with the url: {}", apiRoot, url);
		ApiRoot apiRootDocument = apiRootRepository.findByUrl(apiRoot);
		String header = ("/").concat(apiRoot).concat("/collections/").concat(url).concat("/objects");
		if (Optional.ofNullable(apiRootDocument).isPresent()) {
			Set<Collection> collections = apiRootDocument.getCollections();
			Optional<Collection> collection = collections.stream().filter((Collection c) -> c.getUrl().equals(url))
					.findFirst();
			if (collection.isPresent()) {
				Page<StixObject> page = stixObjectRepository.findAll(pageable);
				HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, header);
				StixBundle bundle = taxiiService.loadBundle(page);
				return new ResponseEntity<>(bundle, headers, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	/**
	 * Post a bundle of stix objects for processing
	 * @param stixBundle
	 * @param api
	 * @param url
	 * @return Status
	 */
	@PostMapping(value = "/{api}/collections/{url}/objects", consumes = Constants.ACCEPT_TAXII_HEADER, produces = Constants.ACCEPT_STIX_HEADER)
	public ResponseEntity<Status> postStixBundle(@RequestBody StixBundle stixBundle, @PathVariable String api,
			@PathVariable String url) {

		log.debug("REST request to post Object {} to API-ROOT: {} with the url: {}", stixBundle, api, url);
		ApiRoot apiRootDocument = apiRootRepository.findByUrl(api);
		if (Optional.ofNullable(apiRootDocument).isPresent()) {
			Set<Collection> collections = apiRootDocument.getCollections();
			Optional<Collection> collection = collections.stream().filter((Collection c) -> c.getUrl().equals(url))
					.findFirst();
			if (collection.isPresent()) {
				Status status = new Status();
				if(taxiiService.validate(stixBundle, status)) {
					taxiiService.save(stixBundle, status);
				}
				String header = ("/").concat(api).concat("/status/").concat(status.getId());
				return ResponseEntity.accepted().header("bundle", header).body(status);
			}
		}
		return ResponseEntity.notFound().build();
	}

	/**
	 * GET /statuses/:id : get the "id" status.
	 *
	 * @param id
	 *            the id of the status to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the status, or
	 *         with status 404 (Not Found)
	 */
	@GetMapping(value = "/{apiRoot}/collections/{url}/objects/{id}", consumes = Constants.ACCEPT_STIX_HEADER, produces = Constants.ACCEPT_STIX_HEADER)
	@Timed
	public ResponseEntity<StixObject> getStixObject(@PathVariable String apiRoot, @PathVariable String url,
			@PathVariable String id) {
		log.debug("REST request to get Object with ID: {}", id);
		ApiRoot apiRootDocument = apiRootRepository.findByUrl(apiRoot);
		// String header =
		// ("/").concat(apiRoot).concat("/collections/").concat(url).concat("/objects/").concat(id);
		if (Optional.ofNullable(apiRootDocument).isPresent()) {
			Set<Collection> collections = apiRootDocument.getCollections();
			Optional<Collection> collection = collections.stream().filter((Collection c) -> c.getUrl().equals(url))
					.findFirst();
			if (collection.isPresent()) {
				StixObject stixObject = stixObjectRepository.findOne(id);
				return ResponseUtil.wrapOrNotFound(Optional.ofNullable(stixObject));
			}
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

}

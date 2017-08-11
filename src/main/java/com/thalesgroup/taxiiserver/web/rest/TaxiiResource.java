package com.thalesgroup.taxiiserver.web.rest;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thalesgroup.taxiiserver.config.Constants;
import com.thalesgroup.taxiiserver.domain.Discovery;
import com.thalesgroup.taxiiserver.repository.DiscoveryRepository;

import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing Discovery.
 */
@RestController
@RequestMapping("/taxii")
public class TaxiiResource {

    private final Logger log = LoggerFactory.getLogger(TaxiiResource.class);

    private final DiscoveryRepository discoveryRepository;

    
    public TaxiiResource(DiscoveryRepository discoveryRepository) {
        this.discoveryRepository = discoveryRepository;
    }
    
    
    
    /**
     * GET  / : get default taxii discovery
     *
     * @param none expected
     * @return the ResponseEntity with status 200 (OK) and the discovery in body
     */
    @GetMapping(value="/", consumes=Constants.ACCEPT_TAXII_HEADER, produces=Constants.ACCEPT_TAXII_HEADER)
    public ResponseEntity<Discovery> getDiscovery() {
        log.debug("REST request to get the taxii Discovery");
        Discovery discovery = discoveryRepository.findByDisplayName("taxii");
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(discovery));
    }
}

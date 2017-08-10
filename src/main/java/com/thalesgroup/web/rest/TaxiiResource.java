package com.thalesgroup.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thalesgroup.config.Constants;
import com.thalesgroup.domain.Discovery;
import com.thalesgroup.repository.DiscoveryRepository;

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
    @GetMapping(value="/", headers=Constants.ACCEPT_HEADER)
    public ResponseEntity<Discovery> getDiscovery() {
        log.debug("REST request to get the taxii Discovery");
        Discovery discovery = discoveryRepository.findByName("taxii");
        return new ResponseEntity<>(discovery, HttpStatus.OK);
    }
}

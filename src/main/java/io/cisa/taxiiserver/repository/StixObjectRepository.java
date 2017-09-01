package io.cisa.taxiiserver.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import io.cisa.taxiiserver.domain.stix.StixObject;

public interface StixObjectRepository extends MongoRepository<StixObject,String> {
	Page<StixObject> findByType(String type, Pageable pageable);
	Page<StixObject> findByRevoked(String type, Pageable pageable);
	
}

package io.cisa.taxiiserver.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import io.cisa.taxiiserver.domain.stix.StixBundle;

@Repository
public interface StixBundleRepository  extends MongoRepository<StixBundle,String>{
	
}

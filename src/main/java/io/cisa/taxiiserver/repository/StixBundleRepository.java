package io.cisa.taxiiserver.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import io.cisa.taxiiserver.domain.StixBundle;

@Repository
public interface StixBundleRepository  extends MongoRepository<StixBundle,String>{
	Page<StixBundle> findByType(String type);
}

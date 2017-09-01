package io.cisa.taxiiserver.repository;

<<<<<<< HEAD
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import io.cisa.taxiiserver.domain.stix.StixBundle;

@Repository
public interface StixBundleRepository  extends MongoRepository<StixBundle,String>{
	
=======
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import io.cisa.taxiiserver.domain.StixBundle;

@Repository
public interface StixBundleRepository  extends MongoRepository<StixBundle,String>{
	Page<StixBundle> findByType(String type);
>>>>>>> 759fb197725503daf5a2192ee305fc05a398da5d
}

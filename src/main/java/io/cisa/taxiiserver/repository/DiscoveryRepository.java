package io.cisa.taxiiserver.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import io.cisa.taxiiserver.domain.Discovery;

/**
 * Spring Data MongoDB repository for the Discovery entity.
 */
@Repository
public interface DiscoveryRepository extends MongoRepository<Discovery,String> {

	Discovery findOneByDisplayName(String name);
	Discovery findOneByDefaultURL(String url);
	Discovery findOneByContact(String contact);
    
}

package io.cisa.taxiiserver.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import io.cisa.taxiiserver.domain.Discovery;

/**
 * Spring Data MongoDB repository for the Discovery entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DiscoveryRepository extends MongoRepository<Discovery,String> {

	Discovery findByDisplayName(String name);
	Discovery findByDefaultURL(String url);
	Discovery findByContact(String contact);
    
}
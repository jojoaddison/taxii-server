package com.thalesgroup.repository;

import com.thalesgroup.domain.Discovery;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Discovery entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DiscoveryRepository extends MongoRepository<Discovery,String> {

	Discovery findByName(String name);
    
}

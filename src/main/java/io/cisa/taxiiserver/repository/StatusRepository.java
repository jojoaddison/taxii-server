package io.cisa.taxiiserver.repository;

import org.springframework.stereotype.Repository;

import io.cisa.taxiiserver.domain.Status;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Status entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StatusRepository extends MongoRepository<Status,String> {
    
}

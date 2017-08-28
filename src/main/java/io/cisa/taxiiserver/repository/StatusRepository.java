package io.cisa.taxiiserver.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import io.cisa.taxiiserver.domain.status.Status;

/**
 * Spring Data MongoDB repository for the Status entity.
 */
@Repository
public interface StatusRepository extends MongoRepository<Status,String> {
    
}

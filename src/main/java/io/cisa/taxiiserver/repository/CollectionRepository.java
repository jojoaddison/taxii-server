package io.cisa.taxiiserver.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import io.cisa.taxiiserver.domain.Collection;

/**
 * Spring Data MongoDB repository for the Collection entity.
 */
@Repository
public interface CollectionRepository extends MongoRepository<Collection,String> {
    
}

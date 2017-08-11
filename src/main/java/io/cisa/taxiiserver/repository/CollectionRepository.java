package io.cisa.taxiiserver.repository;

import org.springframework.stereotype.Repository;

import io.cisa.taxiiserver.domain.Collection;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Collection entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CollectionRepository extends MongoRepository<Collection,String> {
    
}

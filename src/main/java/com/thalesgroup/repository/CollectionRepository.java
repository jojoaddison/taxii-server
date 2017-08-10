package com.thalesgroup.repository;

import com.thalesgroup.domain.Collection;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Collection entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CollectionRepository extends MongoRepository<Collection,String> {
    
}

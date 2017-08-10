package com.thalesgroup.repository;

import com.thalesgroup.domain.ApiRoot;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the ApiRoot entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApiRootRepository extends MongoRepository<ApiRoot,String> {
    
}

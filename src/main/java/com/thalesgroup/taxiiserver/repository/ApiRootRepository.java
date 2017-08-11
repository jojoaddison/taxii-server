package com.thalesgroup.taxiiserver.repository;

import org.springframework.stereotype.Repository;

import com.thalesgroup.taxiiserver.domain.ApiRoot;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the ApiRoot entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApiRootRepository extends MongoRepository<ApiRoot,String> {
    
}

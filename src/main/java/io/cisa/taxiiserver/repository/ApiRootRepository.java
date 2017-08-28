package io.cisa.taxiiserver.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import io.cisa.taxiiserver.domain.ApiRoot;

/**
 * Spring Data MongoDB repository for the ApiRoot entity.
 */
@Repository
public interface ApiRootRepository extends MongoRepository<ApiRoot,String> {

	ApiRoot findByUrl(String apiRoot);
    
}

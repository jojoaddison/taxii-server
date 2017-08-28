package io.cisa.taxiiserver.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import io.cisa.taxiiserver.domain.stix.StixObject;

public interface StixObjectRepository extends MongoRepository<StixObject,String> {

}

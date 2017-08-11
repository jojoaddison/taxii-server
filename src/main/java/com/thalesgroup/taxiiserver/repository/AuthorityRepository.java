package com.thalesgroup.taxiiserver.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.thalesgroup.taxiiserver.domain.Authority;

/**
 * Spring Data MongoDB repository for the Authority entity.
 */
public interface AuthorityRepository extends MongoRepository<Authority, String> {
}

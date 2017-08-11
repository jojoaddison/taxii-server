package io.cisa.taxiiserver.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import io.cisa.taxiiserver.domain.Authority;

/**
 * Spring Data MongoDB repository for the Authority entity.
 */
public interface AuthorityRepository extends MongoRepository<Authority, String> {
}

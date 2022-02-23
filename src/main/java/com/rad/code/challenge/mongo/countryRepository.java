package com.rad.code.challenge.mongo;

import com.rad.code.challenge.entities.country;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface countryRepository extends MongoRepository<country, String> {
}

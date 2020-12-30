package com.github.nationminu.spider.repository;
 
import org.springframework.data.mongodb.repository.MongoRepository;

import com.github.nationminu.spider.domain.Location;

public interface LocationRepository extends MongoRepository<Location, String> {
}
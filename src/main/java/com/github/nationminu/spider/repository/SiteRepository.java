package com.github.nationminu.spider.repository;
 
import org.springframework.data.mongodb.repository.MongoRepository;

import com.github.nationminu.spider.domain.Site;

public interface SiteRepository extends MongoRepository<Site, String> {
}
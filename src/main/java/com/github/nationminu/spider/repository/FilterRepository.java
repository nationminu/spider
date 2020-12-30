package com.github.nationminu.spider.repository;
 
import org.springframework.data.mongodb.repository.MongoRepository;
 
import com.github.nationminu.spider.domain.Filter;

public interface FilterRepository extends MongoRepository<Filter, String> {
}
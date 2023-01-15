package com.bicheka.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import com.bicheka.POJO.Store;

@Component
public interface StoreRepository extends MongoRepository<Store, Long>{
    
}

package com.bicheka.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.bicheka.POJO.Store;

public interface StoreRepository extends MongoRepository<Store, String>{
    @Query("{'storename' : ?0}")
    Store findStoreByName(String storename);
}

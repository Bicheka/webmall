package com.bicheka.store;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface StoreRepository extends MongoRepository<Store, String>{
    @Query("{'storename' : ?0}")
    Store findStoreByName(String storename);
}

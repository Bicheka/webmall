package com.webmall.store;


import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface StoreRepository extends MongoRepository<Store, String>{
    @Query("{'storename' : ?0}")
    Store findStoreByName(String storename);

    @Query("{ $text: { $search: ?0 } }")
    List<Store> searchByText(String searchTerm);
}

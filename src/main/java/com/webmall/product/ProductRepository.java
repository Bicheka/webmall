package com.webmall.product;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface ProductRepository extends MongoRepository<Product, String>{
    @Query("{ $text: { $search: ?0 } }")
    List<Product> searchByText(String searchTerm);
}

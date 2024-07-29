package com.webmall.product;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String>{
    // @Query("")
    // public List<Product> getProductsWithLimit(boolean status, Pageable pageable);
}

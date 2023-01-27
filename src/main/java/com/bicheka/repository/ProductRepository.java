package com.bicheka.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bicheka.POJO.Product;

public interface ProductRepository extends MongoRepository<Product, String>{
    // @Query("")
    // public List<Product> getProductsWithLimit(boolean status, Pageable pageable);
}

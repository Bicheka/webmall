package com.bicheka.service;

import java.util.List;

import com.bicheka.POJO.Product;

public interface ProductService {
    Product saveProduct(Product product);
    Product getProductById(String id);
    List<Product> getAllProducts();
    void deleteProduct(String id);
    // void updateProduct(String id, Product updatedProduct);
}

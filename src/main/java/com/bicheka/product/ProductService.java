package com.bicheka.product;

import java.util.List;

public interface ProductService {
    Product saveProduct(Product product, String ownerEmail);
    Product getProductById(String id);
    List<Product> getAllProducts();
    String deleteProduct(String id, String userEmail);//TODO: needs optimization
    void updateProductPrice(String id, double price, String userEmail);
    void updateProductName(String id, String name, String userEmail);
    void updateProductDescription(String id, String description, String userEmail); 
}

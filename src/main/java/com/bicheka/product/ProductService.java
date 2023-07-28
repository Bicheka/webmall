package com.bicheka.product;

import java.util.List;

public interface ProductService {
    Product saveProduct(Product product);
    Product getProductById(String id);
    List<Product> getAllProducts();
    String deleteProduct(String id, String userEmail);//TODO: needs optimization
    // void updateProduct(String id, Product updatedProduct);
}

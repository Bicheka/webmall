package com.bicheka.service;

public interface ShoppingCartService {

    void addProductToShoppingCart(String email, String productId, Integer quantity);
    void updateProductQuantityInShoppingCart(String email, String productId, Integer quantity);
    String addToCart(String id, String email);
    String removeFromCart(String id, String email);
    String clearCart(String email);
    String buyCartItems();
}

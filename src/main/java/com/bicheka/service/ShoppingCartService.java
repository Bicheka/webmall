package com.bicheka.service;

import java.util.List;

import com.bicheka.POJO.CartItem;

public interface ShoppingCartService {

    List < CartItem > getShoppingCart(String email);
    void addProductToShoppingCart(String email, String productId);
    String updateProductQuantityInShoppingCart(String email, String productId, Integer quantity);
    String removeFromCart(String id, String email);
    String clearCart(String email);
    double calculateTotal(String email);
    String buyCartItems();
}

package com.bicheka.cart;

import java.util.List;

public interface ShoppingCartService {

    List < CartItem > getShoppingCart(String email);
    void addProductToShoppingCart(String email, String productId);
    String updateProductQuantityInShoppingCart(String email, String productId, Integer quantity);
    String removeFromCart(String id, String email);
    String clearCart(String email);
    double calculateTotal(String email);
    String buyCartItems();
}

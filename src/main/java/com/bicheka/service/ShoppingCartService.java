package com.bicheka.service;

public interface ShoppingCartService {
    String addToCart(String id, String email);
    String removeFromCart(String id, String email);
    String clearCart();
    String buyCartItems();
}

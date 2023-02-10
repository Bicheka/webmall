package com.bicheka.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bicheka.POJO.User;
import com.bicheka.exeption.EntityNotFoundException;
import com.bicheka.repository.ProductRepository;
import com.bicheka.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService{

    ProductRepository productRepository;
    UserRepository userRepository;

    @Override
    public String addToCart(String id, String email) {
        Optional<User> user = userRepository.findByEmail(email);
        unwrapUser(user, email).getShoppingCartProductsIds().add(id);
        return "product added to shopping cart";
    }

    @Override
    public String removeFromCart(String id, String email) {
       
        return null;
    }

    @Override
    public String clearCart() {
       
        return null;
    }

    @Override
    public String buyCartItems() {
        
        return "This method is not working yet";
    }

    static User unwrapUser(Optional<User> entity, String id) {
        if (entity.isPresent()) return entity.get();
        else throw new EntityNotFoundException(id, User.class);
    }
    
}

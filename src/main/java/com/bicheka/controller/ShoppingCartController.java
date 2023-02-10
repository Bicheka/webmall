package com.bicheka.controller;

import com.bicheka.service.ShoppingCartService;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// import lombok.AllArgsConstructor;

// @AllArgsConstructor
@RestController
@RequestMapping("/cart")
public class ShoppingCartController {
    
    @Autowired
    ShoppingCartService shoppingCartService;

    @PatchMapping("/add-to-cart/{id}")
    public ResponseEntity<String> addToCart(@PathVariable String id, Principal principal ){
        return new ResponseEntity<>(shoppingCartService.addToCart(id, principal.getName()), HttpStatus.OK);
    }

    @PatchMapping("/remove-from-cart/{id}")
    public ResponseEntity<String> removeFromCart(@PathVariable String id, Principal principal){
        return new ResponseEntity<>(shoppingCartService.removeFromCart(id, principal.getName()), HttpStatus.OK);
    }
}

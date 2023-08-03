package com.bicheka.cart;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// import lombok.AllArgsConstructor;

// @AllArgsConstructor
@RestController
@RequestMapping("/cart")
public class ShoppingCartController {
    
    @Autowired
    ShoppingCartService shoppingCartService;

    @GetMapping("/get-cart")
    public ResponseEntity<?> getCart(Principal principal){
        return new ResponseEntity<>(shoppingCartService.getShoppingCart(principal.getName()), HttpStatus.OK);
    }

    @PatchMapping("/add-product-to-cart/{id}")
    public ResponseEntity<Void> addProductToCart(@PathVariable String id, Principal principal ){
        shoppingCartService.addProductToShoppingCart(principal.getName(), id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/remove-from-cart/{id}")
    public ResponseEntity<String> removeFromCart(@PathVariable String id, Principal principal){
        return new ResponseEntity<>(shoppingCartService.removeFromCart(id, principal.getName()), HttpStatus.OK);
    }

    @PatchMapping("/clear-cart")
    public ResponseEntity<String> clearCart(Principal principal){
        return new ResponseEntity<>(shoppingCartService.clearCart(principal.getName()), HttpStatus.OK);
    }

    @PatchMapping("/update-product-quantity/{id}")
    public ResponseEntity<String> updateQuantity(Principal principal, @PathVariable String id, @RequestBody Integer quantity){
        return new ResponseEntity<>(shoppingCartService.updateProductQuantityInShoppingCart(principal.getName(), id, quantity), HttpStatus.OK);
    }

    @GetMapping("/get-total")
    public ResponseEntity<Double> getTotal(Principal principal){
        return new ResponseEntity<>(shoppingCartService.calculateTotal(principal.getName()), HttpStatus.OK);
    }
}

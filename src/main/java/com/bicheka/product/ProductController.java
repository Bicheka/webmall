package com.bicheka.product;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import lombok.AllArgsConstructor;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@AllArgsConstructor
@RestController
@RequestMapping("/product")
public class ProductController {
    private ProductService productService;

    @PostMapping("/create_product")
    public ResponseEntity<Product> saveProduct(@RequestBody Product product, Principal principal) {
        return new ResponseEntity<>(productService.saveProduct(product, principal.getName()), HttpStatus.CREATED);
    }

    

    @GetMapping("/get_product_by_id/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable String id){
        return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);
    }

    @GetMapping("/get_all_products")
    public ResponseEntity<List<Product>> getAllProducts(){
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @DeleteMapping("/delete_product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable String id, Principal principal){
        return new ResponseEntity<>(productService.deleteProduct(id, principal.getName()), HttpStatus.OK);
    }
}

package com.bicheka.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bicheka.POJO.Store;
import com.bicheka.service.StoreService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@AllArgsConstructor
@RestController
@RequestMapping("/store")
public class StoreController{
    
    private StoreService storeService;

    @PostMapping("/create_store")
    public ResponseEntity<Store> createStore(@RequestBody Store store, Principal principal) {
        return  new ResponseEntity<Store>(storeService.createStore(store, principal), HttpStatus.CREATED);
    }

    @GetMapping("/get_store/{storename}")
    public ResponseEntity<Store> getStore(@PathVariable String storename){
        return new ResponseEntity<>(storeService.getStoreByName(storename), HttpStatus.OK);
    }

    @GetMapping("/get_all_stores")
    public ResponseEntity<List<Store>> getAlStores(){
        return new ResponseEntity<>(storeService.getAllStores(), HttpStatus.OK);
    }

    @DeleteMapping("/delete_store/{storename}")
    public ResponseEntity<Void> deleteStore(@PathVariable String storename){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/rename_store/{storename}")
    public ResponseEntity<Store> renameStore(@PathVariable String storename, @RequestBody String newName) {
        return new ResponseEntity<>(storeService.renameStore(storename, newName), HttpStatus.OK);
    }
    
}

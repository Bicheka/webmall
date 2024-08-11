package com.webmall.store;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public HttpStatus createStore(@RequestBody Store store, Principal principal) {
        HttpStatus status = storeService.createStore(store, principal);
        return status;
    }

    @GetMapping("/get_store/{storeId}")
    public ResponseEntity<Store> getStore(@PathVariable String storeId){
        return new ResponseEntity<>(storeService.getStoreById(storeId), HttpStatus.OK);
    }

    @GetMapping("/get_all_stores")
    public ResponseEntity<List<Store>> getAlStores(){
        return new ResponseEntity<>(storeService.getAllStores(), HttpStatus.OK);
    }

    @DeleteMapping("/delete_store/{id}")
    public ResponseEntity<String> deleteStoreById(@PathVariable String id, Principal principal){
        String email = principal.getName();
        
        return new ResponseEntity<>(storeService.deleteStoreById(id, email), HttpStatus.OK);
    }

    @PutMapping("/rename_store/{storename}")
    public ResponseEntity<Store> renameStore(@PathVariable String storename, @RequestBody String newName) {
        return new ResponseEntity<>(storeService.renameStore(storename, newName), HttpStatus.OK);
    }
    
}

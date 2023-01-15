package com.bicheka.controller;

import java.util.List;

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
public class StoreController {
    
    private StoreService storeService;

    @PostMapping("/create_store")
    public Store createStore(@RequestBody Store store) {
        return  storeService.createStore(store);
    }

    @GetMapping("/get_store/{id}")
    public Store getStore(@PathVariable Long id){
        return storeService.getStore(id);
    }

    @GetMapping("/get_all_stores")
    public List<Store> getAlStores(){
        return storeService.getAllStores();
    }

    @DeleteMapping("/delete_store/{id}")
    public void deleteStore(@PathVariable Long id){
        storeService.deleteStore(id);
    }

    @PutMapping("/rename_store/{id}")
    public Store putMethodName(@PathVariable Long id, @RequestBody String newName) {
        return storeService.renameStore(id, newName);
    }
    
}

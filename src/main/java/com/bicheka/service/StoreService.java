package com.bicheka.service;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.bicheka.POJO.Store;

public interface StoreService {
    HttpStatus createStore(Store store, Principal principal);
    Store getStoreById(String id);
    List<Store> getUserStores(String email);
    List<Store> getAllStores();
    void deleteStore(String storename);
    String deleteStoreById(String id, String userEmail);//TODO: need optimization
    Store renameStore(String storename, String newName);
}

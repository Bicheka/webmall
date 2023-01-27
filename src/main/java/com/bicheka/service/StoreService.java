package com.bicheka.service;

import java.security.Principal;
import java.util.List;

import com.bicheka.POJO.Store;

public interface StoreService {
    Store createStore(Store store, Principal principal);
    Store getStoreByName(String name);
    List<Store> getUserStores(String email);
    List<Store> getAllStores();
    void deleteStore(String storename);
    void deleteStoreById(String id);
    void deleteUserStores(String email);
    Store renameStore(String storename, String newName);
}

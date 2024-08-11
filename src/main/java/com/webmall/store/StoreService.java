package com.webmall.store;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;

public interface StoreService {
    HttpStatus createStore(Store store, Principal principal);
    Store getStoreById(String id);
    List<Store> getAllStores();
    String deleteStoreById(String id, String userEmail);//TODO: need optimization
    Store renameStore(String storename, String newName);
}

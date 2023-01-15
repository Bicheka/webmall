package com.bicheka.service;

import java.util.List;

import com.bicheka.POJO.Store;

public interface StoreService {
    public Store createStore(Store store);

    public Store getStore(Long id);

    public List<Store> getAllStores();

    public void deleteStore(Long id);

    public Store renameStore(Long id, String newName);
}

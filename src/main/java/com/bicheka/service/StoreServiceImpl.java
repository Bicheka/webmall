package com.bicheka.service;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bicheka.POJO.Store;
import com.bicheka.repository.StoreRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StoreServiceImpl implements StoreService{

    private StoreRepository storeRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public Store createStore(Store store) {
        store.setPassword(bCryptPasswordEncoder.encode(store.getPassword()));
        return storeRepository.save(store);
    }

    @Override
    public void deleteStore(String id) {
        storeRepository.deleteById(id);  
    }

    @Override
    public Store getStoreByName(String name) {
        return storeRepository.findStoreByName(name);
    }

    @Override
    public List<Store> getAllStores() {
        List<Store> stores = storeRepository.findAll(); 
        return  stores;
    }

    @Override
    public Store renameStore(String storename, String newName) {
        Store store = getStoreByName(storename);
        store.setStoreName(newName);
        return store;
    }

}

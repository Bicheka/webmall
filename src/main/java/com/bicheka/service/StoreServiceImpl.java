package com.bicheka.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bicheka.POJO.Store;
import com.bicheka.exeption.EntityNotFoundException;
import com.bicheka.repository.StoreRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StoreServiceImpl implements StoreService{

    private StoreRepository storeRepository;

    @Override
    public Store createStore(Store store) {
        return storeRepository.save(store);
    }

    @Override
    public void deleteStore(Long id) {
        storeRepository.deleteById(id);  
    }

    @Override
    public Store getStore(Long id) {
        Optional<Store> store = storeRepository.findById(id);
        return unwrapStore(store, 404L);
    }

    static Store unwrapStore(Optional<Store> entity, Long id) {
        if (entity.isPresent()) return entity.get();
        else throw new EntityNotFoundException(id, Store.class);
    }

    @Override
    public List<Store> getAllStores() {
        List<Store> stores = storeRepository.findAll();
        return  stores;
    }

    @Override
    public Store renameStore(Long id, String newName) {
        Store store = getStore(id);
        store.setStorename(newName);
        return store;
    }
}

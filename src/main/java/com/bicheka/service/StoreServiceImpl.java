package com.bicheka.service;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.bicheka.POJO.Role;
import com.bicheka.POJO.Store;
import com.bicheka.POJO.User;
import com.bicheka.repository.StoreRepository;


import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StoreServiceImpl implements StoreService{

    private StoreRepository storeRepository;

    private MongoTemplate mongoTemplate;
    

    @Override
    public Store createStore(Store store, String email) {

        store.setUserEmail(email);
        storeRepository.insert(store);

        //THIS NEEDS TO BE MODIFIED SO IT DOES NOT UPDATE THE USER TO STORE EVRY TIME A STORE IS CREATED, WE JUST NEED IT ONCE
        mongoTemplate.update(User.class)
            .matching(Criteria.where("email").is(email))
            .apply(new Update().set("role", Role.STORE))
            .first();
        
        //update the user with id -> "userId" and push a store into its property "storeIds"
        mongoTemplate.update(User.class)
            .matching(Criteria.where("email").is(email))
            .apply(new Update().push("storeIds", store))
            .first(); //first() makes sure to get only one user, the first one to find
        
        return store;
    }

    @Override
    public void deleteStore(String id) {
        storeRepository.deleteById(id);  
    }

    @Override
    public void deleteUserStores(String email) {
        
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

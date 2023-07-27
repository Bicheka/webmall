package com.bicheka.service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bicheka.POJO.Product;
import com.bicheka.POJO.Role;
import com.bicheka.POJO.Store;
import com.bicheka.POJO.User;
import com.bicheka.exeption.EntityNotFoundException;
import com.bicheka.repository.StoreRepository;


import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StoreServiceImpl implements StoreService{

    private StoreRepository storeRepository;

    private MongoTemplate mongoTemplate;
    private UserService userService;

    @Override
    public HttpStatus createStore(Store store, Principal principal) {

        String email = principal.getName();

        boolean storeExists = storeRepository.findStoreByName(store.getStoreName() ) != null;

        //if the role of the user is not STORE change it to the role of STORE
        if(Role.STORE != userService.getUserByEmail(email).getRole()){
            userService.updateRole(email, Role.STORE);
        }
        
        store.setUserEmail(email);
        storeRepository.insert(store);
        
        if(!storeExists){
            try {
                //update the user with id -> "userId" and push a store into its property "storeIds"
                mongoTemplate.update(User.class)
                .matching(Criteria.where("email").is(email))
                .apply(new Update().push("storeIds", store))
                .first(); //first() makes sure to get only one user, the first one to find
                return HttpStatus.CREATED;
            } catch (Exception e) {
                return HttpStatus.CONFLICT;
            }
        }
        else{
            return HttpStatus.CONFLICT;
        }
    }

    @Override
    public List<Store> getUserStores(String email) {
        Query query = Query.query(Criteria.where("userEmail").is(email));
        return mongoTemplate.find(query, Store.class);
    }

    @Override
    public void deleteStore(String id) {
        storeRepository.deleteById(id);  
    }

    @Override
    public String deleteStoreById(String id, String userEmail) {
        Query storeQuery = Query.query(Criteria.where("id").is(id));
        Store store = mongoTemplate.findOne(storeQuery, Store.class);

        if(store == null) return "Store not found";

        if(!store.getUserEmail().equals(userEmail)){// if who make the request is not the owner of the store deny the request
            return "The store with id: " + id + " is not owned by you";
        }
        String email = store.getUserEmail();
        
        //delete asociated stores
        mongoTemplate.remove( storeQuery, Store.class);

        //update user 
        // TODO: Needs optimization adding a Document reference on the entity to its parent to save this query
        Query userQuery = Query.query(Criteria.where("email").is(email));
        User user = mongoTemplate.findOne(userQuery, User.class);
        //by saving the user it gets updated
        mongoTemplate.save(user);

        Query productQuery = Query.query(Criteria.where("storeId").is(id));
        mongoTemplate.remove(productQuery, Product.class);

        if(userService.getUserByEmail(email).getStoreIds().size() == 0){
            userService.updateRole(email, Role.USER);
        }

        return "Store deleted";
    }

    @Override
    public Store getStoreById(String storeId) {
        Optional<Store> store = storeRepository.findById(storeId);
        return unwrapUser(store, storeId);
    }

    //unwarps the optional and throws an exception if the store is not present
    static Store unwrapUser(Optional<Store> store, String id) {
        if (store.isPresent()) return store.get();
        else throw new EntityNotFoundException(id, Store.class);
    }

    @Override
    public List<Store> getAllStores() {
        List<Store> stores = storeRepository.findAll(); 
        return  stores;
    }

    @Override
    public Store renameStore(String id, String newName) {
        Store store = getStoreById(id);
        store.setStoreName(newName);
        return store;
    }

}

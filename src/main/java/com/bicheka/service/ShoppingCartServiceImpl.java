package com.bicheka.service;

import java.util.Optional;


import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.bicheka.POJO.Product;
import com.bicheka.POJO.User;
import com.bicheka.exeption.EntityNotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService{

    MongoTemplate mongoTemplate;

    @Override
    public String addToCart(String id, String email) {
        Query query = Query.query(Criteria.where("id").is(id));
        Product product = mongoTemplate.findOne(query, Product.class);
        mongoTemplate.update(User.class)
            .matching(Criteria.where("email").is(email))
            .apply(new Update().push("shoppingCart", product))
            .first();
        return "product added to shopping cart";
    }

    @Override
    public String removeFromCart(String id, String email) {

        Query query = Query.query(Criteria.where("id").is(id));
        Product product = mongoTemplate.findOne(query, Product.class);

        mongoTemplate.update(User.class)
        .matching(Criteria.where("email").is(email))
        .apply(new Update().pull("shoppingCart", product))
        .first();

        return "item removed from cart";
    }

    @Override
    public String clearCart() {
       
        return null;
    }

    @Override
    public String buyCartItems() {
        
        return "This method is not working yet";
    }

    static User unwrapUser(Optional<User> entity, String id) {
        if (entity.isPresent()) return entity.get();
        else throw new EntityNotFoundException(id, User.class);
    }
    
}

package com.bicheka.service;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;


import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.bicheka.POJO.CartItem;
import com.bicheka.POJO.Product;
import com.bicheka.POJO.User;
import com.bicheka.exeption.EntityNotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService{

    MongoTemplate mongoTemplate;

    @Override
    public void addProductToShoppingCart(String email, String productId) {
        
        Query query = Query.query(Criteria.where("email").is(email));
        User user = mongoTemplate.findOne(query, User.class);
        Product product = mongoTemplate.findById(productId, Product.class);
        if (user != null && product != null) {
            List<CartItem> shoppingCart = user.getShoppingCart();
            if (shoppingCart == null) {
              shoppingCart = new ArrayList<>();
              user.setShoppingCart(shoppingCart);
            }

            boolean productFound = false;
            for (CartItem pq : shoppingCart) {
                if (pq.getProduct().getId().equals(productId)) {
                    pq.setQuantity(pq.getQuantity() + 1);
                    productFound = true;
                    break;
                }
            }
            if (!productFound) {
                shoppingCart.add(new CartItem(product, 1));
            }
            
            
            mongoTemplate.save(user);
        } 
    }

    //update the the shopping cart by a given quantity
    @Override
    public void updateProductQuantityInShoppingCart(String email, String productId, Integer quantityChange) {
        
        Query query = Query.query(Criteria.where("email").is(email));
        User user = mongoTemplate.findOne(query, User.class);
        if (user != null) {
            List<CartItem> shoppingCart = user.getShoppingCart();
            if (shoppingCart != null) {
            for (CartItem pq : shoppingCart) {
                if (pq.getProduct().getId().equals(productId)) {
                int newQuantity = pq.getQuantity() + quantityChange;
                if (newQuantity >= 0) {
                    pq.setQuantity(newQuantity);
                } else {
                    pq.setQuantity(0); //if the quantity is less than 0 set it to 0
                }
                break;
                }
            }
            }
            mongoTemplate.save(user);
        }
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
    public String clearCart(String email) {

        // Query query = Query.query(Criteria.where("email").is(email));
        // Object[] list = mongoTemplate.findOne(query, User.class).getShoppingCart().toArray();

        // mongoTemplate.update(User.class)
        // .matching(Criteria.where("email").is(email))
        // .apply(new Update().pullAll("shoppingCart", list))
        // .first();

        return "all items in the shopping cart have being deleted";
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

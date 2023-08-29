package com.bicheka.cart;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.bicheka.product.Product;
import com.bicheka.user.User;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService{

    MongoTemplate mongoTemplate;

    @Override
    public List<CartItem> getShoppingCart(String email) {
        Query query = Query.query(Criteria.where("email").is(email));
        User user = mongoTemplate.findOne(query, User.class);


        if (user != null) {
            
            List<CartItem> cartItems = user.getShoppingCart();

            Iterator<CartItem> iter = cartItems.iterator();
            while (iter.hasNext()) {
                CartItem item = iter.next();
                if (item.getProduct() == null) {
                    iter.remove();
                }
            }

            user.setShoppingCart(cartItems);

            mongoTemplate.save(user);
            return user.getShoppingCart();
            
        }
        return null;
    }

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
    public String updateProductQuantityInShoppingCart(String email, String productId, Integer quantityChange) {
        
        boolean isOnCart = false;

        Query query = Query.query(Criteria.where("email").is(email));
        User user = mongoTemplate.findOne(query, User.class);
        if (user != null) {
            List<CartItem> shoppingCart = user.getShoppingCart();
            if (shoppingCart != null) {
                for (CartItem pq : shoppingCart) {
                    if (pq.getProduct().getId().equals(productId)) {
                        isOnCart = true;
                        
                        if (quantityChange >= 0) {
                            pq.setQuantity(quantityChange);
                        } else {
                            shoppingCart.remove(pq);
                        }
                        break;
                    }
                }
                if(isOnCart == false){
                    return "Item not found on cart";
                }
            }
            mongoTemplate.save(user);
        }

        return "item updated";
    }

    //remove an item from the cart by id
    @Override
    public String removeFromCart(String id, String email) {

        Query query = Query.query(Criteria.where("email").is(email));
        User user = mongoTemplate.findOne(query, User.class);

        if (user == null) {
            return "user not found";
        }

        List<CartItem> shoppingCart = user.getShoppingCart();
        
        Iterator<CartItem> iter = shoppingCart.iterator();
        while (iter.hasNext()) {
            CartItem item = iter.next();
            if (item.getProduct().getId().equals(id)) {
                iter.remove();
                mongoTemplate.save(user);
                return "item removed from cart";
            }
        }
        return "item not found in the shopping cart";
    }

    //remove all items from cart
    @Override
    public String clearCart(String email) {

        Query query = Query.query(Criteria.where("email").is(email));
        User user = mongoTemplate.findOne(query, User.class);
        
        if (user == null) {
            return "user not found";
        }

        user.getShoppingCart().clear();
        mongoTemplate.save(user);
        return "all items in the shopping cart have being deleted";
    }

    @Override
    public double calculateTotal(String email) {
        
        double total = 0;

        Query query = Query.query(Criteria.where("email").is(email));
        User user = mongoTemplate.findOne(query, User.class);

        if (user == null) {
            return 0;
        }

        List<CartItem> shoppingCart = user.getShoppingCart();
        for(CartItem i : shoppingCart){
            total = total + (i.getProduct().getPrice() * i.getQuantity());
        }
        return total;
    }

    //it will be able to buy all items in the cart
    @Override
    public String buyCartItems() {
        
        return "This method is not working yet";
    }  
}

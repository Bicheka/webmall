package com.webmall.user;

import java.util.Optional;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.webmall.email.EmailService;
import com.webmall.exeption.EntityNotFoundException;
import com.webmall.product.Product;
import com.webmall.store.Store;

import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
    private MongoTemplate mongoTemplate;
    private EmailService emailService;

    @Override
    public User getUserByName(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return unwrapUser(user, null);
    }

    @Override
    public User getUserByEmail(String email){
        email = email.toLowerCase();

        Optional<User> user = userRepository.findByEmail(email);
        return unwrapUser(user, email);
    }

    @Override
    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setEmail(user.getEmail().toLowerCase()); // email to lower case
        mongoTemplate.save(user);
        emailService.sendEmail(user.getEmail(), "Confirm your email", "Please confirm your email address");
        return user;
    }

    

    @Override
    public void confirmEmail(String email) {
        email = email.toLowerCase();
        mongoTemplate.update(User.class)
            .matching(Criteria.where("email").is(email))
            .apply(new Update().set("emailConfirmed", true))
            .first();
        System.out.println("Email confirmed");
    }

    @Override
    public void deleteAccount(String email){
        email = email.toLowerCase();
        Query query = Query.query(Criteria.where("userEmail").is(email));

        // TODO: optimize
        //remove all stores and for each store remove all products
        mongoTemplate.findAllAndRemove( query, Store.class)
            .forEach(x ->{ Query productQuery = Query.query(Criteria.where("storeId").is(x.getId()));
            mongoTemplate.remove(productQuery, Product.class);});
        
        //delete asociated stores
        userRepository.deleteByEmail(email);//delete user
    }

    @Override
    public UserDetails getUserDetailsByName(String username){
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new EntityNotFoundException());
    }

    @Override
    public void updateRole(String email, UserRole role) {
        mongoTemplate.update(User.class)
            .matching(Criteria.where("email").is(email))
            .apply(new Update().set("role", role))
            .first();
    }

    static User unwrapUser(Optional<User> entity) {
        if (entity.isPresent()) return entity.get();
        else throw new EntityNotFoundException();
    }

    static User unwrapUser(Optional<User> entity, String id) {
        if (entity.isPresent()) return entity.get();
        else throw new EntityNotFoundException(id, User.class);
    }
    
}

package com.bicheka.service;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bicheka.POJO.User;
import com.bicheka.exeption.EntityNotFoundException;
import com.bicheka.repository.UserRepository;

import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;

    // @Override
    // public User getUser(String id) {
    //     Optional<User> user = userRepository.findById(id);
    //     return unwrapUser(user, null);
    // }

    @Override
    public User getUserByName(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return unwrapUser(user, null);
    }

    @Override
    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        
        return userRepository.save(user);
    }

    static User unwrapUser(Optional<User> entity, Long id) {
        if (entity.isPresent()) return entity.get();
        else throw new EntityNotFoundException(id, User.class);
    }
    
}

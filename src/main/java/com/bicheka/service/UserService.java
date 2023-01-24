package com.bicheka.service;

import org.springframework.security.core.userdetails.UserDetails;

import com.bicheka.POJO.User;

public interface UserService {
    // User getUser(Long id);
    User getUserByName(String username);
    UserDetails getUserDetailsByName(String username);
    User getUserByEmail(String email);
    User saveUser(User user);
    void deleteAccount(String email);
    void updateRole(String email);
}
package com.bicheka.service;

import com.bicheka.POJO.User;

public interface UserService {
    // User getUser(Long id);
    User getUserByName(String username);
    User saveUser(User user);
}
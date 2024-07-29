package com.webmall.user;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
    // User getUser(Long id);
    User getUserByName(String username);
    UserDetails getUserDetailsByName(String username);
    User getUserByEmail(String email);
    User saveUser(User user);
    void confirmEmail(String email);
    void deleteAccount(String email);
    void updateRole(String email, UserRole role);
}
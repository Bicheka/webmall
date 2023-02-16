package com.bicheka.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bicheka.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class EmailController {
    
    private UserService userService;

    @GetMapping("/confirm")
    public String confirmEmail(@RequestParam String email) {
        userService.confirmEmail(email);
        return "Email confirmed successfully";
    }
}

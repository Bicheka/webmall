package com.bicheka.email;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bicheka.user.UserService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/confirm_email")
public class EmailController {
    
    private UserService userService;

    @GetMapping
    public String confirmEmail(@RequestParam String email) {
        userService.confirmEmail(email);
        return "Email confirmed successfully";
    }
}

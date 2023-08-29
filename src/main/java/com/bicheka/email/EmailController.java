package com.bicheka.email;

import org.springframework.web.bind.annotation.PostMapping;
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

    //TODO: change this to a post request
    //TODO: change the return type to a ResponseEntity
    @PostMapping
    public String confirmEmail(@RequestParam String email) {

        // when the user hits this endpoint, the email is confirmed

        userService.confirmEmail(email);
        return "Email confirmed successfully";
    }
}

package com.bicheka.controller;



import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bicheka.POJO.User;
import com.bicheka.service.UserService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {


    UserService userService;

	@GetMapping("/{username}")
	public ResponseEntity<User> findByName(@PathVariable String username) {
		return new ResponseEntity<>(userService.getUserByName(username), HttpStatus.OK);
	}

	@GetMapping("/get_user_details/{username}")
	public ResponseEntity<UserDetails> getUserDetails(@PathVariable String username) {
		return new ResponseEntity<>(userService.getUserDetailsByName(username), HttpStatus.OK);
	}

	@GetMapping("/getByEmail/{email}")
	public ResponseEntity<User> findByEmail(@PathVariable String email){
		return new ResponseEntity<>(userService.getUserByEmail(email), HttpStatus.OK);
	}
		
	@DeleteMapping("/delete_account")
	public ResponseEntity<String> deleteByEmail(Principal principal) {

		String email = principal.getName(); //it says getName but what is actually getting is the email of the current authenticated user
		userService.deleteAccount(email);
		return new ResponseEntity<>("Account deleted",HttpStatus.OK);
	}

    @PostMapping("/register")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		userService.saveUser(user);
		return new ResponseEntity<>( HttpStatus.CREATED);
	}

}

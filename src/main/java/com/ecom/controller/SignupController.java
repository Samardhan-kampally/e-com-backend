package com.ecom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.dto.SignupDto;
import com.ecom.dto.UserDto;
import com.ecom.service.UserService;

@RestController
@CrossOrigin(allowCredentials = "")
public class SignupController {

	@Autowired
	private UserService userService;
	
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
	@PostMapping("/register")
	public ResponseEntity<?> signupUser(@RequestBody SignupDto signupDto){
		
		signupDto.setEmail(signupDto.getEmail().toLowerCase());
		
		if(userService.hasUserWithEmail(signupDto.getEmail())) {
			return new ResponseEntity<>("User Already Exists",HttpStatus.NOT_ACCEPTABLE);
		}
		
		signupDto.setPassword(encoder.encode(signupDto.getPassword()));
		
		UserDto createdUser = userService.createuser(signupDto);
		
		if(createdUser == null) {
			return new ResponseEntity<>("User not Created!!!",HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(createdUser,HttpStatus.CREATED);
	}
	
	
}

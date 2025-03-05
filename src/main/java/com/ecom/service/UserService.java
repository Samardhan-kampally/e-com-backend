package com.ecom.service;

import com.ecom.dto.SignupDto;
import com.ecom.dto.UserDto;

public interface UserService {

	UserDto createuser(SignupDto signupDto);

	boolean hasUserWithEmail(String email);

}

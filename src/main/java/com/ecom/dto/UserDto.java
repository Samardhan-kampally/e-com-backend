package com.ecom.dto;

import com.ecom.enums.UserRole;

import lombok.Data;

@Data
public class UserDto {

	private String name;

	private String email;

	private String password;

	private UserRole userRole;

	private byte[] img;

	private String bio;
	
	
}

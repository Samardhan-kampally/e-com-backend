package com.ecom.dto;

import lombok.Data;

@Data
public class AuthenticationResponse {

	public AuthenticationResponse(String jwttoken) {
		this.jwttoken=jwttoken;
	}
	
	
	private String jwttoken;
}

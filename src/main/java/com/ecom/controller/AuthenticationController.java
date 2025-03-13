package com.ecom.controller;

import java.io.IOException;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.dto.AuthenticationRequest;
import com.ecom.entities.User;
import com.ecom.repository.UserRepository;
import com.ecom.service.UserService;
import com.ecom.utils.JWTService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class AuthenticationController {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JWTService jwtService;

	public static final String TOKEN_PREFIX = "Bearer ";

	public static final String HEADER_STRING = "Authorization";

	@PostMapping("/login")
	public void createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest,
			HttpServletResponse response) throws IOException, ServletException, BadCredentialsException,
			DisabledException, UsernameNotFoundException, JsonParseException {

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		} catch (BadCredentialsException e) {
			System.out.println("Bad Cred");
			throw new BadCredentialsException("Incorrect username or password");
		} catch (DisabledException de) {
			response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE, "User is not Activated");
			return;
		}
		
		
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

		User user = userRepository.findFirstByEmail(authenticationRequest.getUsername());

		final String jwt = jwtService.generateToken(authenticationRequest.getUsername());

		System.out.println(jwt);

//		return new AuthenticationResponse(jwt);

		response.getWriter()
				.write(new JSONObject().put("userId", user.getId()).put("role", user.getUserRole()).toString());

		response.addHeader("Access-Control-Expose-Headers", "Authorization");
		response.addHeader("Access-Control-Allow-Headers",
				"Authorization,X-PINGGOTHER,Origin,X-Requested-With,Content-Type,Accept,X-Customheader");
		response.addHeader(HEADER_STRING, TOKEN_PREFIX+jwt);
	}

}

package com.ecom.entities;

import java.time.LocalDateTime;

import com.ecom.dto.UserDto;
import com.ecom.enums.UserRole;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String email;

	private String password;

	private UserRole userRole;

	private byte[] img;

	private String bio;

	private LocalDateTime createdTime;

	private LocalDateTime updatedTime;

	public UserDto getUserDto() {

		UserDto userDto = new UserDto();
		userDto.setName(name);
		userDto.setEmail(email);
		userDto.setPassword(password);
		userDto.setUserRole(userRole);
		userDto.setBio(bio);
		userDto.setImg(img);

		return userDto;

	}

}

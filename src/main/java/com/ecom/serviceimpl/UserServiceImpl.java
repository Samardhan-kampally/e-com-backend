package com.ecom.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecom.dto.SignupDto;
import com.ecom.dto.UserDto;
import com.ecom.entities.User;
import com.ecom.enums.UserRole;
import com.ecom.repository.UserRepository;
import com.ecom.service.UserService;

import jakarta.annotation.PostConstruct;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@PostConstruct
	public void createAdminAccount() {

		User adminAccount = userRepository.findByUserRole(UserRole.ADMIN);
		if (adminAccount == null) {
			User user = new User();

			user.setUserRole(UserRole.ADMIN);
			user.setEmail("admin@gmail.com");
			user.setPassword(new BCryptPasswordEncoder().encode("Admin"));
			user.setName("admin");

			userRepository.save(user);
		}

	}

	@Override
	public UserDto createuser(SignupDto signupDto) {

		User user = new User();

		user.setName(signupDto.getName());
		user.setEmail(signupDto.getEmail());
		user.setPassword(signupDto.getPassword());
		user.setUserRole(UserRole.USER);

		User userCreated = userRepository.save(user);

		return DaoToDto(userCreated);

	}

	public User DtoToDao(UserDto signupDto) {

		return null;
	}

	public UserDto DaoToDto(User user) {
		UserDto userDto = new UserDto();

		userDto.setName(user.getName());
		userDto.setEmail(user.getEmail());
		userDto.setPassword(user.getPassword());
		userDto.setUserRole(user.getUserRole());
		userDto.setBio(user.getBio());

		return userDto;
	}

	@Override
	public boolean hasUserWithEmail(String email) {
		return userRepository.findFirstByEmail(email) != null;
	}

}

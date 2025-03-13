package com.ecom.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecom.dto.SignupDto;
import com.ecom.dto.UserDto;
import com.ecom.entities.Order;
import com.ecom.entities.User;
import com.ecom.enums.OrderStatus;
import com.ecom.enums.UserRole;
import com.ecom.repository.OrderRepository;
import com.ecom.repository.UserRepository;
import com.ecom.service.UserService;

import jakarta.annotation.PostConstruct;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OrderRepository orderRepository;

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
		
		Order order = new Order();
		order.setPrice(0d);
		order.setOrderStatus(OrderStatus.PENDING);
		order.setUser(userCreated);
		orderRepository.save(order);

		return userCreated.getUserDto();
		
//		return DaoToDto(userCreated);

	}

	

	@Override
	public boolean hasUserWithEmail(String email) {
		return userRepository.findFirstByEmail(email) != null;
	}

}

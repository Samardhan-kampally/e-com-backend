package com.ecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecom.entities.User;
import java.util.List;
import com.ecom.enums.UserRole;



@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findFirstByEmail(String email);
	
	User findByUserRole(UserRole userRole);
}

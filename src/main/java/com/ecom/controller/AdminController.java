package com.ecom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.dto.CategoryDto;
import com.ecom.entities.Category;
import com.ecom.service.AdminService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;
	
	@PostMapping("/category")
	public ResponseEntity<Category> createCategory(@RequestBody CategoryDto categoryDto){
		
		Category createdCategory = adminService.createCategory(categoryDto);
		
		
		
		return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
		
	}
	
}

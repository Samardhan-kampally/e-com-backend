package com.ecom.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom.dto.CategoryDto;
import com.ecom.entities.Category;
import com.ecom.repository.CategoryRepository;
import com.ecom.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public Category createCategory(CategoryDto categoryDto) {
		
		Category category = new Category();
		
		category.setName(categoryDto.getName());
		category.setDescription(categoryDto.getDescription());
		
		return categoryRepository.save(category);
	}

	
	
}

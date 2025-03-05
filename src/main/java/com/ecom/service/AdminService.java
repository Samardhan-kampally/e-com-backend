package com.ecom.service;

import com.ecom.dto.CategoryDto;
import com.ecom.entities.Category;

public interface AdminService {

	Category createCategory(CategoryDto categoryDto);

}

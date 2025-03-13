package com.ecom.service;

import java.io.IOException;
import java.util.List;

import com.ecom.dto.CategoryDto;
import com.ecom.dto.ProductDto;
import com.ecom.entities.Category;
import com.ecom.entities.Product;

public interface AdminService {

	Category createCategory(CategoryDto categoryDto);

	boolean hasWithCategoryName(String name);

	List<CategoryDto> getAllCategories();
	
	Product postProduct(Long categoryId,ProductDto productDto) throws IOException;
	
	boolean hasWithProductName(String name);
	
	List<ProductDto> getAllProducts();
	
	void deleteProduct(Long id);
	
	ProductDto getProductById(Long id);
	
	ProductDto updateProduct(Long categoryId,Long productId,ProductDto productDto) throws IOException;

}

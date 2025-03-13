package com.ecom.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.dto.CategoryDto;
import com.ecom.dto.ProductDto;
import com.ecom.entities.Category;
import com.ecom.entities.Product;
import com.ecom.service.AdminService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;

	@PostMapping("/category")
	public ResponseEntity<?> createCategory(@RequestBody CategoryDto categoryDto) {

		if (adminService.hasWithCategoryName(categoryDto.getName())) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Category Already Exists");
		}

		Category createdCategory = adminService.createCategory(categoryDto);

		return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);

	}

	@GetMapping("/categories")
	public ResponseEntity<List<CategoryDto>> getAllCategories() {

		List<CategoryDto> allCategories = adminService.getAllCategories();

		return ResponseEntity.ok(allCategories);
	}

	@PostMapping("/product/{categoryId}")
	public ResponseEntity<?> postProduct(@PathVariable Long categoryId, @ModelAttribute ProductDto productDto)
			throws IOException {

		if (adminService.hasWithProductName(productDto.getName())) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Product Already Exists");
		}

		Product postedProduct = adminService.postProduct(categoryId, productDto);

		return ResponseEntity.status(HttpStatus.CREATED).body(postedProduct);

	}

	@GetMapping("/products")
	public ResponseEntity<List<ProductDto>> getAllProducts() {

		List<ProductDto> allProducts = adminService.getAllProducts();

		return ResponseEntity.ok(allProducts);

	}

	@DeleteMapping("/products/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
		adminService.deleteProduct(id);

		return ResponseEntity.noContent().build();

	}

	@GetMapping("/product/{id}")
	public ResponseEntity<?> getProductById(@PathVariable long id) {

		ProductDto productDto = adminService.getProductById(id);

		if (productDto == null)
			return ResponseEntity.notFound().build();

		return ResponseEntity.ok(productDto);
	}

	@PutMapping("/{categoryId}/product/{productId}")
	public ResponseEntity<?> updateProduct(@PathVariable Long categoryId, @PathVariable Long productId,
			@ModelAttribute ProductDto productDto) throws IOException {

		ProductDto updateProduct = adminService.updateProduct(categoryId, productId, productDto);

		if (updateProduct == null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong");

		return ResponseEntity.ok(updateProduct);

	}
}

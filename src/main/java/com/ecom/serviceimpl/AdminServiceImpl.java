package com.ecom.serviceimpl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom.dto.CategoryDto;
import com.ecom.dto.ProductDto;
import com.ecom.entities.Category;
import com.ecom.entities.Product;
import com.ecom.repository.CategoryRepository;
import com.ecom.repository.ProductRepository;
import com.ecom.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ProductRepository productRepository;

	@Override
	public Category createCategory(CategoryDto categoryDto) {

		Category category = new Category();

		category.setName(categoryDto.getName());
		category.setDescription(categoryDto.getDescription());

		return categoryRepository.save(category);
	}

	public List<CategoryDto> getAllCategories() {

		return categoryRepository.findAll().stream().map(Category::getCategoryDto).collect(Collectors.toList());
	}

	@Override
	public boolean hasWithCategoryName(String name) {

		return categoryRepository.findByName(name) != null;
	}

	@Override
	public Product postProduct(Long categoryId, ProductDto productDto) throws IOException {

		Optional<Category> optionalCategory = categoryRepository.findById(categoryId);

		if (optionalCategory.isPresent()) {

			Product product = new Product();

			product.setName(productDto.getName());
			product.setPrice(productDto.getPrice());
			product.setDescription(productDto.getDescription());

			if (productDto.getImage() != null) {
				product.setImage(productDto.getImage().getBytes());
			}

			product.setCategory(optionalCategory.get());

			return productRepository.save(product);
		}
		return null;
	}

	@Override
	public boolean hasWithProductName(String name) {

		return productRepository.findByName(name) != null;
	}

	@Override
	public List<ProductDto> getAllProducts() {

		return productRepository.findAll().stream().map(Product::getProductDto).collect(Collectors.toList());
	}

	@Override
	public void deleteProduct(Long id) {

		Optional<Product> optionalProduct = productRepository.findById(id);

		if (optionalProduct.isEmpty())
			throw new IllegalArgumentException("Product with id " + id + "not Found");

		productRepository.deleteById(id);

	}

	@Override
	public ProductDto getProductById(Long id) {
		Optional<Product> optionalproduct = productRepository.findById(id);

		if (optionalproduct.isPresent()) {
			Product product = optionalproduct.get();
			return product.getProductDto();
		}

		return null;
	}

	@Override
	public ProductDto updateProduct(Long categoryId, Long productId, ProductDto productDto) throws IOException {
		Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
		Optional<Product> optionalProduct = productRepository.findById(productId);

		if (optionalCategory.isPresent() && optionalProduct.isPresent()) {

			Product product = optionalProduct.get();
			product.setName(productDto.getName());
			product.setDescription(productDto.getDescription());
			product.setPrice(productDto.getPrice());
			product.setCategory(optionalCategory.get());

			if (productDto.getImage() != null)
				product.setImage(productDto.getImage().getBytes());

			Product updatedProduct = productRepository.save(product);
			ProductDto updatedProductDto = new ProductDto();
			updatedProductDto.setId(updatedProduct.getId());

			return updatedProductDto;
		}

		return null;
	}
}

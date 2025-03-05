package com.ecom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

//	@Autowired
//	private ProductService productService;
//
//	@PostMapping
//	@PreAuthorize("hasRole('ADMIN')")
//	public ResponseEntity<Product> createProduct(@RequestBody Product product) {
//		return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(product));
//	}
//
//	@GetMapping
//	public ResponseEntity<List<Product>> getAllProducts() {
//		return ResponseEntity.ok(productService.getAllProducts());
//	}

}

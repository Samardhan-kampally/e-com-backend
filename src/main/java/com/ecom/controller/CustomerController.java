package com.ecom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.dto.CartItemDto;
import com.ecom.dto.OrderDto;
import com.ecom.dto.PlaceOrderDto;
import com.ecom.dto.ProductDto;
import com.ecom.service.CustomerService;

import io.jsonwebtoken.Header;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customer")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@GetMapping("/products")
	public ResponseEntity<List<ProductDto>> getAllProducts() {

		List<ProductDto> allProducts = customerService.getAllProducts();

		return ResponseEntity.ok(allProducts);

	}

	@PostMapping("/cart")
	public ResponseEntity<?> postProductToCart(@RequestBody CartItemDto cartItemDto) {

		return customerService.addProductToCart(cartItemDto);
	}

	@GetMapping("/cart/{userId}")
	public ResponseEntity<OrderDto> getCartByUserId(@PathVariable Long userId) {
		OrderDto orderDto = customerService.getCartByUserId(userId);

		if (orderDto == null)
			return ResponseEntity.notFound().build();

		return ResponseEntity.ok(orderDto);

	}

	@PostMapping("/{userId}/deduct/{productId}")
	public ResponseEntity<OrderDto> addMinusOnproduct(@PathVariable Long userId, @PathVariable Long productId) {
		OrderDto orderDto = customerService.addMinusOnProduct(userId, productId);
		return ResponseEntity.ok(orderDto);
	}

	@PostMapping("/{userId}/add/{productId}")
	public ResponseEntity<OrderDto> addPlusOnproduct(@PathVariable Long userId, @PathVariable Long productId) {
		System.out.println(userId+"--"+productId);
		OrderDto orderDto = customerService.addPlusOnProduct(userId, productId);
		return ResponseEntity.ok(orderDto);
	}
	
	
	@PostMapping("/placeOrder")
	public ResponseEntity<OrderDto> placeOrder(@RequestBody PlaceOrderDto placeOrderDto){
		OrderDto orderDto = customerService.placeOrder(placeOrderDto);
		if(orderDto == null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		
		return ResponseEntity.status(HttpStatus.CREATED).body(orderDto);
	}
}

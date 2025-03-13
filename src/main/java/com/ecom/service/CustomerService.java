package com.ecom.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.ecom.dto.CartItemDto;
import com.ecom.dto.OrderDto;
import com.ecom.dto.PlaceOrderDto;
import com.ecom.dto.ProductDto;

public interface CustomerService {

	List<ProductDto> getAllProducts();
	
	ResponseEntity<?> addProductToCart(CartItemDto cartItemDto);
	
	OrderDto getCartByUserId(Long userId);
	
	OrderDto addMinusOnProduct(Long userId,Long productId);
	
	OrderDto addPlusOnProduct(Long userId,Long productId);
	
	OrderDto placeOrder(PlaceOrderDto placeOrderDto);
	
}

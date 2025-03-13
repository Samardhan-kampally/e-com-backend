package com.ecom.serviceimpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ecom.dto.CartItemDto;
import com.ecom.dto.OrderDto;
import com.ecom.dto.PlaceOrderDto;
import com.ecom.dto.ProductDto;
import com.ecom.entities.CartItems;
import com.ecom.entities.Order;
import com.ecom.entities.Product;
import com.ecom.entities.User;
import com.ecom.enums.OrderStatus;
import com.ecom.repository.CartItemsRepository;
import com.ecom.repository.OrderRepository;
import com.ecom.repository.ProductRepository;
import com.ecom.repository.UserRepository;
import com.ecom.service.CustomerService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CartItemsRepository cartItemsRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public List<ProductDto> getAllProducts() {
		
		return productRepository.findAll().stream().map(Product::getProductDto).collect(Collectors.toList());
	}

	@Override
	public ResponseEntity<?> addProductToCart(CartItemDto cartItemDto) {
		
		Order pendingOrder = orderRepository.findByUserIdAndOrderStatus(cartItemDto.getUserId(),OrderStatus.PENDING);
		Optional<CartItems> optionalCartItems = cartItemsRepository.findByUserIdAndProductIdAndOrderId(cartItemDto.getUserId(),
				cartItemDto.getProductId(),
				pendingOrder.getId());
		
		if(optionalCartItems.isPresent()) {
			CartItemDto productExistInCart = new CartItemDto();
			productExistInCart.setProductId(null);
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Product Already Exists in Cart");
		}else {
			Optional<Product> optionalProduct = productRepository.findById(cartItemDto.getProductId());
			Optional<User> optionalUser = userRepository.findById(cartItemDto.getUserId());
			
			if(optionalProduct.isPresent() && optionalUser.isPresent()) {
				
				Product product = optionalProduct.get();
				User user = optionalUser.get();
				
				CartItems cartItem = new CartItems();
				System.out.println(cartItem.getId());
				cartItem.setProduct(product);
				cartItem.setUser(user);
				cartItem.setQuantity(1L);
				cartItem.setOrder(pendingOrder);
				cartItem.setPrice(product.getPrice());
				System.out.println(cartItem.getId());
				try {
					System.out.println("try");
					CartItems updatedCart = cartItemsRepository.save(cartItem);
					System.out.println("after cart repo");
					pendingOrder.setPrice(pendingOrder.getPrice() + cartItem.getPrice());
					pendingOrder.getCartItems().add(cartItem);
					orderRepository.save(pendingOrder);
					
					CartItemDto updatedCartItemDto = new CartItemDto();
					updatedCartItemDto.setId(updatedCart.getId());
					
					return ResponseEntity.status(HttpStatus.CREATED).body(updatedCartItemDto);
				}catch (Exception e) {
					e.printStackTrace();
	                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                                     .body("Error adding product to cart");
				}
				
				
			}else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product Not Found");
			}
		}
	}

	@Override
	public OrderDto getCartByUserId(Long userId) {
		Order pendingOrder = orderRepository.findByUserIdAndOrderStatus(userId, OrderStatus.PENDING);
		
		List<CartItemDto> cartItemDtoList = pendingOrder.getCartItems().stream().map(CartItems::getCartItemDto).collect(Collectors.toList());
		
		OrderDto orderDto = new OrderDto();
		orderDto.setCartItemDtoList(cartItemDtoList);
		orderDto.setAmount(pendingOrder.getPrice());
		orderDto.setId(pendingOrder.getId());
		orderDto.setOrderStatus(pendingOrder.getOrderStatus());
		
		return orderDto;
	}

	@Override
	public OrderDto addMinusOnProduct(Long userId, Long productId) {
		
		Order pendingOrder = orderRepository.findByUserIdAndOrderStatus(userId, OrderStatus.PENDING);
		Optional<Product> optionalProduct = productRepository.findById(productId);
		Optional<CartItems> optionalCartItem = cartItemsRepository.findByUserIdAndProductIdAndOrderId(userId, productId, pendingOrder.getId());
		
		if(optionalCartItem.isPresent() && optionalProduct.isPresent()) {
			CartItems cartItem = optionalCartItem.get();
			cartItem.setQuantity(optionalCartItem.get().getQuantity() - 1);
			pendingOrder.setPrice(pendingOrder.getPrice() - optionalProduct.get().getPrice());
			cartItemsRepository.save(cartItem);
			orderRepository.save(pendingOrder);
			
			return pendingOrder.getOrderDto();
		}
		
		System.out.println(optionalCartItem+"--"+optionalCartItem);
		return null;
	}

	@Override
	public OrderDto addPlusOnProduct(Long userId, Long productId) {
		Order pendingOrder = orderRepository.findByUserIdAndOrderStatus(userId, OrderStatus.PENDING);
		Optional<Product> optionalProduct = productRepository.findById(productId);
		Optional<CartItems> optionalCartItem = cartItemsRepository.findByUserIdAndProductIdAndOrderId(userId, productId, pendingOrder.getId());
		
		if(optionalCartItem.isPresent() && optionalProduct.isPresent()) {
			CartItems cartItem = optionalCartItem.get();
			cartItem.setQuantity(optionalCartItem.get().getQuantity() + 1);
			pendingOrder.setPrice(pendingOrder.getPrice() + optionalProduct.get().getPrice());
			cartItemsRepository.save(cartItem);
			orderRepository.save(pendingOrder);
			
			return pendingOrder.getOrderDto();
		}
		System.out.println(optionalCartItem+"--"+optionalCartItem);
		return null;
	}

	@Override
	public OrderDto placeOrder(PlaceOrderDto placeOrderDto) {
		Order existingOrder = orderRepository.findByUserIdAndOrderStatus(placeOrderDto.getUserId(), OrderStatus.PENDING);
		
		Optional<User> optionalUser = userRepository.findById(placeOrderDto.getUserId());
		if(optionalUser.isPresent()) {
			existingOrder.setOrderStatus(OrderStatus.SUBMITTED);
			existingOrder.setAddress(placeOrderDto.getAddress());
			existingOrder.setDate(new Date());
			existingOrder.setPaymentType(placeOrderDto.getPayment());
			existingOrder.setDescription(placeOrderDto.getOrderDescription());
			existingOrder.setPrice(existingOrder.getPrice());
			orderRepository.save(existingOrder);
			
			Order order = new Order();
			order.setOrderStatus(OrderStatus.PENDING);
			order.setUser(optionalUser.get());
			order.setPrice(0d);
			orderRepository.save(order);
			
			return order.getOrderDto();
		}
		return null;
	}

}

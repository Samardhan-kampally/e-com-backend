package com.ecom.dto;

import java.util.Date;
import java.util.List;

import com.ecom.enums.OrderStatus;

import lombok.Data;

@Data
public class OrderDto {

	private String OrderDescription;
	
	private List<CartItemDto> cartItemDtoList;
	
	private Long id;
	
	private Date date;
	
	private Double amount;
	
	private String address;
	
	private OrderStatus orderStatus;
	
	private String paymentType;
	
	private String username;
}

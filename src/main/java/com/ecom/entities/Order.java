package com.ecom.entities;

import java.util.Date;
import java.util.List;

import com.ecom.dto.OrderDto;
import com.ecom.enums.OrderStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String description;
	
	private String address;
	
	private String paymentType;
	
	private Date date;
	
	private Double price;
	
	private OrderStatus orderStatus;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "user_id",referencedColumnName = "id")
	private User user;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
	private List<CartItems> cartItems;

	public OrderDto getOrderDto() {
		OrderDto orderDto = new OrderDto();
		orderDto.setId(id);
		return orderDto;
	}
}

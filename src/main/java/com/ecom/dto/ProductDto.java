package com.ecom.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Lob;
import lombok.Data;

@Data
public class ProductDto {

	private Long id;

	private String name;

	@Lob
	private String description;

	private Double price;

	private MultipartFile image;

	private byte[] returnedImage;

	private Long categoryId;

	private String categoryName;
	
}

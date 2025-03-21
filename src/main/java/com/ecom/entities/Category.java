package com.ecom.entities;

import com.ecom.dto.CategoryDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "categories")
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	@Lob
	private String description;
	
	public CategoryDto getCategoryDto() {
		
		CategoryDto categoryDto = new CategoryDto();
		
		categoryDto.setId(id);
		categoryDto.setName(name);
		categoryDto.setDescription(description);
		
		return categoryDto;
	}
	
}

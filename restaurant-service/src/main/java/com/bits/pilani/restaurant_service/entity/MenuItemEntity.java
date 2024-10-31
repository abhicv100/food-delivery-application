package com.bits.pilani.restaurant_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name = "MenuItem", schema = "public")
@Entity
@Getter @Setter
public class MenuItemEntity {
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	
	@Column
	int restaurantId;
	
	@Column
	int categoryId;
	
	@Column
	int cuisineId;
	
	@Column
	String name;
	
	@Column
	String description;
	
	@Column
	int price;
	
	@Column
	boolean available;
	
	@Column
	float rating;
	
	@Column
	int preparationTime;
	
	@Column
	boolean pureVeg;

}

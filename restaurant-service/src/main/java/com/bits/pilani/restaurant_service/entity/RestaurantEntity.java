package com.bits.pilani.restaurant_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name = "Restaurant", schema = "public")
@Entity
@Getter @Setter
public class RestaurantEntity {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	
	@Column(nullable = false)
	String name;
	
	@Column(nullable = false)
	String address;
	
	@Column
	String openingHours;
	
	@Column
	String closingHours;
	
	@Column
	float rating;
	
	@Column
	int ownerId;
}
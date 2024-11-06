package com.bits.pilani.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name = "delivery_details", schema = "public")
@Entity
@Getter @Setter
public class DeliveryDetailsEntity {
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	
	@Column
	int orderId;
	
	@Column
	Boolean delivery_status;
	
	@Column
	String delivery_message;
	
	@Column
	String available_person;

}

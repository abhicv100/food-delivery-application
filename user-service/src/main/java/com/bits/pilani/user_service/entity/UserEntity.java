package com.bits.pilani.user_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name="user", schema="public")
@Entity
@Getter @Setter
public class UserEntity {
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	
	@Column(nullable = false)
	String username;
	
	@Column(nullable = false)
	String password;
	
	@Column(nullable = false)
	String fullName;
	
	@Column
	String address;
	
	@Column
	String phone;
	
	@Column
	String email;
	
	@Column
	Integer vehicleTypeId;
	
	@Column(nullable = false)
	Integer roleId;
}

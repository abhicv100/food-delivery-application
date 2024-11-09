package com.bits.pilani.delivery_service.dao;

import java.util.Optional;

import org.springframework.data.repository.ListCrudRepository;

import com.bits.pilani.delivery_service.entity.DeliveryDetailsEntity;

public interface DeliveryDao extends ListCrudRepository<DeliveryDetailsEntity, Integer> {
	
	Optional<DeliveryDetailsEntity> findById(int id);
	
	
}

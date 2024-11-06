package com.bits.pilani.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.ListCrudRepository;

import com.bits.pilani.entity.DeliveryDetailsEntity;

public interface DeliveryDao extends ListCrudRepository<DeliveryDetailsEntity, Integer> {
	
	Optional<List<DeliveryDetailsEntity>> findByOrderId(int orderId);
}

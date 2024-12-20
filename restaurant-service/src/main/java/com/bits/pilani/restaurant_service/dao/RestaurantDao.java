package com.bits.pilani.restaurant_service.dao;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;

import com.bits.pilani.restaurant_service.entity.RestaurantEntity;

public interface RestaurantDao extends ListCrudRepository<RestaurantEntity, Integer> {	
	List<RestaurantEntity> findByOwnerId(int ownerId);
}

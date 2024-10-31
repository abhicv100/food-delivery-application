package com.bits.pilani.restaurant_service.dao;

import org.springframework.data.repository.CrudRepository;

import com.bits.pilani.restaurant_service.entity.CuisineEntity;

public interface CuisineDao extends CrudRepository<CuisineEntity, Integer> {
	
}

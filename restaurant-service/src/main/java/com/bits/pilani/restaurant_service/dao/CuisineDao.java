package com.bits.pilani.restaurant_service.dao;

import org.springframework.data.repository.ListCrudRepository;

import com.bits.pilani.restaurant_service.entity.CuisineEntity;

public interface CuisineDao extends ListCrudRepository<CuisineEntity, Integer> {
	
}

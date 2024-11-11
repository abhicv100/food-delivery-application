package com.bits.pilani.restaurant_service.dao;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;

import com.bits.pilani.restaurant_service.entity.MenuItemEntity;

public interface MenuDao extends ListCrudRepository<MenuItemEntity, Integer> {	
	List<MenuItemEntity> findByRestaurantId(int restaurantId);
}

package com.bits.pilani.restaurant_service.dao;

import org.springframework.data.repository.ListCrudRepository;

import com.bits.pilani.restaurant_service.entity.RestaurantEntity;

public interface RestaurantDao extends ListCrudRepository<RestaurantEntity, Integer> {

}

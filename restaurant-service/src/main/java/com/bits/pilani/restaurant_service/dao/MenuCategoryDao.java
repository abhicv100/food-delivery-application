package com.bits.pilani.restaurant_service.dao;

import org.springframework.data.repository.ListCrudRepository;

import com.bits.pilani.restaurant_service.entity.MenuCategoryEntity;

public interface MenuCategoryDao extends ListCrudRepository<MenuCategoryEntity, Integer> {

}

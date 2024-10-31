package com.bits.pilani.restaurant_service.dao;

import org.springframework.data.repository.CrudRepository;

import com.bits.pilani.restaurant_service.entity.MenuCategoryEntity;

public interface MenuCategoryDao extends CrudRepository<MenuCategoryEntity, Integer> {

}

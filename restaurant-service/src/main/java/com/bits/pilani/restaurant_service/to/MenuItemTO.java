package com.bits.pilani.restaurant_service.to;

import com.bits.pilani.restaurant_service.entity.MenuItemEntity;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MenuItemTO extends MenuItemEntity {	
	private MenuCategoryTO category;
	private CuisineTO cuisine;	
}

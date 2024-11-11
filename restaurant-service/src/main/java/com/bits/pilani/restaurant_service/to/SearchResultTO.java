package com.bits.pilani.restaurant_service.to;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SearchResultTO {	
	RestaurantTO restaurant;
	List<MenuItemTO> menu;
}

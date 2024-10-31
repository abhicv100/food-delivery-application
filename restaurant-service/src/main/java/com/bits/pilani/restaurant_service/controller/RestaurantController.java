package com.bits.pilani.restaurant_service.controller;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bits.pilani.restaurant_service.service.MenuService;
import com.bits.pilani.restaurant_service.service.RestaurantService;
import com.bits.pilani.restaurant_service.to.MenuItemTO;
import com.bits.pilani.restaurant_service.to.RestaurantTO;
import com.bits.pilani.restaurant_service.to.SearchResultTO;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@ResponseBody
@RequestMapping("/restaurant")
public class RestaurantController {
	
	@Autowired
	RestaurantService restaurantService;
	
	@Autowired
	MenuService menuService;
	
    @GetMapping("/{restaurantId}")
    public ResponseEntity<RestaurantTO> getRestaurant(@PathVariable int restaurantId) {
        return ResponseEntity.ok(restaurantService.getRestaurant(restaurantId));
    }

    @PostMapping
    public ResponseEntity<RestaurantTO> createRestaurant(@RequestBody RestaurantTO restaurantTO) {
        return ResponseEntity.ok(restaurantService.createRestaurant(restaurantTO));
    }

    @PutMapping
    public ResponseEntity<RestaurantTO> updateRestaurant(@RequestBody RestaurantTO restaurantTO) {
        return ResponseEntity.ok(restaurantService.updateRestaurant(restaurantTO));
    }

    @DeleteMapping("/{restaurantId}")
    public ResponseEntity<RestaurantTO> deleteRestaurant(@PathVariable int restaurantId) {
    	restaurantService.deleteRestaurant(restaurantId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<RestaurantTO>> getAllResturants(@RequestParam Map<String, String> filter) {
        return ResponseEntity.ok(restaurantService.getAllResturants(filter));
    }

    @GetMapping("/{restaurantId}/menu")
    public ResponseEntity<List<MenuItemTO>> getRestaurantMenu(@PathVariable int restaurantId) {
        return ResponseEntity.ok(menuService.getMenuForRestaurant(restaurantId));
    }
    
    @GetMapping("/menu")
    public ResponseEntity<List<SearchResultTO>> searchAllRestauarntMenu(@RequestParam Map<String, String> filter) {
        return ResponseEntity.ok(restaurantService.searchMenu(filter));
    }
}

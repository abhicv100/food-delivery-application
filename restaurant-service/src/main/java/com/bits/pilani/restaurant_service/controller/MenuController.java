package com.bits.pilani.restaurant_service.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bits.pilani.restaurant_service.service.MenuService;
import com.bits.pilani.restaurant_service.to.MenuItemTO;
import com.bits.pilani.restaurant_service.to.RestaurantTO;
import com.bits.pilani.restaurant_service.to.SearchResultTO;
import com.bits.pilani.to.ResponseTO;
import com.bits.pilani.to.SuccessResponseTO;

@RestController
@ResponseBody
@RequestMapping("/menu")
public class MenuController {

	@Autowired
	MenuService menuService;
	
    @PostMapping
    public ResponseEntity<MenuItemTO> createMenu(@RequestBody MenuItemTO menuTO) {
        return ResponseEntity.ok(menuService.createMenu(menuTO));
    }

    @PutMapping
    public ResponseEntity<MenuItemTO> updateMenu(@RequestBody MenuItemTO menuTO) {
        return ResponseEntity.ok(menuService.updateMenu(menuTO));
    }

    @DeleteMapping("/{menuId}")
    public ResponseEntity<RestaurantTO> deleteMenu(@PathVariable int menuId) {
    	menuService.deleteMenu(menuId);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/search")
    public ResponseEntity<ResponseTO> searchMenu(@RequestParam Map<String, String> filter) {
    	List<SearchResultTO> searchResults = menuService.searchMenu(filter); 
    	return SuccessResponseTO.create(searchResults);
    }

}

package com.bits.pilani.restaurant_service.controller;

import static com.bits.pilani.exception.CustomException.handleException;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.bits.pilani.exception.CustomException;
import com.bits.pilani.restaurant_service.service.MenuService;
import com.bits.pilani.restaurant_service.to.MenuItemTO;
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
    public ResponseEntity<ResponseTO> createMenu(@RequestBody MenuItemTO menuTO) {
    	try {
    		menuService.validateMenuTO(menuTO);
    		menuTO.setId(null);
    		var createdMenuItem = menuService.createMenu(menuTO);
    		return SuccessResponseTO.create(createdMenuItem, HttpStatus.CREATED);
    	} catch(CustomException e) {
    		return handleException(e);
    	}
    }

    @PutMapping("/{menuItemId}")
    public ResponseEntity<ResponseTO> updateMenu(@RequestBody MenuItemTO menuTO, @PathVariable int menuItemId) {
    	try {
    		menuService.checkIfMenuItemIdExist(menuItemId);
    		menuService.validateMenuTO(menuTO);
    		menuTO.setId(menuItemId);
    		var updatedMenuItem = menuService.updateMenu(menuTO);
    		return SuccessResponseTO.create(updatedMenuItem);
    	} catch(CustomException e) {
    		return handleException(e);
    	}
    }

    @DeleteMapping("/{menuItemId}")
    public ResponseEntity<ResponseTO> deleteMenu(@PathVariable int menuItemId) throws CustomException {
    	try {			
    		menuService.checkIfMenuItemIdExist(menuItemId);
    		menuService.deleteMenu(menuItemId);
    		return SuccessResponseTO.create(menuItemId);
		} catch (CustomException e) {
			return handleException(e);
		}
    }
    
    @GetMapping("/search")
    public ResponseEntity<ResponseTO> searchMenu(@RequestParam Map<String, String> filter) {
    	try {
    		List<SearchResultTO> searchResults = menuService.searchMenu(filter); 
    		return SuccessResponseTO.create(searchResults);			
		} catch (CustomException e) {
			return handleException(e);
		}
    }
}

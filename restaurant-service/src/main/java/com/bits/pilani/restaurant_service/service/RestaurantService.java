package com.bits.pilani.restaurant_service.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.bits.pilani.restaurant_service.dao.RestaurantDao;
import com.bits.pilani.restaurant_service.entity.RestaurantEntity;
import com.bits.pilani.restaurant_service.to.MenuItemTO;
import com.bits.pilani.restaurant_service.to.RestaurantTO;
import com.bits.pilani.restaurant_service.to.SearchResultTO;

@Service
public class RestaurantService {
    
	@Autowired
	RestaurantDao restaurantDao;
	
	@Autowired 
	MenuService menuService;
	
	public RestaurantTO getRestaurant(int restaurantId) {
		RestaurantTO restaurantTO = new RestaurantTO();
		RestaurantEntity restaurantEntity = restaurantDao.findById(restaurantId).get();
		BeanUtils.copyProperties(restaurantEntity, restaurantTO);
        return restaurantTO;
    }

    public RestaurantTO createRestaurant(RestaurantTO restaurantTO) {
    	RestaurantEntity restaurantEntity = new RestaurantEntity();
    	BeanUtils.copyProperties(restaurantTO, restaurantEntity);
    	restaurantEntity = restaurantDao.save(restaurantEntity);
    	BeanUtils.copyProperties(restaurantEntity, restaurantTO);
        return restaurantTO;
    }

    public RestaurantTO updateRestaurant(RestaurantTO restaurantTO) {
    	return createRestaurant(restaurantTO);
    }

    public void deleteRestaurant(@PathVariable int restaurantId) {
        restaurantDao.deleteById(restaurantId);
    }

    public List<RestaurantTO> getAllResturants(Map<String, String> filter) {
    	
    	if(filter.containsKey("name")) filter.remove("name");
    	
    	List<RestaurantEntity> restaurantEntities = restaurantDao.findAll();
    	    	
		List<Predicate<RestaurantEntity>> restaurantMatchers = new ArrayList<>();
    	
     	if(filter.containsKey("rating")) {
     		float rating = Float.valueOf(filter.get("rating"));
     		restaurantMatchers.add((restaurantEntity) -> {
     			return restaurantEntity.getRating() >= rating;
     		});
     	}
     	
     	Predicate<RestaurantEntity> restaurantMatcher = restaurantMatchers.stream()
     														.reduce(x -> true, Predicate::and);
     	
     	List<RestaurantTO> result = restaurantEntities.stream().filter((restaurant) -> {
     		List<MenuItemTO> filteredMenu = menuService.getMenuForRestaurant(restaurant.getId(), filter);
         	return restaurantMatcher.test(restaurant) && filteredMenu.size() > 0;
    	}).map((restaurantEntity) -> {
    		RestaurantTO restaurantTO = new RestaurantTO();
    		BeanUtils.copyProperties(restaurantEntity, restaurantTO);
    		return restaurantTO;
    	}).toList();
    	
        return result;
    }
    
    public List<SearchResultTO> searchMenu(Map<String, String> filter) {
    	
    	List<SearchResultTO> searchResults = new ArrayList<>();

    	List<RestaurantEntity> restaurantEntities = restaurantDao.findAll();
    	
     	restaurantEntities.forEach((restaurantEntity) -> {
     		List<MenuItemTO> filteredMenu = menuService.getMenuForRestaurant(restaurantEntity.getId(), filter);     		
     		if(filteredMenu.size() > 0) {
     			SearchResultTO searchResultTO = new SearchResultTO();
        		RestaurantTO restaurantTO = new RestaurantTO();
        		BeanUtils.copyProperties(restaurantEntity, restaurantTO);
     			searchResultTO.setRestaurant(restaurantTO);
     			searchResultTO.setMenu(filteredMenu);
     			searchResults.add(searchResultTO);
     		}
    	});
    	    	
    	return searchResults;
    }
}

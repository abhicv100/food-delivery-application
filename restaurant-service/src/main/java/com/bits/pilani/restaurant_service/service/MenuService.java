package com.bits.pilani.restaurant_service.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.bits.pilani.restaurant_service.dao.CuisineDao;
import com.bits.pilani.restaurant_service.dao.MenuCategoryDao;
import com.bits.pilani.restaurant_service.dao.MenuDao;
import com.bits.pilani.restaurant_service.entity.MenuCategoryEntity;
import com.bits.pilani.restaurant_service.entity.CuisineEntity;
import com.bits.pilani.restaurant_service.entity.MenuItemEntity;
import com.bits.pilani.restaurant_service.to.CuisineTO;
import com.bits.pilani.restaurant_service.to.MenuCategoryTO;
import com.bits.pilani.restaurant_service.to.MenuItemTO;

@Service
public class MenuService {
	
	@Autowired
	MenuDao menuDao;
	
	@Autowired
	MenuCategoryDao menuCategoryDao;
	
	@Autowired
	CuisineDao cuisineDao;
	
	public MenuItemTO getMenu(int  menuId) {
		Optional<MenuItemEntity> maybeMenuEntity = menuDao.findById(menuId);
		if(maybeMenuEntity.isEmpty()) {
			return new MenuItemTO();
		}
        return convertMenuEntityToMenuTO(maybeMenuEntity.get());
    }

    public MenuItemTO createMenu(MenuItemTO menuTO) {
    	MenuItemEntity menuEntity = new MenuItemEntity();
    	BeanUtils.copyProperties(menuTO, menuEntity);
    	menuEntity = menuDao.save(menuEntity);
        return convertMenuEntityToMenuTO(menuEntity);
    }

    public MenuItemTO updateMenu(MenuItemTO menuTO) {
    	return createMenu(menuTO);
    }

    public void deleteMenu(@PathVariable int menuId) {
        menuDao.deleteById(menuId);
    }
    
    public List<MenuItemTO> getMenuForRestaurant(int restaurantId) {
    	List<MenuItemEntity> menuEntities = menuDao.findByRestaurantId(restaurantId);
    	
    	List<MenuItemTO> menuItems = new ArrayList<>();
    	
    	menuEntities.forEach((menuEntity) -> {
    		MenuItemTO menuTO = convertMenuEntityToMenuTO(menuEntity);
    		menuItems.add(menuTO);
    	});
    	
    	return menuItems;
    }
    
    public List<MenuItemTO> getMenuForRestaurant(int restaurantId, Map<String, String> filter) {

    	List<MenuItemTO> menu = this.getMenuForRestaurant(restaurantId);

    	List<MenuItemTO> filteredMenu = menu;
		
		List<Predicate<MenuItemTO>> menuMatchers = new ArrayList<>();
		    		
		if(filter.containsKey("pure-veg")) {
			boolean isPureVeg = Boolean.valueOf(filter.get("pure-veg"));	    			
			menuMatchers.add((menuTO) -> {
				return menuTO.isPureVeg() == isPureVeg;
			});
		}
		
		if(filter.containsKey("cuisine")) {
			int cuisineId = Integer.valueOf(filter.get("cuisine"));
			menuMatchers.add((menuTO) -> {
				return menuTO.getCuisineId() == cuisineId;
			});
		}
		
		if(filter.containsKey("name")) {
			String nameStr = filter.get("name");
			menuMatchers.add((menuTO) -> {
				return menuTO.getName().contains(nameStr);
			});
		}
				
		Predicate<MenuItemTO> menuMatcher = menuMatchers.stream().reduce(x -> true, Predicate::and);
		
		filteredMenu = filteredMenu.stream()
									.filter(menuMatcher)
									.toList();

		return filteredMenu;
    }
    
    private MenuItemTO convertMenuEntityToMenuTO(MenuItemEntity menuEntity) {

    	MenuItemTO menuTO = new MenuItemTO();
		BeanUtils.copyProperties(menuEntity, menuTO);
		
		Optional<CuisineEntity> maybeCuisineTO = cuisineDao.findById(menuTO.getCuisineId());

		if(maybeCuisineTO.isEmpty()) {
			
		}

		Optional<MenuCategoryEntity> maybeMenuCategoryTO = menuCategoryDao.findById(menuTO.getCategoryId());
				
		if(maybeMenuCategoryTO.isEmpty()) {
			
		}
		
		MenuCategoryTO menuCategoryTO = new MenuCategoryTO();
		BeanUtils.copyProperties(maybeMenuCategoryTO.get(), menuCategoryTO);
		
		CuisineTO cuisineTO = new CuisineTO();
		BeanUtils.copyProperties(maybeCuisineTO.get(), cuisineTO);
		
		menuTO.setCategory(menuCategoryTO);
		menuTO.setCuisine(cuisineTO);
		
    	return menuTO;
    }
}

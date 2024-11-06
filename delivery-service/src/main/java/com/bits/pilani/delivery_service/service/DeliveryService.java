package com.bits.pilani.delivery_service.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bits.pilani.delivery_service.dao.DeliveryDao;
import com.bits.pilani.delivery_service.to.DeliveryTO;

@Service
public class DeliveryService {
	
	@Autowired
	DeliveryDao deliveryDao;
	
	
	public List<DeliveryTO> getRestaurantByOwnerId(int ownerId) {
		List<DeliveryTO> restaurants = new ArrayList<DeliveryTO>();
		
		var mayBeRestaurantEntities = deliveryDao.findByOrderId(ownerId);
		
		if(mayBeRestaurantEntities.isPresent()) {
			
			return mayBeRestaurantEntities.get().stream().map((restaurantEntity)-> {
				DeliveryTO deliveryTO = new DeliveryTO();
				BeanUtils.copyProperties(restaurantEntity, deliveryTO);
				return deliveryTO;
			}).toList();
		}		
        return restaurants;
    }
	
	public List<DeliveryTO> getAllDeliveryDetails() {	
		return deliveryDao.findAll().stream().map((deliveryEntity)-> {
			DeliveryTO restaurantTO = new DeliveryTO();
			BeanUtils.copyProperties(deliveryEntity, restaurantTO);
			return restaurantTO;
		}).toList();
    }

}

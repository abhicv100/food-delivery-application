package com.bits.pilani.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bits.pilani.service.DeliveryService;
import com.bits.pilani.to.DeliveryTO;

@RestController
@ResponseBody
@RequestMapping("/delivery")
public class DeliveryServiceController {
	
	@Autowired
	DeliveryService deliveryService;
	
    @GetMapping("/{orderId}")
    public ResponseEntity<List<DeliveryTO>> getDeliveryDetailsByOrderId(@PathVariable int orderId) {
        return ResponseEntity.ok(deliveryService.getRestaurantByOwnerId(orderId));
    }
    
    @GetMapping
    public ResponseEntity<List<DeliveryTO>> getAllDeliveryDetails() {
        return ResponseEntity.ok(deliveryService.getAllDeliveryDetails());
    }

}

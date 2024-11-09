package com.bits.pilani.delivery_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bits.pilani.delivery_service.service.DeliveryService;
import com.bits.pilani.delivery_service.to.DeliveryTO;

@RestController
@ResponseBody
@RequestMapping("/delivery")
public class DeliveryServiceController {
	
	@Autowired
	DeliveryService deliveryService;
	
    @GetMapping("/{orderId}")
    public ResponseEntity<DeliveryTO> getDeliveryDetailsByOrderId(@PathVariable int orderId) throws Exception {
        return ResponseEntity.ok(deliveryService.getDeliveryByOrderId(orderId));
    }
    
    @GetMapping
    public ResponseEntity<List<DeliveryTO>> getAllDeliveryDetails() {
        return ResponseEntity.ok(deliveryService.getAllDeliveryDetails());
    }
    
    @PostMapping
    public ResponseEntity<DeliveryTO> newDeliveryDetails(@RequestBody DeliveryTO deliveryTO) {
        return ResponseEntity.ok(deliveryService.newDeliveryDetails(deliveryTO));
    }
    
    @PutMapping("/{orderId}")
    public ResponseEntity<DeliveryTO> updateDeliveryDetails(@PathVariable int orderId,@RequestBody DeliveryTO deliveryTO) throws Exception {
        return ResponseEntity.ok(deliveryService.updateDeliveryByOrderId(deliveryTO, orderId));
    }
}

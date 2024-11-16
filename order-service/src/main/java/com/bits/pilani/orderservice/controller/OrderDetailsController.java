package com.bits.pilani.orderservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bits.pilani.orderservice.service.OrderDetailsService;
import com.bits.pilani.to.ResponseTO;

@RestController
@RequestMapping("/orderdetails/")
public class OrderDetailsController {

    @Autowired
    OrderDetailsService orderDetailsService;

    // @GetMapping("/popular/items")
    // public ResponseEntity<ResponseTO> getMostOrderedItems(@RequestParam(name = "cuisine", required = false) String cuisine,
    //                                                         @RequestParam(name = "restaurant", required = false) String restaurant)
    // {
        
    // }

    // @GetMapping("/popular/restaurant")
    // public ResponseEntity<ResponseTO> getMostPopularRestaurants(@RequestParam(name = "cuisine", required = false) String cuisine)
    // {
        
    // }
}

package com.bits.pilani.orderservice.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bits.pilani.orderservice.dto.OrderRequest;
import com.bits.pilani.orderservice.dto.OrderResponse;
import com.bits.pilani.orderservice.entity.Order;
import com.bits.pilani.orderservice.enums.OrderStatus;
import com.bits.pilani.orderservice.repository.OrderRepo;
import com.bits.pilani.orderservice.service.OrderService;
import com.bits.pilani.orderservice.utils.OrderConvertor;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    OrderRepo orderRepo;

    @PostMapping("/")
    public Order placeOrder(@RequestBody OrderRequest orderRequest) {

        Order order = OrderConvertor.toOrder(orderRequest);

        LocalDateTime currentTime = LocalDateTime.now();
        order.setStartTime(currentTime);
        
        order.setExpectedTime(orderService.getEstimatedTime(orderRequest.getKilometers(), currentTime));

        order.setFinalAmt(orderRequest.getTotalAmt() - orderRequest.getRestaurantDiscAmt());

        Order savedOrder = orderRepo.save(order);
        //TODO: store order details too!

        return savedOrder;
        //return orderService.placeOrder(orderRequest);
    }

    @GetMapping("/{orderId}")
    public Order getOrder(@PathVariable int orderId){
        return orderRepo.findByOrderId(orderId);
    }
}

package com.bits.pilani.orderservice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bits.pilani.orderservice.entity.Order;
import com.bits.pilani.orderservice.entity.OrderDetails;
import com.bits.pilani.orderservice.repository.OrderDetailsRepo;

@Service
public class OrderDetailsService {

    @Autowired
    OrderDetailsRepo orderDetailsRepo;

    public void saveOrderDetails(Order order){
        
        List<OrderDetails> orderDetailsList = order.getItems().stream().map(item -> {
            OrderDetails orderDetails = new OrderDetails();

            orderDetails.setOrder(order);
            orderDetails.setOrderMonth(order.getStartTime().getMonthValue());
            orderDetails.setOrderYear(order.getStartTime().getYear());
            orderDetails.setRestaurantId(order.getRestaurantId());
            orderDetails.setUserId(order.getUserId());

            // Set fields specific to the item
            orderDetails.setItemId(item.getId());
            orderDetails.setQuantity(item.getQuantity());

            return orderDetails;
        }).collect(Collectors.toList());

        // Save all OrderDetails in batch
        orderDetailsRepo.saveAll(orderDetailsList);
    }

    

}

package com.bits.pilani.orderservice.service;

import org.springframework.stereotype.Service;

import com.bits.pilani.orderservice.entity.Order;
import com.bits.pilani.orderservice.entity.OrderDetails;

@Service
public class OrderDetailsService {

    public void saveOrderDetails(Order order)
    {
        OrderDetails orderDetails = new OrderDetails();

        orderDetails.setOrder(order);
        orderDetails.setOrderMonth(order.getStartTime().getMonthValue());
        orderDetails.setOrderYear(order.getStartTime().getYear());
        orderDetails.setRestaurantId(order.getRestaurantId());
        orderDetails.setUserId(order.getUserId());

        

    }

}

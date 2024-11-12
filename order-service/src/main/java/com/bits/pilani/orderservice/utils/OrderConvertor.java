package com.bits.pilani.orderservice.utils;

import com.bits.pilani.orderservice.dto.OrderRequest;
import com.bits.pilani.orderservice.dto.OrderResponse;
import com.bits.pilani.orderservice.entity.Order;
import com.bits.pilani.orderservice.enums.OrderStatus;

public class OrderConvertor {

    public static Order toOrder(OrderRequest orderRequest)
    {
        Order order = new Order();
        order.setUserId(orderRequest.getUserId());
        order.setRestaurantId(orderRequest.getRestaurantId());
        order.setItems(orderRequest.getItems());
        order.setRestaurantDiscId(orderRequest.getRestaurantDiscId());
        order.setRestaurantDiscAmt(orderRequest.getRestaurantDiscAmt());
        order.setTotalAmt(orderRequest.getTotalAmt());
        order.setAddress(orderRequest.getAddress());
        order.setKilometers(orderRequest.getKilometers());

        order.setOrderStatus(OrderStatus.PLACED);

        return order;
    }

    public static OrderResponse toOrderResponse(Order order)
    {
        OrderResponse orderResponse = new OrderResponse();

        orderResponse.setOrderId(order.getOrderId());
        orderResponse.setOrderStatus(order.getOrderStatus());
        orderResponse.setStartTime(order.getStartTime());
        orderResponse.setExpectedTime(order.getExpectedTime());
        orderResponse.setModifiedTime(order.getModifiedTime());
        orderResponse.setEndTime(order.getEndTime());

        return orderResponse;
    }
}

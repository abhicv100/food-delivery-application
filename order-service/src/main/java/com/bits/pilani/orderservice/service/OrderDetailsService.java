package com.bits.pilani.orderservice.service;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bits.pilani.orderservice.dto.PopularItemResponse;
import com.bits.pilani.orderservice.dto.PopularRestuarantResponse;
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
            orderDetails.setCategoryId(item.getCategoryId());
            orderDetails.setCuisineId(item.getCuisineId());

            return orderDetails;
        }).collect(Collectors.toList());

        // Save all OrderDetails in batch
        orderDetailsRepo.saveAll(orderDetailsList);
    }

    public List<PopularItemResponse> toPopularItemResponse(List<Object[]> rawResults)
    {
        return rawResults.stream()
                .map(result -> new PopularItemResponse(
                        (Integer) result[0], // item
                        (Long) result[1] // count
                ))
                .collect(Collectors.toList());
    }

    public List<PopularRestuarantResponse> toPopularRestaurantResponse(List<Object[]> rawResults)
    {
        return rawResults.stream()
                .map(result -> new PopularRestuarantResponse(
                        (Integer) result[0], // item
                        (Long) result[1] // count
                ))
                .collect(Collectors.toList());
    }

}

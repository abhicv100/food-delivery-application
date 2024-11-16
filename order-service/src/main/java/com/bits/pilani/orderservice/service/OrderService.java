package com.bits.pilani.orderservice.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bits.pilani.exception.CustomException;
import com.bits.pilani.orderservice.dto.OrderRequest;
import com.bits.pilani.orderservice.entity.Order;
import com.bits.pilani.orderservice.enums.OrderStatus;
import com.bits.pilani.orderservice.repository.OrderRepo;
import com.bits.pilani.orderservice.utils.OrderConvertor;
import com.bits.pilani.to.SuccessResponseTO;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class OrderService {

    @Autowired
    OrderRepo orderRepo;

    @Autowired
    OrderDetailsService orderDetailsService;

    public LocalDateTime getEstimatedTime(int kms, LocalDateTime currentTime)
    {
        if(kms <= 2)
        {
            return currentTime.plusMinutes(15);
        }
        else if(kms <= 5)
        {
            return currentTime.plusMinutes(25);
        }
        else if(kms <= 10)
        {
            return currentTime.plusMinutes(40);
        }
        else if(kms <= 15)
        {
            return currentTime.plusMinutes(60);
        }
        else
        {
            return currentTime.plusMinutes(90);
        }
    }

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = RANDOM.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(randomIndex));
        }
        return sb.toString();
    }

    public boolean validateStatus(OrderStatus previousStatus, OrderStatus newStatus){
        
        if(newStatus.equals(OrderStatus.REJECTED))
        {
            return true;
        }

        if(newStatus.equals(OrderStatus.CANCELLED) && previousStatus.equals(OrderStatus.PLACED))
        {
            return true;
        }

        if(previousStatus.getNext().equals(newStatus))
        {
            return true;
        }

        return false;
        
    }

    public boolean ongoingOrderExists(OrderRequest orderRequest, int userId)
    {
        List<Order> orders = orderRepo.findByUserIdAndRestaurantId(userId, orderRequest.getRestaurantId());

        if(orders.isEmpty())
        {
            return false;
        }

        for (Order order : orders) {
            if(!order.getOrderStatus().equals(OrderStatus.DELIVERED)
            || !order.getOrderStatus().equals(OrderStatus.REJECTED)
            || !order.getOrderStatus().equals(OrderStatus.CANCELLED))
            {
                return true;
            }
        }         

        return false;
    }

    public String getDiscountCode(LocalDateTime endTime, LocalDateTime expectedTime)
    {
        if(endTime.isAfter(expectedTime))
        {
            return generateRandomString(6);
        }
        
        return null;
    }

    public Order placeOrder(OrderRequest orderRequest, int userId) throws Exception{
        if(!ongoingOrderExists(orderRequest, userId))
        {
            orderRequest.setOrderStatus(OrderStatus.PLACED);
            Order order = OrderConvertor.toOrder(orderRequest);
            order.setUserId(userId);

            LocalDateTime currentTime = LocalDateTime.now();
            order.setStartTime(currentTime);
            
            order.setExpectedTime(getEstimatedTime(orderRequest.getKilometers(), currentTime));

            order.setFinalAmt(orderRequest.getTotalAmt() - orderRequest.getRestaurantDiscAmt());

            Order savedOrder = orderRepo.save(order);
            orderDetailsService.saveOrderDetails(savedOrder);


            return savedOrder;
        }
        else{
            throw new CustomException(HttpStatus.CONFLICT, "There's an ongoing order from the same restaurant! Please place another order once this completes, or contact the Restaurant for more information.");
        }  
    }
}

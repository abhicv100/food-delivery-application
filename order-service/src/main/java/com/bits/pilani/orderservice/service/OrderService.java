package com.bits.pilani.orderservice.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bits.pilani.orderservice.dto.OrderRequest;
import com.bits.pilani.orderservice.entity.Order;
import com.bits.pilani.orderservice.enums.OrderStatus;
import com.bits.pilani.orderservice.repository.OrderRepo;

@Service
public class OrderService {

    @Autowired
    OrderRepo orderRepo;

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

    public boolean validate(OrderRequest orderRequest, int orderId)
    {
        Order order = orderRepo.findByOrderId(orderId);

        return order.getUserId() == orderRequest.getUserId();        
    }

    public boolean validateStatus(OrderStatus previousStatus, OrderStatus newStatus)
    {
        if(newStatus.equals(OrderStatus.ACCEPTED) && previousStatus.equals(OrderStatus.PLACED))
        {
            return true;
        }
        if(newStatus.equals(OrderStatus.PREPARING) && previousStatus.equals(OrderStatus.ACCEPTED))
        {
            return true;
        }           
            
        if(newStatus.equals(OrderStatus.READY_FOR_PICKUP) && previousStatus.equals(OrderStatus.PREPARING))
        {
            return true;
        }   

        if(newStatus.equals(OrderStatus.OUT_FOR_DELIVERY) && previousStatus.equals(OrderStatus.READY_FOR_PICKUP))
        {
            return true;
        }

        if(newStatus.equals(OrderStatus.DELIVERED) && previousStatus.equals(OrderStatus.OUT_FOR_DELIVERY))
        {
            return true;
        }
        
        if(newStatus.equals(OrderStatus.REJECTED))
        {
            return true;
        }

        if(newStatus.equals(OrderStatus.CANCELLED) && previousStatus.equals(OrderStatus.PLACED))
        {
            return true;
        }

        return false;
        
    }
}

package com.bits.pilani.orderservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.bits.pilani.orderservice.entity.Order;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    List<Order> findByUserId(Long userId);

    Order findByOrderIdAndUserId(int orderId, int userId);

    List<Order> findByUserIdAndRestaurantId(Integer userId, Integer restaurantId);
    
}
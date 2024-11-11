package com.bits.pilani.orderservice.dto;

import java.time.LocalDateTime;

import com.bits.pilani.orderservice.enums.OrderStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderResponse {

    private Long orderId;

    private OrderStatus orderStatus;

    private LocalDateTime expectedTime;

    private LocalDateTime startTime;

    private LocalDateTime modifiedTime;
    
    private LocalDateTime endTime;

}

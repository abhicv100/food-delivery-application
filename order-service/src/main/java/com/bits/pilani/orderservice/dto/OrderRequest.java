package com.bits.pilani.orderservice.dto;

import java.time.LocalDateTime;

import com.bits.pilani.orderservice.enums.OrderStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequest {

    private Long userId;

    private Long restaurantId;

    private String items;

    private Float totalAmt;

    private String restaurantDiscId;

    private Float restaurantDiscAmt;

    private Float finalAmt;

    private OrderStatus orderStatus;

    private LocalDateTime startTime;

    private LocalDateTime modifiedTime;

    private LocalDateTime expectedTime;

    private String address;
    
    private int kilometers;

}

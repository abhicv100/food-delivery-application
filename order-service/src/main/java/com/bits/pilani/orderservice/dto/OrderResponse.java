package com.bits.pilani.orderservice.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.bits.pilani.orderservice.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderResponse {

    private Integer orderId;
    private Integer userId;
    private Integer restaurantId;
    private List<MenuItem> items;
    private Float totalAmt;
    private String restaurantDiscId;
    private Float restaurantDiscAmt;
    private Float finalAmt;
    private OrderStatus orderStatus;

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startTime;

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime modifiedTime;

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endTime;

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime expectedTime;
    
    private String address;
    private Integer kilometers;
    private Integer deliveryPersonnelId;
}

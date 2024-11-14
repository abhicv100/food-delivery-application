package com.bits.pilani.orderservice.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequest {

    private Long userId;

    private Long restaurantId;

    private List<MenuItem> items;

    private Float totalAmt;

    private String restaurantDiscId;

    private Float restaurantDiscAmt;

    private String address;
    
    private int kilometers;

}

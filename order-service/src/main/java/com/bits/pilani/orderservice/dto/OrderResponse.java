package com.bits.pilani.orderservice.dto;

import java.time.LocalDateTime;

import com.bits.pilani.orderservice.entity.Order;
import com.bits.pilani.orderservice.enums.OrderStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderResponse extends Order{

}

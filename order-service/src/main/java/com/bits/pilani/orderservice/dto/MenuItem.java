package com.bits.pilani.orderservice.dto;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class MenuItem {
    private Integer id;
    private String name;
    private Integer quantity;
}

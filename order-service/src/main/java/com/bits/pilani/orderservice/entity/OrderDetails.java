package com.bits.pilani.orderservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="orderdetails", schema="public")
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="order_details_id")
    private Long orderDetailsId;

    @Column(nullable = false, name="user_id")
    private Long userId;

    @Column(nullable = false, name="restaurant_id")
    private Long restaurantId;

    @Column(nullable = false, name="item_id")
    private Long itemId;

    @Column(nullable = false)
    private int quantity;

    @Column(name="order_month")
    private Integer orderMonth;

    @Column(name="order_year")
    private Integer orderYear;

    // Many-to-One relationship with Order
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderId", nullable = false)
    private Order order;
}

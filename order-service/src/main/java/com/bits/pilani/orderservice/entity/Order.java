package com.bits.pilani.orderservice.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.bits.pilani.orderservice.enums.OrderStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="order", schema="public")
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="order_id")
    private Long orderId;

    @Column(nullable = false, name="user_id")
    private Long userId;

    @Column(nullable = false, name="restaurant_id")
    private Long restaurantId;

    @Column(nullable = false)
    private String items;

    @Column(nullable = false)
    private Float totalAmt;

    @Column(name="restaurant_disc_id")
    private String restaurantDiscId;

    @Column(name="restaurant_disc_amt")
    private Float restaurantDiscAmt;

    @Column(nullable = false, name="final_amt")
    private Float finalAmt;

    @Column(nullable = false, name="order_status")
    private OrderStatus orderStatus;

    @Column(nullable = false, name="start_time")
    @CreatedDate
    private LocalDateTime startTime;

    @Column(nullable = false, name="modified_time")
    @LastModifiedDate
    private LocalDateTime modifiedTime;

    @Column(name="end_time")
    private LocalDateTime endTime;

    @Column(nullable = false, name="expected_time")
    private LocalDateTime expectedTime;

    //TODO: store a discount coupon somewhere if end time doesn't match expected end time.
    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private int kilometers;

    // One-to-Many relationship with OrderDetails
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderDetails> orderDetails;

    public Float getFinalAmt() {
        return this.totalAmt - this.restaurantDiscAmt;
    }
}

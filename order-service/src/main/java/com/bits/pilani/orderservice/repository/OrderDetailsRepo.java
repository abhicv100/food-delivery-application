package com.bits.pilani.orderservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bits.pilani.orderservice.entity.OrderDetails;

@Repository
public interface OrderDetailsRepo extends JpaRepository<OrderDetails, Long>, JpaSpecificationExecutor<OrderDetails> {
    
    // Count orders per month for a given year
    @Query("SELECT COUNT(o) FROM OrderDetails o WHERE o.orderYear = :year AND o.orderMonth = :month")
    Long countOrdersByMonthAndYear(int month, int year);

    // Count orders per year
    @Query("SELECT COUNT(o) FROM OrderDetails o WHERE o.orderYear = :year")
    Long countOrdersByYear(int year);

    // Get most ordered items
    @Query("SELECT o.itemId, COUNT(o.itemId) AS count FROM OrderDetails o GROUP BY o.itemId ORDER BY count DESC")
    List<Object[]> findMostOrderedItems();
}

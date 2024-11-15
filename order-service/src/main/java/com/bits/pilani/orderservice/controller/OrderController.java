package com.bits.pilani.orderservice.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bits.pilani.config.GlobalWebConfig;
import com.bits.pilani.exception.CustomException;
import com.bits.pilani.orderservice.dto.OrderRequest;
import com.bits.pilani.orderservice.dto.OrderResponse;
import com.bits.pilani.orderservice.entity.Order;
import com.bits.pilani.orderservice.enums.OrderStatus;
import com.bits.pilani.orderservice.repository.OrderRepo;
import com.bits.pilani.orderservice.service.OrderDetailsService;
import com.bits.pilani.orderservice.service.OrderService;
import com.bits.pilani.orderservice.utils.OrderConvertor;
import com.bits.pilani.security.Authorize;
import com.bits.pilani.security.Role;
import com.bits.pilani.to.ResponseTO;
import com.bits.pilani.to.SuccessResponseTO;
import com.bits.pilani.util.TokenUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    OrderDetailsService orderDetailsService;

    @Autowired
    OrderRepo orderRepo;

    @Authorize( roles= {Role.CUSTOMER})
    @PostMapping
    public ResponseEntity<ResponseTO> placeOrder(@RequestBody OrderRequest orderRequest,
                                                    @RequestHeader("Authorization") String token) throws Exception 
    {
        if(!orderService.ongoingOrderExists(orderRequest))
        {
            orderRequest.setOrderStatus(OrderStatus.PLACED);
            Order order = OrderConvertor.toOrder(orderRequest);

            LocalDateTime currentTime = LocalDateTime.now();
            order.setStartTime(currentTime);
            
            order.setExpectedTime(orderService.getEstimatedTime(orderRequest.getKilometers(), currentTime));

            order.setFinalAmt(orderRequest.getTotalAmt() - orderRequest.getRestaurantDiscAmt());

            Order savedOrder = orderRepo.save(order);
            orderDetailsService.saveOrderDetails(savedOrder);


            return SuccessResponseTO.create(savedOrder, HttpStatus.CREATED);
        }
        else{
            throw new CustomException(HttpStatus.CONFLICT, "There's an ongoing order from the same restaurant! Please place another order once this completes, or contact the Restaurant for more information.");
        }    
    }

    @Authorize( roles= {Role.CUSTOMER, Role.ADMIN, Role.DELIVERY_PERSONNEL})
    @GetMapping("/{orderId}")
    public ResponseEntity<ResponseTO> getOrder(@PathVariable int orderId,
                                                @RequestHeader("Authorization") String token) throws CustomException{

        Order order = orderRepo.findByOrderId(orderId);
        if(order != null && TokenUtil.validateUser(token, order.getUserId())){

            OrderResponse orderResponse = OrderConvertor.toOrderResponse(order);
            return SuccessResponseTO.create(orderResponse);
        }

        throw new CustomException(HttpStatus.NOT_FOUND, "Order not found!");
        
    }

    @Authorize( roles= {Role.CUSTOMER, Role.RESTAURANT_OWNER, Role.ADMIN, Role.DELIVERY_PERSONNEL})
    @PatchMapping("/{orderId}")
    public ResponseEntity<ResponseTO> updateOrder(@PathVariable int orderId, 
                                @RequestBody OrderRequest orderRequest) throws Exception
    {
        
        Order order = orderRepo.findByOrderId(orderId);

        if(order == null)
        {
            throw new CustomException(HttpStatus.NOT_FOUND, "Order not found");
        }

        if(orderService.validateStatus(order.getOrderStatus(), orderRequest.getOrderStatus()))
        {
            if(orderRequest.getOrderStatus().equals(OrderStatus.OUT_FOR_DELIVERY)){
                if(orderRequest.getDeliveryPersonnelId() != null && orderRequest.getDeliveryPersonnelId() != 0)
                {
                    order.setDeliveryPersonnelId(orderRequest.getDeliveryPersonnelId());   
                }
                else{
                    throw new CustomException(HttpStatus.BAD_REQUEST, "Delivery personnel is not assigned!");
                }
            }

            if(orderRequest.getOrderStatus().equals(OrderStatus.DELIVERED))
            {
                order.setEndTime(LocalDateTime.now());
                order.setOrderStatus(orderRequest.getOrderStatus());
                
                Order savedOrder = orderRepo.save(order);
                
                
                
                return SuccessResponseTO.create(OrderConvertor.toCompletedOrderResponse(savedOrder, orderService.getDiscountCode(savedOrder.getEndTime(), savedOrder.getExpectedTime())));
            }

            order.setOrderStatus(orderRequest.getOrderStatus());
            return SuccessResponseTO.create(OrderConvertor.toOrderResponse(orderRepo.save(order)));
        }

        throw new CustomException(HttpStatus.BAD_REQUEST, "Previous status is " 
                                    + order.getOrderStatus() + " next status should be " 
                                    + order.getOrderStatus().getNext());
    }
}

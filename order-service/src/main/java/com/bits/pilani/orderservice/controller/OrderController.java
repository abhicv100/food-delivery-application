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
import org.springframework.web.bind.annotation.RestController;

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
        int userId = TokenUtil.getUserIdFromToken(token);

        return SuccessResponseTO.create(orderService.placeOrder(orderRequest, userId), HttpStatus.CREATED);
    }

    @Authorize( roles= {Role.CUSTOMER, Role.ADMIN, Role.DELIVERY_PERSONNEL})
    @GetMapping("/{orderId}")
    public ResponseEntity<ResponseTO> getOrder(@PathVariable int orderId,
                                                @RequestHeader("Authorization") String token) throws CustomException{

        int userId = TokenUtil.getUserIdFromToken(token);
        Order order = orderRepo.findByOrderIdAndUserId(orderId, userId);
        if(order != null){

            OrderResponse orderResponse = OrderConvertor.toOrderResponse(order);
            return SuccessResponseTO.create(orderResponse);
        }

        throw new CustomException(HttpStatus.NOT_FOUND, "Order not found!");
        
    }

    @Authorize( roles= {Role.CUSTOMER, Role.RESTAURANT_OWNER, Role.ADMIN, Role.DELIVERY_PERSONNEL})
    @PatchMapping("/{orderId}")
    public ResponseEntity<ResponseTO> updateOrder(@PathVariable int orderId, 
                                @RequestBody OrderRequest orderRequest,
                                @RequestHeader("Authorization") String token) throws Exception{

        int userId = TokenUtil.getUserIdFromToken(token);
        return SuccessResponseTO.create(orderService.patchOrder(orderId, userId, orderRequest));
    }
}

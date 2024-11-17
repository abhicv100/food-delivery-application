package com.bits.pilani.orderservice.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.bits.pilani.exception.CustomException;
import com.bits.pilani.orderservice.dto.MenuItem;
import com.bits.pilani.orderservice.dto.OrderRequest;
import com.bits.pilani.orderservice.dto.OrderResponse;
import com.bits.pilani.orderservice.entity.Order;
import com.bits.pilani.orderservice.enums.OrderStatus;
import com.bits.pilani.orderservice.repository.OrderRepo;
import com.bits.pilani.orderservice.service.OrderService;
import com.bits.pilani.security.JwtAuthHandlerInterceptor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@WebMvcTest(controllers = { OrderController.class })
public class OrderControllerTest {

    @MockBean
    private OrderService orderService;

    @Autowired
    private MockMvc mockMvc;

    private Order mockOrder;

    private OrderResponse mockOrderResponse;

    @MockBean
    private OrderRepo orderRepo;

    @MockBean
	JwtAuthHandlerInterceptor jwtInterceptor;

    @BeforeEach
    void setUp() throws Exception {
        
        mockOrder = new Order();
        mockOrder.setOrderId(1);
        mockOrder.setUserId(1);
        mockOrder.setRestaurantId(1);
        mockOrder.setTotalAmt(100.0f);
        mockOrder.setFinalAmt(100.0f);
        mockOrder.setAddress("mock address");

        List<MenuItem> menuItems = new ArrayList<>();
        MenuItem m1 = new MenuItem(1, "Item 1", 1, 1, 1);
        menuItems.add(m1);

        mockOrder.setItems(menuItems);
        mockOrder.setKilometers(2);

        LocalDateTime starTime = LocalDateTime.now();
        mockOrder.setStartTime(starTime);
        mockOrder.setExpectedTime(starTime.plusMinutes(15));
        mockOrder.setModifiedTime(starTime);
        mockOrder.setOrderStatus(OrderStatus.PLACED);

        mockOrderResponse = new OrderResponse();

        mockOrderResponse.setOrderId(1);
        mockOrderResponse.setRestaurantId(1);
        mockOrderResponse.setTotalAmt(100.0f);
        mockOrderResponse.setFinalAmt(100.0f);
        mockOrderResponse.setAddress("mock address");

        mockOrderResponse.setItems(menuItems);
        mockOrderResponse.setKilometers(2);
        mockOrderResponse.setStartTime(starTime);
        mockOrderResponse.setExpectedTime(starTime.plusMinutes(15));
        mockOrderResponse.setModifiedTime(starTime);
        mockOrderResponse.setOrderStatus(OrderStatus.PLACED);

        when(jwtInterceptor.preHandle(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(true);
    }

    @Test
    void testPlaceOrder() throws Exception {
        
    }
}

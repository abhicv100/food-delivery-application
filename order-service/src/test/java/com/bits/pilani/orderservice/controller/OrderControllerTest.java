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

    @Mock
    private OrderService orderService;

    @Autowired
    private MockMvc mockMvc;

    private Order mockOrder;

    private OrderResponse mockOrderResponse;

    @Mock
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
    void testGetOrderById() throws Exception {
        when(orderRepo.findByUserIdAndOrderId(1, 1)).thenReturn(mockOrder);

        mockMvc.perform(get("/order/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.status").value("PLACED"));

        verify(orderRepo, times(1)).findByUserIdAndOrderId(1,1);
    }

    @Test
    void testGetOrderById_NotFound() throws Exception {
        when(orderRepo.findByUserIdAndOrderId(1, 1)).thenThrow(new RuntimeException("Order not found"));

        mockMvc.perform(get("/order/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Order not found"));

        verify(orderRepo, times(1)).findByUserIdAndOrderId(1,1);
    }

    @Test
    void testUpdateOrderStatus() throws Exception {

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setOrderStatus(OrderStatus.ACCEPTED);
        when(orderService.patchOrder(1, 1, orderRequest )).thenReturn(mockOrderResponse);

        mockMvc.perform(put("/order/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"orderStatus\":\"ACCEPTED\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderStatus").value("ACCEPTED"));

        verify(orderService, times(1)).patchOrder(1, 1, orderRequest );
    }

    @Test
    void testUpdateOrderStatus_OrderNotFound() throws Exception {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setOrderStatus(OrderStatus.ACCEPTED);
        when(orderService.patchOrder(1, 1, orderRequest )).thenThrow(new CustomException(HttpStatus.NOT_FOUND, "Order not found"));

        mockMvc.perform(put("/orders/1/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"status\":\"ACCEPTED\"}"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Order not found"));

        verify(orderService, times(1)).patchOrder(1, 1, orderRequest );
    }
}

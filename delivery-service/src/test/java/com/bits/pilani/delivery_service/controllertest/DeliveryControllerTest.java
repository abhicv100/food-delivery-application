package com.bits.pilani.delivery_service.controllertest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.bits.pilani.delivery_service.controller.DeliveryServiceController;
import com.bits.pilani.delivery_service.service.DeliveryService;
import com.bits.pilani.delivery_service.to.DeliveryTO;
import com.bits.pilani.security.JwtAuthHandlerInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;

@EnableAspectJAutoProxy
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(DeliveryServiceController.class)
public class DeliveryControllerTest {

	@Autowired
	protected MockMvc mvc;

	@Autowired
	private ObjectMapper mapper;

	@MockBean
	private DeliveryService service;
	
	@MockBean
	JwtAuthHandlerInterceptor jwtInterceptor;

	@BeforeEach
	void before() throws Exception{
		when(jwtInterceptor.preHandle(Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(true);
	}
	
	@Test
	@Order(1)
	void testGetDeliveryDetailsByOrderId() throws Exception {
		int orderId = 1;
		DeliveryTO mockDeliveryTO = new DeliveryTO();
		mockDeliveryTO.setOrder_id(orderId);
		mockDeliveryTO.setDelivered(true);
		mockDeliveryTO.setDelivery_accepted(true);
		mockDeliveryTO.setDelivery_person_id(3);
		mockDeliveryTO.setDelivery_message("On the Way");
		doReturn(mockDeliveryTO).when(service).getDeliveryByOrderId(Mockito.anyInt());

		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/delivery/{orderId}", orderId)
				.contentType(MediaType.APPLICATION_JSON)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);

		Mockito.verify(service, times(1)).getDeliveryByOrderId(orderId);
	}

	@Test
	@Order(2)
	void testgetAllDeliveryDetails() throws Exception {
		int orderId = 1;
		DeliveryTO mockDeliveryTO = new DeliveryTO();
		mockDeliveryTO.setOrder_id(orderId);
		mockDeliveryTO.setDelivered(true);
		mockDeliveryTO.setDelivery_accepted(true);
		mockDeliveryTO.setDelivery_person_id(3);
		mockDeliveryTO.setDelivery_message("On the Way");
		doReturn(List.of(mockDeliveryTO)).when(service).getAllDeliveryDetails();

		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/delivery", orderId)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);

		Mockito.verify(service, times(1)).getAllDeliveryDetails();
	}

	@Test
	@Order(3)
	void testNewDeliveryDetails() throws Exception {
		int orderId = 1;
		DeliveryTO mockDeliveryTO = new DeliveryTO();
		mockDeliveryTO.setOrder_id(orderId);
		mockDeliveryTO.setDelivered(true);
		mockDeliveryTO.setDelivery_accepted(true);
		mockDeliveryTO.setDelivery_person_id(3);
		mockDeliveryTO.setDelivery_message("On the Way");

		int orderID = 1;
		DeliveryTO mockDeliveryTo = new DeliveryTO();
		mockDeliveryTO.setOrder_id(orderID);
		mockDeliveryTO.setDelivered(true);
		mockDeliveryTO.setDelivery_accepted(true);
		mockDeliveryTO.setDelivery_person_id(3);
		mockDeliveryTO.setDelivery_message("picked up");
		doReturn(mockDeliveryTO).when(service).newDeliveryDetails(Mockito.any());
		String content = mapper.writeValueAsString(mockDeliveryTO);

		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post("/delivery").contentType(MediaType.APPLICATION_JSON).content(content))
				.andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);

		Mockito.verify(service, times(1)).newDeliveryDetails(Mockito.any());
	}

	@Test
	@Order(4)
	void testUpdateDeliveryByOrderId() throws Exception {
		int orderId = 1;
		DeliveryTO mockDeliveryTO = new DeliveryTO();
		mockDeliveryTO.setOrder_id(orderId);
		mockDeliveryTO.setDelivered(true);
		mockDeliveryTO.setDelivery_accepted(true);
		mockDeliveryTO.setDelivery_person_id(3);
		mockDeliveryTO.setDelivery_message("On the Way");

		doReturn(mockDeliveryTO).when(service).updateDeliveryByOrderId(Mockito.any(), Mockito.anyInt());
		String content = mapper.writeValueAsString(mockDeliveryTO);

		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put("/delivery/{orderId}", orderId)
				.contentType(MediaType.APPLICATION_JSON).content(content)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);

		Mockito.verify(service, times(1)).updateDeliveryByOrderId(Mockito.any(), Mockito.anyInt());
	}

}

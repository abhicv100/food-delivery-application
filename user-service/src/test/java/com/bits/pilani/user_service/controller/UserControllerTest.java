package com.bits.pilani.user_service.controller;

import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.bits.pilani.security.JwtAuthHandlerInterceptor;
import com.bits.pilani.security.Role;
import com.bits.pilani.user_service.service.UserService;
import com.bits.pilani.user_service.to.UserTO;
import com.bits.pilani.user_service.to.VehicleTypeTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = { UserController.class })
public class UserControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	UserService userService;

	@MockBean
	JwtAuthHandlerInterceptor jwtInterceptor;

	@Autowired
	ObjectMapper mapper;

	@BeforeEach
	void before() throws Exception {
		when(jwtInterceptor.preHandle(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(true);
	}

	@Order(1)
	@Test
	void testGetUser() throws Exception {
		UserTO userTo = new UserTO();
		userTo.setId(1);
		userTo.setUsername("user");
		userTo.setFullName("fullname");
		userTo.setAddress("address");
		userTo.setRoleId(1);

		when(userService.getUser(Mockito.anyInt())).thenReturn(userTo);

		mockMvc.perform(MockMvcRequestBuilders.get("/user/1")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Order(2)
	@Test
	void testCreateUser() throws Exception {
		UserTO userTo = new UserTO();
		userTo.setId(1);
		userTo.setUsername("user");
		userTo.setFullName("fullname");
		userTo.setAddress("address");
		userTo.setRoleId(1);

		var requestBody = mapper.writeValueAsString(userTo);

		when(userService.createUser(Mockito.any(UserTO.class))).thenReturn(userTo);

		mockMvc.perform(
				MockMvcRequestBuilders.post("/user").contentType(MediaType.APPLICATION_JSON).content(requestBody))
				.andExpect(MockMvcResultMatchers.status().isCreated());
	}

	@Order(3)
	@Test
	void testUpdateUser() throws Exception {
		UserTO userTo = new UserTO();
		userTo.setId(1);
		userTo.setUsername("user");
		userTo.setFullName("fullname");
		userTo.setAddress("address");
		userTo.setRoleId(1);

		var requestBody = mapper.writeValueAsString(userTo);

		when(userService.updateUser(Mockito.any(UserTO.class))).thenReturn(userTo);

		mockMvc.perform(
				MockMvcRequestBuilders.put("/user/1").contentType(MediaType.APPLICATION_JSON).content(requestBody))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Order(4)
	@Test
	void testDeleteUser() throws Exception {		
		mockMvc.perform(
				MockMvcRequestBuilders.delete("/user/1"))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Order(5)
	@Test
	void testGetRoles() throws Exception {
		when(userService.getRoles()).thenReturn(new ArrayList<Role>());
		mockMvc.perform(MockMvcRequestBuilders.get("/user/roles")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Order(6)
	@Test
	void testGetVehicleTypes() throws Exception {
		when(userService.getVehicleTypes()).thenReturn(new ArrayList<VehicleTypeTO>());
		mockMvc.perform(MockMvcRequestBuilders.get("/user/vehicleTypes"))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}
}

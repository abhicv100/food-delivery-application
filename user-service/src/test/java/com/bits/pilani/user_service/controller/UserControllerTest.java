package com.bits.pilani.user_service.controller;

import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.bits.pilani.security.JwtAuthHandlerInterceptor;
import com.bits.pilani.user_service.service.UserService;
import com.bits.pilani.user_service.to.RoleTO;

@WebMvcTest(controllers = {UserController.class})	
public class UserControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	UserService userService;
	
	@MockBean
	JwtAuthHandlerInterceptor jwtInterceptor;
	
	@BeforeEach
	void before() throws Exception {
		when(jwtInterceptor.preHandle(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(true);
	}
	
	@Test
	void testGetRoles() throws Exception {		
		when(userService.getRoles()).thenReturn(new ArrayList<RoleTO>());		
		mockMvc.perform(MockMvcRequestBuilders.get("/user/roles"))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}
}

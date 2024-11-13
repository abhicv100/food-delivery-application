package com.bits.pilani.user_service.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.bits.pilani.exception.CustomException;
import com.bits.pilani.user_service.dao.UserDao;
import com.bits.pilani.user_service.entity.UserEntity;
import com.bits.pilani.user_service.to.UserTO;

@SpringBootTest
public class UserServiceTest {

	@MockBean
	UserDao userDao;
	
	@Autowired
	UserService userService;
	
	@Test
	void testGetUser_Success() throws CustomException {
		UserEntity userEntity = new UserEntity();
		userEntity.setId(1);
		userEntity.setPassword("1234");
		when(userDao.findById(Mockito.anyInt())).thenReturn(Optional.of(userEntity));
		var user = userService.getUser(1);
		assertEquals(user.getId(), 1);
		assertNull(user.getPassword());
	}
	
	@Test
	void testGetUser_CustomException() throws CustomException {
		when(userDao.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		CustomException exception = assertThrows(CustomException.class, () -> {
        	userService.getUser(1);
        });
		assertEquals("User id = '1' is invalid", exception.getMessage());
	}
	
	@Test
	void testCreateUser() throws CustomException {
		UserEntity userEntity = new UserEntity();
		userEntity.setId(1);
		userEntity.setPassword("1234");
		
		when(userDao.save(Mockito.any(UserEntity.class))).thenReturn(userEntity);
		
		UserTO mockUserTO = new UserTO();
		mockUserTO.setId(null);
		mockUserTO.setPassword("1234");
		
		var user = userService.createUser(mockUserTO);
		assertEquals(user.getId(), 1);
		assertNull(user.getPassword());
	}
	
	@Test
	void testUpdateUser() throws CustomException {
		UserEntity userEntity = new UserEntity();
		userEntity.setId(1);
		userEntity.setPassword("1234");
		
		when(userDao.save(Mockito.any(UserEntity.class))).thenReturn(userEntity);
		
		UserTO mockUserTO = new UserTO();
		mockUserTO.setId(1);
		mockUserTO.setPassword("1234");
		
		var user = userService.updateUser(mockUserTO);
		assertEquals(user.getId(), 1);
		assertNull(user.getPassword());
	}
}

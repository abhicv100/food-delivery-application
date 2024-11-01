package com.bits.pilani.user_service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bits.pilani.user_service.dao.RoleDao;
import com.bits.pilani.user_service.dao.UserDao;
import com.bits.pilani.user_service.dao.VehicleTypeDao;
import com.bits.pilani.user_service.entity.UserEntity;
import com.bits.pilani.user_service.to.UserTO;
import com.bits.pilani.user_service.to.VehicleTypeTO;
import com.bits.pilani.user_service.to.RoleTO;

@Service
public class UserService {
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	RoleDao roleDao;
	
	@Autowired
	VehicleTypeDao vehicleTypeDao;
	
	public UserTO getUser(int userId) {
		
		UserTO userTO = new UserTO();
		
		Optional<UserEntity> maybeUserEntity = userDao.findById(userId);
		
		if(maybeUserEntity.isPresent()) {
			BeanUtils.copyProperties(maybeUserEntity.get(), userTO);
		}
		
		userTO.setPassword(null);

		return userTO;
	}
	
	public UserTO createUser(UserTO userTO) {		
		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(userTO, userEntity);
		userEntity = userDao.save(userEntity);
		BeanUtils.copyProperties(userEntity, userTO);
		userTO.setPassword(null);
		return userTO;
	}
	
	public UserTO updateUser(UserTO userTO) {		
		return createUser(userTO);
	}
	
	public void deleteUser(int userId) {
		userDao.deleteById(userId);
	}
	
	public List<RoleTO> getRoles() {
		return roleDao.findAll().stream().map((roleEntity)-> {
			RoleTO roleTO = new RoleTO();
			BeanUtils.copyProperties(roleEntity, roleTO);
			return roleTO;
		}).toList();
	}
	
	public List<VehicleTypeTO> getVehicleTypes() {
		return vehicleTypeDao.findAll().stream().map((vehicleTypeEntity)-> {
			System.out.println(vehicleTypeEntity);
			VehicleTypeTO vehicleTypeTO = new VehicleTypeTO();
			BeanUtils.copyProperties(vehicleTypeEntity, vehicleTypeTO);
			return vehicleTypeTO;
		}).toList();
	}
}

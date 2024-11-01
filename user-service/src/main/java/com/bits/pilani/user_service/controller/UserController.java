package com.bits.pilani.user_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bits.pilani.user_service.service.UserService;
import com.bits.pilani.user_service.to.UserTO;
import com.bits.pilani.user_service.to.VehicleTypeTO;
import com.bits.pilani.user_service.to.RoleTO;

@RestController
@ResponseBody
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserTO> getUser(@PathVariable int userId) {
		// TODO: take user id from the auth token
		return ResponseEntity.ok(userService.getUser(userId));
	}
	
	@PostMapping
	public ResponseEntity<UserTO> createUser(@RequestBody UserTO userTO) {
		//TODO: validate user data
		userTO.setId(null);
		return ResponseEntity.ok(userService.createUser(userTO));
	}
	
	@PutMapping
	public ResponseEntity<UserTO> updateUser(@RequestBody UserTO userTO) {
		// TODO: take user id from the auth token 
		// verify the existance of the user id
		return ResponseEntity.ok(userService.updateUser(userTO));
	}
		
	@DeleteMapping("/{userId}")
	public ResponseEntity<UserTO> deleteUser(@PathVariable int userId) {
		// TODO: take user id from the auth token

		userService.deleteUser(userId);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/roles")
	public List<RoleTO> getRoles() {
		return userService.getRoles();
	}
	
	@GetMapping("/vehicleTypes")
	public List<VehicleTypeTO> getVehicleTypes() {
		return userService.getVehicleTypes();
	}
}

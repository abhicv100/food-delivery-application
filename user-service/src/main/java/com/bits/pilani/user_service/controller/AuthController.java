package com.bits.pilani.user_service.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bits.pilani.user_service.to.UsernamePasswordTO;

@RestController
@ResponseBody
public class AuthController {
	
	@GetMapping("/authenticate")
	public Map<String, String> authenticateUser(@RequestBody UsernamePasswordTO usernamePasswordTO) {
		return new HashMap<String, String>();
	}
}

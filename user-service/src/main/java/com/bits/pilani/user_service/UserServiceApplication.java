package com.bits.pilani.user_service;

import javax.crypto.SecretKey;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@SpringBootApplication(scanBasePackages = "com.bits.pilani")
public class UserServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

	String secret = "gZcLm+oqbX2jqZTiSt/LmdFsDZItipAMM3PYRMc4kJs=";

	@Bean
	public SecretKey getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secret);
		SecretKey key = Keys.hmacShaKeyFor(keyBytes);
		return key;
	}
}

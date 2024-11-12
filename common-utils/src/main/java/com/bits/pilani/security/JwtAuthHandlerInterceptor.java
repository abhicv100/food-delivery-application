package com.bits.pilani.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.http.HttpHeaders;

import com.bits.pilani.config.GlobalWebConfig;
import com.bits.pilani.to.ErrorResponseTO;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthHandlerInterceptor implements HandlerInterceptor {
	
	ObjectMapper mapper = new ObjectMapper();
	
	private void sendBearerTokenNotFoundError(HttpServletResponse response) throws StreamWriteException, DatabindException, IOException {
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		ErrorResponseTO errorResponse = new ErrorResponseTO();
		errorResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
		errorResponse.setError("BEARER_TOKEN_NOT_FOUND");
		errorResponse.setMessage("Bearer token is missing. Please provide JWT bearer token through Authorization header");				
		mapper.writeValue(response.getWriter(), errorResponse);		
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		if (handler instanceof HandlerMethod handlerMethod) {
			Authorize authroize = handlerMethod.getMethodAnnotation(Authorize.class);
			
			if (authroize == null) {
				return true;				
			}
			
			System.out.println(authroize.roles());

			String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);

			if (Objects.isNull(bearerToken)) {
				sendBearerTokenNotFoundError(response);
				return false;
			}

			if (bearerToken.startsWith("Bearer")) {
				String token = bearerToken.split(" ")[1];
				System.out.println(token);
				
				try {
					var claims = Jwts.parser().verifyWith(GlobalWebConfig.getSignInKey()).build().parseSignedClaims(token).getPayload();					
					if(!claims.containsKey("role")) {
						response.setStatus(HttpStatus.FORBIDDEN.value());
						response.setContentType(MediaType.APPLICATION_JSON_VALUE);
						ErrorResponseTO errorResponse = new ErrorResponseTO();
						errorResponse.setStatus(HttpStatus.FORBIDDEN.value());
						errorResponse.setError("ACCESS_DENIED");
						errorResponse.setMessage("User does not have permission to access this resource.");				
						mapper.writeValue(response.getWriter(), errorResponse);						
						return false;
					}
					
					String role = String.class.cast(claims.get("role"));
					
					var isRoleAuthorized = Arrays.asList(authroize.roles()).contains(role);
					
					if(!isRoleAuthorized) {
						response.setStatus(HttpStatus.FORBIDDEN.value());
						response.setContentType(MediaType.APPLICATION_JSON_VALUE);
						ErrorResponseTO errorResponse = new ErrorResponseTO();
						errorResponse.setStatus(HttpStatus.FORBIDDEN.value());
						errorResponse.setError("ACCESS_DENIED");
						errorResponse.setMessage("User does not have permission to access this resource.");				
						mapper.writeValue(response.getWriter(), errorResponse);
						return false;						
					}
					
				} catch (JwtException e) {
					response.setStatus(HttpStatus.UNAUTHORIZED.value());
					response.setContentType(MediaType.APPLICATION_JSON_VALUE);
					ErrorResponseTO errorResponse = new ErrorResponseTO();
					errorResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
					errorResponse.setError("INVALID_TOKEN");
					errorResponse.setMessage("Given bearer token in invalid. Please provide a valid token");				
					mapper.writeValue(response.getWriter(), errorResponse);
					return false;											
				}
			}
		}

		return true;
	}
}

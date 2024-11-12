package com.bits.pilani.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Authorize {
	
	String [] roles() default {"customer", "restaurant-owner", "deilvery-personal", "admin"};
}

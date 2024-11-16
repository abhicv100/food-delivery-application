package com.bits.pilani.util;

import org.springframework.http.HttpStatus;

import com.bits.pilani.config.GlobalWebConfig;
import com.bits.pilani.exception.CustomException;
import com.bits.pilani.security.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class TokenUtil {


    public static boolean validateUser(String token, int userId) throws CustomException
    {
        int userFromToken = 0;
        Role role = null;

        if (token.startsWith("Bearer")) {
            String tokenString = token.split(" ")[1];

            Claims claims = Jwts.parser().verifyWith(GlobalWebConfig.getSignInKey()).build().parseSignedClaims(tokenString).getPayload();

            if(claims.containsKey("userId"))
            {
                userFromToken = (int) claims.get("userId");
            }
            
            if(claims.containsKey("role"))
            {
                role = Role.valueOf((String) claims.get("role"));
            }
        }
        else{
            throw new CustomException(HttpStatus.UNAUTHORIZED, "Bearer token expected");
        }
        
        if(userFromToken == 0)
        {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "No user found in the token!");
        }

        if(userFromToken == userId)
        {
            return true;
        }
        else
        {
            if(role != null)
            {
                if(role.equals(Role.ADMIN))
                {
                    return true;
                }
            }
            else{
                throw new CustomException(HttpStatus.UNAUTHORIZED, "No role is assigned to this user");
            }
        }

        throw new CustomException(HttpStatus.UNAUTHORIZED, "Accessing other user's data is not permitted");
    }

}

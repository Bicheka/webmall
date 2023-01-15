package com.bicheka.security.filter;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.bicheka.security.SecurityConstants;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTAuthorizationFilter extends OncePerRequestFilter {
    

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)throws ServletException, IOException {
       
        //  Authorization: Bearer JWT
        String header = request.getHeader("Authorization"); //this is going to return -> Bearer JWT

        if(header == null || !header.startsWith(SecurityConstants.BEARER)){ // if the user have not being regiustered yet
            filterChain.doFilter(request, response); //go to the next step which is performing the action requested 
            return;
        }

        String token = header.replace(SecurityConstants.BEARER, ""); // this is going to eliminate the BEARER and return only the JWT
        String user = JWT.require(Algorithm.HMAC512(SecurityConstants.SECRET_KEY))
            .build()
            .verify(token)
            .getSubject(); //return username basically
        
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, Arrays.asList()); //this is the right constructor otherwise will get authenticated as false
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response); //because this is the last filter in the filter chain this is just going to end up performing the operation requested 
    }
}

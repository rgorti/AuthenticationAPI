package com.craft.authentication.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.craft.authentication.service.UserService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationFilter extends OncePerRequestFilter{
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SecurityUtil securityUtil;
	
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, 
									FilterChain filterChain) throws ServletException, IOException {
		
		String bearerToken = request.getHeader("Authorization");
	    String username = null;
	    String jwttoken = null;
	
	        if(bearerToken != null && bearerToken.startsWith("Bearer ")){

	        	jwttoken = bearerToken.substring(7);

	            try{
		            username = securityUtil.extractUserName(jwttoken);
	                UserDetails userDetails = userService.loadUserByUsername(username);
	                if(username!=null && SecurityContextHolder.getContext().getAuthentication() == null){

	                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	                    SecurityContextHolder.getContext().setAuthentication(authToken);

	                }else {
	                    System.out.println("Invalid Token!!");
	                }
	            }catch (Exception ex){
	                ex.printStackTrace();
	            }
	        }else {
	            System.out.println("Invalid Bearer Token Format!!");
	        }

	        filterChain.doFilter(request, response);
	}
}

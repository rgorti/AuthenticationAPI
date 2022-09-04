package com.craft.authentication.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.craft.authentication.model.UserModel;
import com.craft.authentication.model.UserRequest;
import com.craft.authentication.model.UserResponse;
import com.craft.authentication.security.SecurityUtil;
import com.craft.authentication.service.UserService;


@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private SecurityUtil securityUtil;
	
	@RequestMapping(method=RequestMethod.POST,value="/user/signup")
	public ResponseEntity<UserModel> createUser(@RequestBody UserModel userModel) {
		UserModel user = userService.createUser(userModel);
		ResponseEntity<UserModel> responeEntity = new ResponseEntity<>(user, HttpStatus.CREATED);
		return responeEntity;
	}
	
	
	@RequestMapping(method=RequestMethod.POST,value="/user/signin")
	public ResponseEntity<UserResponse> signInUser(@RequestBody UserRequest userRequest) {
		String userToken = this.generateToken(userRequest);
        	UserResponse userResponse = new UserResponse(userToken);
        	userResponse.setMessage("Congratulations User "+ userRequest.getUsername()+ " Successfully SignedIn. Use the token to access other resources");
        	return new ResponseEntity<UserResponse>(userResponse, HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/user/token")
	public ResponseEntity<UserResponse> getToken(@RequestBody UserRequest userRequest) {
		String userToken = this.generateToken(userRequest);
        	UserResponse userResponse = new UserResponse(userToken);
        	userResponse.setMessage("JWT Token is Generated for the user "+userRequest.getUsername());
        	return new ResponseEntity<UserResponse>(userResponse, HttpStatus.OK);
	}
	
	// Returns signedIn user
	@GetMapping("/user")
   	public UserModel getCurrentUser(Principal principal) {
        	UserDetails userDetails =  this.userService.loadUserByUsername(principal.getName());
        	return (UserModel) userDetails;
    	} 
	@RequestMapping(method=RequestMethod.GET,value="/user/{username}")
	public ResponseEntity<UserModel> getUserByName(@PathVariable String username){
		UserModel userDetails = (UserModel) this.userService.loadUserByUsername(username);
		ResponseEntity<UserModel> responeEntity = new ResponseEntity<>(userDetails, HttpStatus.OK);
		return responeEntity;
	
	public String generateToken(@RequestBody UserRequest userRequest) {
		
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword());
		authenticationManager.authenticate(authToken);
		UserDetails userDetails = userService.loadUserByUsername(userRequest.getUsername());
        	String userToken = securityUtil.generateToken(userDetails);
        	return userToken;
	
	}
}

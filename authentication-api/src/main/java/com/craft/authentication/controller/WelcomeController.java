package com.craft.authentication.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {	
	
	@RequestMapping(method=RequestMethod.GET,value="/welcome")
	public String welcome() {
	
		return "Welcome to the World of Technology. You are approved by the Authentication Service";
	}
	
		
}

package com.craft.authentication.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class WelcomeController {	
	
	@RequestMapping(method=RequestMethod.GET,value="/welcome")
	public ModelAndView welcome() {
       
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("welcome.html");
		return modelAndView;
	
	}
	
		
}

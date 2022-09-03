package com.craft.authentication.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.craft.authentication.security.AuthenticationFilter;
import com.craft.authentication.service.UserService;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationFilter authFilter;
	
	@Autowired
	private WebAuthEntryPoint authEntryPoint;
	
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	
		 
	     auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
   }
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .cors()
                .disable()
                .authorizeRequests()
                .antMatchers("/user/signin", "/user/signup","/user/token").permitAll()//only allow this endpoint without authentication
                .anyRequest().authenticated()//for any other request, authentication should performed
                .and()
                .exceptionHandling().authenticationEntryPoint(authEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);//every request should be independent of other and server does not have to manage session

        http.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
    }
	
	@Bean
	public PasswordEncoder passwordEncoder(){
		
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
	    return encoder;
	}

		
	@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

}

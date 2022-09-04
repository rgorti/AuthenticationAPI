package com.craft.authentication.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.craft.authentication.data.UserEntity;
import com.craft.authentication.model.UserModel;
import com.craft.authentication.repository.UserRepository;
import com.craft.authentication.security.Encoder;

@Service
public class UserService implements UserDetailsService{
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
    private Encoder encoder;
	
	public UserModel createUser(UserModel userModel) {
		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(userModel, userEntity);
		userEntity.setPassword(encoder.newPasswordEncoder().encode(userModel.getPassword()));
		UserEntity entity = userRepository.save(userEntity);
		BeanUtils.copyProperties(userEntity, userModel);
		return userModel;
	}
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 UserEntity userEntity = userRepository.findByUsername(username);
	     if(userEntity == null) {
	        throw new UsernameNotFoundException("User does not exist!");
	     }
	     UserModel userModel = new UserModel();
	     BeanUtils.copyProperties(userEntity, userModel);      
	     return userModel;
	}


}

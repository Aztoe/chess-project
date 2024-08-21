package com.example.chess.service.impl;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.chess.controller.form.UserForm;
import com.example.chess.domain.User;
import com.example.chess.repository.UserRepository;
import com.example.chess.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
@RequiredArgsConstructor
public class userServiceImpl implements UserService {

	
	private final UserRepository users;
	private final PasswordEncoder passwordEncoder;
	
	
	public void register(UserForm userForm) {
		User user = new User();
        user.setId(userForm.getId());
        user.setUsername(userForm.getUsername());
        user.setEmail(userForm.getEmail());
        
        user.setPassword(passwordEncoder.encode(userForm.getPassword()));
        
        users.save(user);

	}




	@Override
	public User findByUsername(String username) {
		
		return users.findByUsername(username);
		
	}




	@Override
	public UserForm createForm(User user) {
	UserForm form = new UserForm();
		
		if(user==null) {
			return form;
		}
		
		form.setId(user.getId());
		form.setEmail(user.getEmail());
		form.setUsername(user.getUsername());
		return form;
	}
}

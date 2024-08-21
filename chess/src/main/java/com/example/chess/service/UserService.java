package com.example.chess.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.chess.controller.form.UserForm;
import com.example.chess.domain.User;
import com.example.chess.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

public interface UserService {
	
	UserForm createForm(User user);
	
	void register(UserForm form); 
	
	User findByUsername(String username);
	
	
	
}

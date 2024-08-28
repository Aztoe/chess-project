package com.example.chess.service;

import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.chess.config.PasswordEncodeConfig;
import com.example.chess.controller.form.UserForm;
import com.example.chess.domain.User;
import com.example.chess.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class DbUserDetailService implements UserDetailsService {
	
	
	private final UserRepository users;
	
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
	
	/*
	 * public void save(UserForm userForm) { User user = new User();
	 * user.setId(userForm.getId()); user.setUsername(userForm.getUsername());
	 * user.setEmail(userForm.getEmail()); log.info(
	 * passwordEncoder.encode(user.getPassword()));
	 * user.setPassword(passwordEncoder.encode(user.getPassword()));
	 * users.save(user); }
	 */

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetails user = users.findByUsername(username);
        
		if (user == null) {
            throw new UsernameNotFoundException("No user found with username "+username);
        }
		return user;
	}
	 
	
	public void autoLogin(String username ,String password) {
		UserDetails userDetails = loadUserByUsername(username);
		
		  UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, password);

		  
		  if (usernamePasswordAuthenticationToken.isAuthenticated()) {
	            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
	            log.debug(String.format("자동 로그인 %s !!", username));
	        }
	}
	 
}

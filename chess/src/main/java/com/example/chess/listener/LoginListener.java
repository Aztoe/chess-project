package com.example.chess.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.chess.domain.User;
import com.example.chess.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;
@Component
@Slf4j
public class LoginListener implements ApplicationListener<InteractiveAuthenticationSuccessEvent>{

	@Autowired
	private UserRepository users;
	
	@Override
	public void onApplicationEvent(InteractiveAuthenticationSuccessEvent event) {
		
		UserDetails user = (UserDetails) event.getAuthentication().getPrincipal();
        User u = users.findByUsername(user.getUsername());
        if (u != null) {
            u.setIsLogIn(true);
            users.save(u);
            log.info("login true");
        }
	}
	
	

}

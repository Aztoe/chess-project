package com.example.chess.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.LogoutSuccessEvent;
import org.springframework.stereotype.Component;

import com.example.chess.domain.User;
import com.example.chess.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
public class LogoutListener implements ApplicationListener<LogoutSuccessEvent>{

	@Autowired
	private UserRepository users;
	
	@Override
	public void onApplicationEvent(LogoutSuccessEvent event) {
		
		String login = event.getAuthentication().getName();
		
		User u  = users.findByUsername(login);
		
		if(u != null) {
			u.setIsLogIn(false);
			users.save(u);
			log.info("logout");
		}
	}
	
	

}

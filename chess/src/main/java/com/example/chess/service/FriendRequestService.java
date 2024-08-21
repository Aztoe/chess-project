package com.example.chess.service;

import org.springframework.stereotype.Service;

import com.example.chess.controller.form.FriendRequestForm;
import com.example.chess.domain.FriendRequest;

@Service
public class FriendRequestService {

	public FriendRequestForm createForm(FriendRequest req) {
		FriendRequestForm form  = new FriendRequestForm();
		if(req ==null) {
			return form;
	
		}
		  	form.setId(req.getId());
	        form.setUsername(req.getReceiver().getUsername());
	        form.setSender(req.getSender());
	        return form;
	}
}

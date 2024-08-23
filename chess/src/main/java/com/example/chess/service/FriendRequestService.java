package com.example.chess.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.chess.controller.form.FriendRequestForm;
import com.example.chess.domain.FriendRequest;
import com.example.chess.domain.User;
import com.example.chess.repository.FriendRequestRepository;
import com.example.chess.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FriendRequestService {
	
	
	private final UserRepository users;
	
	private final FriendRequestRepository friendRequests;
	

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
	
	public List<User> getFriendUserList(User receiver) {
		List<FriendRequest> friends = friendRequests.findAllByReceiverAndIsAccepted(receiver, true);
		List<User> userFriends = new ArrayList<User>();
		
		for(FriendRequest friend : friends) {
			userFriends.add(users.findByUsername(friend.getSender()));
		}
		return userFriends;
	}
}

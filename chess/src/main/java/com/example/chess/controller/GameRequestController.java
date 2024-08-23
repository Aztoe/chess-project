package com.example.chess.controller;

import java.util.Optional;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.chess.domain.GameRequest;
import com.example.chess.domain.User;
import com.example.chess.repository.GameRequestRepository;
import com.example.chess.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/game/request")
public class GameRequestController {
		
	private final UserRepository users;
	
	private final GameRequestRepository gameRequests;
	
	@GetMapping("/send")
	public String sendFriendRequest(
				
				@AuthenticationPrincipal User currentUser,
				@RequestParam("friendId") Long friendId
			) {
		log.info("send contorller 실행");
		Optional<User> friend  =users.findById(friendId);
		
		if(friend.isPresent()) {
			GameRequest req = new GameRequest();
			
			req.setIsAccepted(false);
			req.setSender(currentUser);
			req.setReceiver(friend.get());
			log.info( "friend.get() ={}" ,friend.get().getUsername().toString());
			gameRequests.save(req);
			
			
		}
		return "redirect:/";
	}
	
		@GetMapping("/accept")
	   public String acceptGameRequest(
	            @AuthenticationPrincipal User currentUser,
	            @RequestParam("friendId") Long friendId
	    ) {
	        Optional<User> sender = users.findById(friendId);
	        if (sender.isPresent()) {
	            Optional<GameRequest> req = gameRequests.findBySenderAndReceiverAndIsAccepted(sender.get(), currentUser, false);
	            if (req.isPresent()) {
	                req.get().setIsAccepted(true);
	                gameRequests.save(req.get());
	            }
	        }
	        return "redirect:/game/init/" + friendId + "/" + currentUser.getId();
	    }
		
		
		  	@GetMapping("/decline")
		    @Transactional
		    public String decline(
		            @RequestParam("senderId") Long senderId
		    ) {
		        Optional<User> sender = users.findById(senderId);
		        sender.ifPresent(user -> gameRequests.deleteBySenderAndIsAccepted(user, false));
		        return "redirect:/";
		    }

	
}

package com.example.chess.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.chess.controller.form.FriendRequestForm;
import com.example.chess.domain.FriendRequest;
import com.example.chess.domain.User;
import com.example.chess.repository.FriendRequestRepository;
import com.example.chess.repository.UserRepository;
import com.example.chess.service.FriendRequestService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/friends")
@RequiredArgsConstructor
public class FriendRequestController {


	private final FriendRequestService service;
	
	private final UserRepository users;
	
	private final FriendRequestRepository friendsRequest;
	
	@GetMapping("/send")
	public String sendFriendRequest(@AuthenticationPrincipal User currentUser, Model model) {
		
		List<User> userList = users.findAll();
		
		List<FriendRequest> friends = friendsRequest.findAllByReceiverAndIsAccepted(currentUser, true);
		
		List<String> senders =friends.stream().map(FriendRequest::getSender).collect(Collectors.toList());
			
				userList.removeIf(user ->{
					return user.getId().equals(currentUser.getId()) ||
							senders.contains(user.getUsername());
				});
				
				  FriendRequestForm form = new FriendRequestForm();
			        form.setSender(currentUser.getUsername());

			        model.addAttribute("users", userList);
			        model.addAttribute("request", form);

			        return "user/send-friend-request";
		
	}
	
	@PostMapping("/send")
	public String sendFriendRequestToUser(@Valid @ModelAttribute("request")
										  FriendRequestForm form,
										  BindingResult result
			) {
		if(result.hasErrors()) {
			return "redirect:";
		}
		User receiver = users.findByUsername(form.getUsername());

        if (receiver != null) {
            FriendRequest req = new FriendRequest();
            req.setId(form.getId());
            req.setSender(form.getSender());
            req.setReceiver(receiver);
            req.setIsAccepted(false);

            friendsRequest.save(req);
        }
		
		return "redirect:/";
	
	}
	
	 @GetMapping("/accept")
	    public String acceptFriendRequest(
	            @RequestParam("userId") Long userId,
	            @RequestParam("username") String username
	    ) {
	        Optional<User> currentUser = users.findById(userId);
	        if (currentUser.isPresent()) {
	            System.out.println(username);
	            Optional<FriendRequest> f = friendsRequest.findByReceiverAndSenderAndIsAccepted(currentUser.get(), username, false);
	            if (f.isPresent()) {
	                User friend = users.findByUsername(username);
	                f.get().setIsAccepted(true);
	                // create the back request
	                FriendRequest back = new FriendRequest();
	                back.setIsAccepted(true);
	                back.setSender(currentUser.get().getUsername());
	                back.setReceiver(friend);

	                friendsRequest.save(back);
	                friendsRequest.save(f.get());
	            }
	        }

	        return "redirect:/";
	    }
	 
	 	@GetMapping("/decline")
	    @Transactional
	    public String declineFriendRequest(
	            @RequestParam final Long userId,
	            @RequestParam final String username
	    ) {
	        Optional<User> currentUser = users.findById(userId);
	        currentUser.ifPresent(user -> friendsRequest.deleteByReceiverAndSenderAndIsAccepted(user, username, false));

	        return "redirect:/";
	    }
	
	 	@GetMapping("/delete")
	 	@Transactional
	 	public String deleteFriend(
	 		@RequestParam("userId") final Long userId,
	 		@RequestParam("username") final String username
	 	){
	 		Optional<User> currentUser =users.findById(userId);
	 		
	 		if(currentUser.isPresent()) {
	 			friendsRequest.deleteByReceiverAndSenderAndIsAccepted(currentUser.get(), username, true);
	 			
	 			User friend = users.findByUsername(username);
	            friendsRequest.deleteByReceiverAndSenderAndIsAccepted(friend, currentUser.get().getUsername(), true);
	 		}
	 		
	 		return "redirect:/";
	 		}
	 	
}
	

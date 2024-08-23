package com.example.chess.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.chess.domain.Game;
import com.example.chess.domain.GameList;
import com.example.chess.domain.GameRequest;
import com.example.chess.domain.User;
import com.example.chess.repository.FriendRequestRepository;
import com.example.chess.repository.GameListRepository;
import com.example.chess.repository.GameRepository;
import com.example.chess.repository.GameRequestRepository;
import com.example.chess.repository.UserRepository;
import com.example.chess.service.FriendRequestService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

		private final GameRepository games;
	
		private final FriendRequestService friendService;
	
	    private final FriendRequestRepository friendRequests;
	    
	    private final UserRepository users;
	    
	    private final GameRequestRepository gameRequests;
	    
	    private final GameListRepository lastGames; 
	    
	    @GetMapping("/")
	    public String welcome(@AuthenticationPrincipal User user, final Model model) {
	        List<User> friends =friendService.getFriendUserList(user);
	        List<Game> currentGames =games.findByWhitePlayerOrBlackPlayerAndIsFinish(user, user, false);
	        currentGames.removeIf(Game::getIsFinish);
	        
	        List<Game> lastGames = games.findByWhitePlayerOrBlackPlayerAndIsFinish(user, user, true);
	        
	        Game game = new Game();
	         
	      
	        User u = users.findByUsername(user.getUsername());
	        u.setIsPlaying(false);
	        u.setIsLogIn(true);
	        users.save(u);
	        games.saveAll(currentGames);
	        
	        model.addAttribute("game", game);
	    	model.addAttribute("user", user);
	        model.addAttribute("friend_requests", friendRequests.findAllByReceiverAndIsAccepted(user, false));
	        model.addAttribute("friends", friends);
	        model.addAttribute("game_requests", gameRequests.findAllByReceiverAndIsAccepted(user, false));
	        model.addAttribute("pending_game_requests", gameRequests.findAllBySenderAndIsAccepted(user, false));
	        	
	      
	        
	        model.addAttribute("last_games", lastGames);
	        model.addAttribute("games", currentGames);
	        
	        
	        return "index";
	    }
}

package com.example.chess.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.chess.domain.Figure;
import com.example.chess.domain.Game;
import com.example.chess.repository.FigureRepository;
import com.example.chess.repository.GameRepository;
import com.example.chess.service.GameService;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
@RequestMapping("/game")
public class GameController {
    @Autowired
    private GameService gameService;

    @Autowired
    private GameRepository games;

    @Autowired
    private FigureRepository figures;

    private static final String GAME_REDIRECTION = "redirect:/game/play/";

    private static final String INDEX_REDIRECTION = "redirect:/";


    @GetMapping("/start")
    public String display(final Model model) {
        games.deleteAll();
        figures.deleteAll();

        // 
        Game g = new Game();

        games.save(g);

        // 체스판 생성
        gameService.generateGrid(g);

        figures.saveAll(g.getGrid());
        log.info("figures saved from game/");

        model.addAttribute("game", g);

        return GAME_REDIRECTION +g.getId();
    }
    
    @GetMapping("/play/{id}")
    public String play(final Model model,@PathVariable("id") Long id) {
    	Optional<Game> game = games.findById(id);
    	
    	if(game.isPresent()) {
    		model.addAttribute("game", game.get());
    		model.addAttribute("error_msg","");
    		return "game-play";
    	}
    	log.info("game {} not found  /play/{}",id,id);
    	return INDEX_REDIRECTION;
    	
   	
    
    }
    @GetMapping("/move/{gameId}/{pawnId}/{x}/{y}")
    public String moveOnVoid(final Model model,
    		@PathVariable("gameId") final Long gameId,
    		@PathVariable("pawnId") final Long pawnId,
    		@PathVariable("x") final Integer x,
    		@PathVariable("y") final Integer y) {
    	Optional<Game> game = games.findById(gameId);
    	if(game.isPresent()) {
    		Figure f = figures.getOne(pawnId);
    		
    		if(f.getOwner() ==game.get().getCurrentPlayer() ) {
    			if(gameService.checkAny(game.get(), f, x, y)) {
    				 log.info("moveOnvoid 실행");
                     f.setX(x);
                     f.setY(y);
                     f.updateMoveCount();
                     figures.save(f);
                     log.info("figure moved");

                     Game g = game.get();
                     g.changePlayer();
                     games.save(g);
    			}
    			
              
    		}else {
    			log.info("is not valid");
    		}
    		model.addAttribute("game" ,game.get());
        	return GAME_REDIRECTION+game.get().getId();
    		}
        log.info("game {} not found for route /move/{}/...", gameId, gameId);

        return INDEX_REDIRECTION;
    	}
    
    @GetMapping("/move/{gameId}/{pawnId1}/{pawnId2}")
    public String moveOnAnyPawn(final Model model,
                                 @PathVariable("gameId") final Long gameId,
                                 @PathVariable("pawnId1") final Long pawnId1,
                                 @PathVariable("pawnId2") final Long pawnId2
    ) {
        Optional<Game> game = games.findById(gameId);
        if (game.isPresent()) {
        	log.info("moveOnAnyPawn실행");
        	Figure f1 = figures.getOne(pawnId1);
        	Figure f2 = figures.getOne(pawnId2);
        	
        	 if (f1.getOwner() == game.get().getCurrentPlayer() && f1.getOwner() != f2.getOwner()) {
        	
        			if(gameService.checkAny(game.get(), f1, f2.getX(), f2.getY())) {
        				f1.setX(f2.getX());
                		f1.setY(f2.getY());
                		f1.updateMoveCount();
                		figures.save(f1);
                		log.info("pawn moved");

                		figures.delete(f2);
                		log.info("figure f2 delelted");
                		
                		 
                		
                		Game g = game.get();
                         g.changePlayer();
                         g.getGrid().remove(f2);
                         
                         games.save(g);
                		//
        			
        			}
        				
        		
        	
        		
        	} else {
        		log.info("you can't move");
        	}
        	 model.addAttribute("game", game.get());
            return GAME_REDIRECTION + game.get().getId();
        }
        log.info("game {} not found for route /play/{}",  gameId);
        return INDEX_REDIRECTION;
    }
    
    @GetMapping("/delete/{gameId}/{pawnId}") 
    public String delete(final Model model,
    	@PathVariable("gameId") Long gameId,
    	@PathVariable("pawnId") Long pawnId
    		) {
    	Optional<Game> game= games.findById(gameId);
    	
    	if(game.isPresent()) {
    		log.info("여기까진");
    		Figure f = figures.getOne(pawnId);
    		figures.save(f);
    		log.info("pawn killed");
    		Game g = game.get();
    		
    		
            return GAME_REDIRECTION + game.get().getId();
    	}
        return INDEX_REDIRECTION;

    }
    
    	
}
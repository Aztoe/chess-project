package com.example.chess.controller;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.chess.controller.form.PromoteForm;
import com.example.chess.domain.Figure;
import com.example.chess.domain.FigureName;
import com.example.chess.domain.Game;
import com.example.chess.domain.GameList;
import com.example.chess.domain.Move;
import com.example.chess.domain.PlayerName;
import com.example.chess.domain.User;
import com.example.chess.repository.FigureRepository;
import com.example.chess.repository.GameListRepository;
import com.example.chess.repository.GameRepository;
import com.example.chess.repository.MoveRepository;
import com.example.chess.repository.UserRepository;
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
    
    @Autowired
    private MoveRepository moves;
    
    @Autowired
    private GameListRepository gamesList;
    
    @Autowired
    private UserRepository users;

    private static final String GAME_REDIRECTION = "redirect:/game/play/";

    private static final String INDEX_REDIRECTION = "redirect:/";


    @GetMapping("/init/{whiteUserId}/{blackUserId}")
    public String init(final Model model,
    		  @PathVariable("whiteUserId") Long whiteUserId,
              @PathVariable("blackUserId") Long blackUserId
    		) {
    	   Optional<User> white = users.findById(whiteUserId);
           Optional<User> black = users.findById(blackUserId);
    	
           if (white.isPresent() && black.isPresent()) {
               if (white.get().getIsLogIn() && black.get().getIsLogIn()) {
                   black.get().setIsPlaying(true);	
                   
                   
                   users.save(black.get());
                   
                   games.deleteAll();
                   figures.deleteAll();
                   moves.deleteAll();
                   // create a game
                   Game g = new Game();
                   
                   Random rand = new Random();
                   int randomValue = rand.nextInt() % 2;
                   g.setCurrentPlayer(randomValue);
                   //plater 추가
                   g.setBlackPlayer(black.get());
                   g.setWhitePlayer(white.get());
                   g.setIsCheck(0);
                   g.setIsPause(false);
                   
                   g.setGameTime();
                   g.setTimeCurrentPlayer(System.currentTimeMillis());

                   games.save(g);

                   gameService.generateGrid(g);

                   figures.saveAll(g.getGrid());
                   gameService.findKing(g);

                   games.save(g);
                   log.info("figures saved from game/");

                   return GAME_REDIRECTION + g.getId();
               }
           }

           return INDEX_REDIRECTION;
       }
    
    @GetMapping("/play/{id}")
    public String play(final Model model,
    				@PathVariable("id") Long id,
    				@AuthenticationPrincipal User currentUser
    		) {
    				

    	Optional<Game> game = games.findById(id);
    	
    	if(game.isPresent()) {
    		
    		 if (game.get().getWhitePlayer().getIsPlaying() && game.get().getBlackPlayer().getIsPlaying()) {
                 game.get().setIsPause(false);
             }

    		
    		
    		model.addAttribute("game", game.get());

    		model.addAttribute("user", currentUser);
    		model.addAttribute("error_msg","");
    		model.addAttribute("time", gameService.getTimeElapsed(game.get().getGameTime()));
    		model.addAttribute("time_move",gameService.getTimeElapsed(game.get().getTimeCurrentPlayer()));
    		
    		if (gameService.isCheck(game.get())) {
                game.get().setIsCheck(1);
            } else {
                game.get().setIsCheck(0);
            }
        	
            model.addAttribute("mate", gameService.checkMate(game.get()));
            
            if(gamesList.findByGameId(id) != null)
                model.addAttribute("gameList", gamesList.findByGameId(id));

            currentUser.setIsPlaying(true);
            users.save(currentUser);
    		
    		
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
    		@PathVariable("y") final Integer y,
    		@AuthenticationPrincipal User currentUser
    		) {
    	Optional<Game> game = games.findById(gameId);
    	if(game.isPresent()) {
    		Figure f = figures.getOne(pawnId);
    		
    		if(f.getOwner() ==game.get().getCurrentPlayer()
    				&& game.get().getCurrentUser().getUsername().equals(currentUser.getUsername())) {
    			if(gameService.checkAny(game.get(), f, x, y)) {
    				Move m = new Move();
    				m.setPositionStart(f.getMoveCode());
                     
    				 f.setX(x);
                     f.setY(y);
                     f.updateMoveCount();
                     figures.save(f);
                     	
                     m.setPositionEnd(f.getMoveCode());
                     m.setPlayer(game.get().getCurrentPlayer());
                     
                     m.setTime(gameService.getTimeElapsed(game.get().getTimeCurrentPlayer()));
                     m.setGame(game.get());
                     moves.save(m);
                     	
                     Game g = game.get();
                     g.changePlayer();
                     g.setTimeCurrentPlayer(System.currentTimeMillis());
                     g.getMoves().add(m);
                     games.save(g);
                     
                     if(gameService.enablePawnPromote(f)) {
                    	 return "redirect:/game/promote/" + game.get().getId() + "/" + f.getId();
                     }
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
                                 @PathVariable("pawnId2") final Long pawnId2,
                                 @AuthenticationPrincipal User currentUser
    ) {
        Optional<Game> game = games.findById(gameId);
        if (game.isPresent()) {
        	log.info("moveOnAnyPawn실행");
        	Figure f1 = figures.getOne(pawnId1);
        	Figure f2 = figures.getOne(pawnId2);
        	
        	 if (f1.getOwner() == game.get().getCurrentPlayer() && f1.getOwner() != f2.getOwner()
        			 &&game.get().getCurrentUser().getUsername().equals(currentUser.getUsername())
        			 ) {
        	
        			if(gameService.checkAny(game.get(), f1, f2.getX(), f2.getY())) {
        				Move m = new Move();
                        m.setPositionStart(f1.getMoveCode());

        				f1.setX(f2.getX());
                		f1.setY(f2.getY());
                		f1.updateMoveCount();
                		figures.save(f1);
                		log.info("pawn moved");

                		figures.delete(f2);
                		log.info("figure f2 delelted");
                		
                		 m.setPositionEnd(f1.getMoveCode());
                         m.setPlayer(game.get().getCurrentPlayer());
                         m.setTime(gameService.getTimeElapsed(game.get().getTimeCurrentPlayer()));
                         m.setGame(game.get()); 
                         moves.save(m);
                         
                         if (gameService.isCheck(game.get())) {
                             game.get().setIsCheck(1);
                         } else {
                             game.get().setIsCheck(0);
                         }
                         
                         
                		Game g = game.get();
                         g.changePlayer();
                         g.setTimeCurrentPlayer(System.currentTimeMillis());
                         g.getMoves().add(m);
                         g.getGrid().remove(f2);
                         
                         games.save(g);	
                		//	
                         if(gameService.enablePawnPromote(f1)) {
                        	 return "redirect:/game/promote/" + game.get().getId() + "/" + f1.getId();
                         }
        			
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
    
    @GetMapping("/promote/{gameId}/{promoteId}")
    public String promote(final Model model,
                          @PathVariable("gameId") final Long gameId,
                          @PathVariable("promoteId") final Long promoteId
    ) {
        Optional<Game> game = games.findById(gameId);
        if (game.isPresent()) {
            Optional<Figure> fig = figures.findById(promoteId);
            if (fig.isPresent()) {
                model.addAttribute("game", game.get());
                model.addAttribute("error_msg", "");
                model.addAttribute("figure", fig.get());
                return "game-promote";
            }
        }
        log.info("game {} not found for route /promote/{}/{}", gameId, gameId, promoteId);
        return INDEX_REDIRECTION;
    }

    @PostMapping("/promote")
    public String promoteForm(PromoteForm form, BindingResult result) {
        if (result.hasErrors()) {
            log.info("error promote form");
        }

        log.info("you decided to promote {} to a {}", form.getId(), form.getName());

        Optional<Figure> figure = figures.findById(form.getId());

        if (figure.isPresent()) {
            if (Game.FIGURES_PROMOTION.contains(form.getName())) {
                figure.get().setName(form.getName());
                figure.get().setCode(FigureName.stringToFigureName(form.getName()).ordinal());
                figures.save(figure.get());
            }

            return GAME_REDIRECTION + figure.get().getGame().getId();
        }

        return "game-promote";
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
    
    @GetMapping("/endGame/{gameId}/{winner}/{looser}")
    public String EndGame(
    	@PathVariable("gameId") final Long gameId,
    	@PathVariable("winner") final String winner,
    	@PathVariable("looser") final String looser	
    ) {
    	
    	 Optional<Game> game = games.findById(gameId);
    	 
    	 if (game.isPresent()) {
    		 
    		 
    		 
    		 
             game.get().setIsFinish(true);
             game.get().setIsPause(true);
             if (game.get().getBlackPlayer().getUsername().equals(winner)) {
                 game.get().setWinner(PlayerName.BLACK);
             } else if (game.get().getWhitePlayer().getUsername().equals(winner)) {
                 game.get().setWinner(PlayerName.WHITE);
             }
             games.save(game.get());
         }

         if (gamesList.findByGameId(gameId) == null) {
             GameList gameList = new GameList();
             gameList.setWinner(winner);
             gameList.setLooser(looser);
             gameList.setGameId(gameId);
             gamesList.save(gameList);
         }

         return GAME_REDIRECTION + gameId;
    }
    
    	
}
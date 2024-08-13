package com.example.chess.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.chess.domain.Figure;
import com.example.chess.domain.FigureName;
import com.example.chess.domain.Game;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class GameService {

	 private void addFigureToGrid(
	            List<Figure> grid,
	            Game game,
	            int x,
	            int y,
	            int code,
	            String name,
	            int owner
	    ) {
	        Figure fig = new Figure();

	        fig.setCode(code);
	        fig.setX(x);
	        fig.setY(y);
	        fig.setName(name);
	        fig.setOwner(owner);
	        fig.setGame(game);

	        grid.add(fig);
	    }

	
	    public void generateGrid(final Game game) {
	    	
	        List<Figure> grid = new ArrayList<>();

	        for (int i = 0; i < Game.NUMBER_OF_PLAYER_IN_GAME; i++) {
	            for (int j = 0; j < Game.WIDTH; j++) {
	                String figName = Game.FIGURES_PLACEMENT.get(j);

	                addFigureToGrid(grid, game, j, 5 * (1 - i) + 1, FigureName.PAWN.ordinal(),"pawn", i);
	                addFigureToGrid(grid, game, j, 7 * (1 - i), FigureName.valueOf(figName.toUpperCase()).ordinal(), figName, i);
	            }
	        }
	        
	        game.setGrid(grid);
	        log.info("grid successfully generated");
	    }
}

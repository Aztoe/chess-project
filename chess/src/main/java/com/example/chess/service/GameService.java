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
	        fig.setKilled(0);
	        fig.setName(name);
	        fig.setOwner(owner);
	        fig.setGame(game);

	        grid.add(fig);
	    }
	 
	 	/**
	 	 * 
	 	 * @param v
	 	 * @return positive 면 1 negative 면 0 반환
	 	 */
	 	public int isPositive(int v) {
	 		return Integer.compare(v, 0);
	 	}
	 	
	 	public boolean isSegmentFree(Game game, int x1, int y1, int x2, int y2) {
	 		int dx= isPositive(x2-x1);
	 		int dy= isPositive(y2-y1);
	 		
	 		while(x1 != x2 || y1 != y2) {
	 			x1 +=dx;
	 			y1 +=dy;
	 			if(!game.isCellFree(x1, y1)) {
	 				return false;
	 			}
	 		}
	 		
	 		return true;
	 	}
	 	
	 	public boolean checkBishop(Game game, int x, int y, int nx, int ny) {
	 		if(Math.abs(x-nx) ==Math.abs(y-ny)) {
	 			return isSegmentFree(game, x, y, nx,ny);
	 		}
	 		
	 		return false;
	 	}
	 	
	    public boolean checkRook(Game game, int x, int y, int nx, int ny) {
	        if (x == nx || y == ny) {
	            return isSegmentFree(game, x, y, nx, ny);
	        }

	        return false;
	    }
	    
	    public boolean checkQueen(Game game, int x, int y, int nx, int ny) {
	        return checkBishop(game, x, y, nx, ny) || checkRook(game, x, y, nx, ny);
	    }

	    public boolean checkKing(int x, int y, int nx, int ny) {
	        return Math.abs(x - nx) <= 1 && Math.abs(y - ny) <= 1;
	    }

	    public boolean checkKnight(int x, int y, int nx, int ny) {
	        return (Math.abs(x - nx) == 1 && Math.abs(y - ny) == 2) ||
	                (Math.abs(x - nx) == 2 && Math.abs(y - ny) == 1);
	    }

	    public boolean checkAny(Game game, Figure f1, int dx, int dy) {
	        FigureName name = FigureName.stringToFigureName(f1.getName());
	        int x = f1.getX();
	        int y = f1.getY();
	        boolean check = false;
	        
	        switch (name) {
            case KING:
                check = checkKing(x, y, dx, dy);
                break;
            case QUEEN:
                check = checkQueen(game, x, y, dx, dy);
                break;
            case BISHOP:
                check = checkBishop(game, x, y, dx, dy);
                break;
            case ROOK:
                check = checkRook(game, x, y, dx, dy);
                break;
            case KNIGHT:
                check = checkKnight(x, y, dx, dy);
                break;
        }
	        return check;
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

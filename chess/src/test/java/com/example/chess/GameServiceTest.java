package com.example.chess;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.chess.domain.Figure;
import com.example.chess.domain.Game;
import com.example.chess.service.GameService;

class GameServiceTest {

	private GameService service;
	
	private Game game;
	
	@BeforeEach
	public void before() {
        service = new GameService();
        game = new Game();
        service.generateGrid(game);
    }
	
	 @Test
	    public void checkBishopTest() {

	        assertThat(service.checkBishop(game, 2, 7, 0, 5)).isFalse();
	        game.getFigureAt(1, 6).setY(5);
	        assertThat(service.checkBishop(game, 2, 7, 0, 5)).isTrue();

	        assertThat(service.checkBishop(game, 2, 7, 4, 5)).isFalse();
	        game.getFigureAt(3, 6).setY(5);
	        assertThat(service.checkBishop(game, 2, 7, 4, 5)).isTrue();

	  
	        game.getFigureAt(2, 6).setY(3);
	        assertThat(service.checkBishop(game, 2, 7, 2, 5)).isFalse();

	        assertThat(service.checkBishop(game, 2, 7, -1, 8)).isFalse();
	    }
	
	@Test
	public void isPositiveTest() {
		  assertThat(service.isPositive(3)).isEqualTo(1);
	      assertThat(service.isPositive(-23)).isEqualTo(-1);
	      assertThat(service.isPositive(0)).isEqualTo(0);
	}
	
    @Test
    public void isSegmentFreeTest() {
        assertThat(service.isSegmentFree(game, 0, 2, 7, 2)).isTrue();
        assertThat(service.isSegmentFree(game, 2, 2, 5, 5)).isTrue();
        assertThat(service.isSegmentFree(game, 0, 7, 0, 2)).isFalse();
        assertThat(service.isSegmentFree(game, 2, 7, 7, 2)).isFalse();
    }
    
    @Test
    public void smallCastlingTest() {
        Figure f = game.getFigureAt(4, 7);

        game.getFigureAt(5, 7).setY(5);
        game.getFigureAt(6, 7).setY(5);

        assertThat(service.checkKing(game, f, 6, 7)).isTrue();
    }
    
    @Test
    public void bigCastlingTest() {
      Figure f = game.getFigureAt(4, 7);

      game.getFigureAt(1, 7).setY(5);
      game.getFigureAt(2, 7).setY(5);
      game.getFigureAt(3, 7).setY(5);


      assertThat(service.checkKing(game, f, 2, 7)).isTrue();
    }
}

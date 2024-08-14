package com.example.chess;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
}

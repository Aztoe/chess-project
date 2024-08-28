package com.example.chess.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GameTest {

	private Game g;
	
	@BeforeEach
	public void before() {
		g = new Game();
	}
	
	
	
	@Test
	@DisplayName("플레이어가 바뀌어야 한다.")
	public void changePlayerTest() {
		//given
		g.setCurrentPlayer(0);
		
		
		//when
		g.changePlayer();
		
		//then
		assertThat(g.getCurrentPlayer()).isEqualTo(1);
		
	}

}

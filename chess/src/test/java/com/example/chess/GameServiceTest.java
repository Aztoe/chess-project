package com.example.chess;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.chess.domain.Figure;
import com.example.chess.domain.FigureName;
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
	 	@DisplayName("Bishop이 규칙대로 이동")
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
	 @DisplayName("ROOK이 규칙대로 이동")
	 public void checkRookTest() {
		 //pawn 위치 이동
		 game.getFigureAt(0, 6).setY(4);
		 
		 assertThat(service.checkRook(game, 0, 7, 0, 5)).isTrue();
		 
		 assertThat(service.checkRook(game, 0, 7, 0, 6)).isTrue();
		 
		 assertThat(service.checkRook(game, 3, 4, 2, 4)).isTrue();
		 
		 assertThat(service.checkRook(game, 3, 4, 4, 4)).isTrue();
		 //board 밖으로
		 assertThat(service.checkRook(game, 4, 4, -1, 0)).isFalse();
		 //
		 assertThat(service.checkRook(game, 3, 3, 4, 4)).isFalse();
	 }
	 
	@Test
	@DisplayName("PAWN이 규칙대로 이동")
	public void checkPawnTest() {
		Figure pawn = game.getFigureAt(0, 6);
		
		assertThat(service.checkPawn(game, pawn,0, 4)).isTrue();
		
		assertThat(service.checkPawn(game, pawn, 0, 5)).isTrue();
		
		assertThat(service.checkPawn(game, pawn, 1, 5)).isFalse();
		
		pawn.setY(2);
		pawn.setX(1);
		//대각선 
        assertThat(service.checkPawn(game, pawn, 2, 1)).isTrue();
        //대각선
        assertThat(service.checkPawn(game, pawn, 0, 1)).isTrue();
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
    
    @Test
    public void findKingTest() {
        for(long i = 0; i < 8; i++){
            for(long j = 0; j <  8; j++) {
                if(game.getFigureAt((int)i, (int)j) != null)
                    game.getFigureAt((int)i,(int)j).setId(i+j);
            }
        }
        service.findKing(game);
        assertThat(game.getWhiteKingId()).isNotNull();
        assertThat(game.getBlackKingId()).isNotNull();
    }
    
    @Test
    @DisplayName("체크 상태에서 킹이 체크 상태인지 확인한다")
    public void isCheckTest() {
    	for(long i = 0; i < 8; i++){
            for(long j = 0; j <  8; j++) {
                if(game.getFigureAt((int)i, (int)j) != null)
                    game.getFigureAt((int)i,(int)j).setId(i+j);
            }
    	}
    	service.findKing(game);
    	
    	Figure k1 = game.getFigureAt(4, 7);
    	k1.setY(2);
    	  assertThat(k1).isNotNull();
    	game.setCurrentPlayer(0);
    	
    	assertThat(service.isCheck(game)).isTrue();
    	
    	k1.setY(3);
    	
    	assertThat(service.isCheck(game)).isFalse();
    	
    	//black king
    	game.setCurrentPlayer(1);
    	k1.setY(2);
    	assertThat(service.isCheck(game)).isTrue();
    	
    }
    
    
    @Test
    public void enablePromotePawnTest() {
        Figure f = game.getFigureAt(0,6);
        assertThat(FigureName.stringToFigureName(f.getName())).isEqualTo(FigureName.PAWN);

        assertThat(service.enablePawnPromote(f)).isFalse();

        f.setY(0);
        assertThat(service.enablePawnPromote(f)).isTrue();
    }

}

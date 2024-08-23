package com.example.chess.domain;

import java.util.Arrays;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Game {
	
	private static final int S_CONVERT = 1000;
	
	public static final int NUMBER_OF_PLAYER_IN_GAME = 2;

    public static final List<String> FIGURES_PLACEMENT = Arrays.asList(
    		"rook",
            "knight",
            "bishop",
            "queen",
            "king",
            "bishop",
            "knight",
            "rook"
            
    );
    

    public static final List<String> FIGURES_PROMOTION = Arrays.asList(
    		"rook",
            "knight",
            "bishop",
            "queen"
           
    );
    
    public static final int START_PLAYER = PlayerName.WHITE.ordinal();
    
   
    
	public static final int WIDTH =8;
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "games_seq_sen")
	@SequenceGenerator(name = "games_seq_gen" ,sequenceName = "games_id_seq")
	private Long id;
	
	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "game")
	private List<Figure> grid;
	
	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "game")
    private List<Move> moves;
	
	@OneToOne
	private User whitePlayer =null;
	@OneToOne
	private User blackPlayer = null;
		
	@Column(nullable = false)
	private Integer currentPlayer =START_PLAYER;
	
	@Column
	private Long whiteKingId;
	
	@Column 
	private Long blackKingId;
	@Column
	private Integer isCheck=0;
	
	@Column
	private Long timeWhitePlayer;

	@Column
	private Long timeBlackPlayer;

	@Column
	private Long gameTime;
	@Column
	private Boolean isFinish = false;
	@Column
	private Boolean isPause = false;
	
	
	
	    public void changePlayer() {
	        this.currentPlayer = 1 - this.currentPlayer;
	    }
	@Enumerated
	private PlayerName winner;    
	    
	public Figure getFigureAt(int x , int y) {
		
		if (x < 0 || x > 7 || y < 0 || y > 7) {
            return null;
        }

		for (Figure figure : grid) {
			if(figure.getX() ==x && figure.getY() ==y) {
				return figure;
			}
		}
		return null;
	}
	
    public boolean isCellFree(int x, int y) {
        return getFigureAt(x, y) == null;
    }
    

   public Figure getFigureById(Long id) {
	   for (Figure f: grid) {
		   if(f.getId().equals(id)) {
			   return f;
		   }
	   }
	   return null;
   }
   
  
    
    public Long getTimeCurrentPlayer() {
        if (currentPlayer == PlayerName.WHITE.ordinal()) {
            return timeWhitePlayer;
        } else if (currentPlayer == PlayerName.BLACK.ordinal()) {
            return timeBlackPlayer;
        }

        return null;
    }
    
    public void setTimeCurrentPlayer(Long time) {
        if (currentPlayer == PlayerName.WHITE.ordinal()) {
            timeWhitePlayer = time;
        } else if (currentPlayer == PlayerName.BLACK.ordinal()) {
            timeBlackPlayer = time;
        }
    }
    
    public void setGameTime() {
        this.gameTime = System.currentTimeMillis();
    }

    public User getCurrentUser() {
        if (currentPlayer == PlayerName.BLACK.ordinal()) {
            return getBlackPlayer();
        } else if (currentPlayer == PlayerName.WHITE.ordinal()) {
            return getWhitePlayer();
        }

        return null;
    }
    
    public int getNumberOfPlay(int player) {
        int count = 0;

        for (Figure f: grid) {
            if (player == f.getOwner()) {
                count = count + f.getMoveCount();
            }
        }

        return count;
    }
    
    public User getUserWinner() {
    	if(winner ==PlayerName.BLACK) {
    		return blackPlayer;
    	} else {
    		return whitePlayer;
    	}
    }
    
    public User getUserLooser() {
    	if(winner ==PlayerName.BLACK) {
    		return whitePlayer;
    		
    	}else {
    		return blackPlayer;
    	}
    }
    
    public Long getTimeElasped(final Long time) {
    	if(time ==null) {
    		return 0L;
    	}
    	
    	return (System.currentTimeMillis() - time) /S_CONVERT;
    }
}

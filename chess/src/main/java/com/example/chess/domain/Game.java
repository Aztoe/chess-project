package com.example.chess.domain;

import java.util.Arrays;
import java.util.List;

import jakarta.annotation.Generated;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Game {
	
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
    
	public static final int WIDTH =8;
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "games_seq_sen")
	@SequenceGenerator(name = "games_seq_gen" ,sequenceName = "games_id_seq")
	private Long id;
	
	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "game")
	private List<Figure> grid;

	
	
	
	
	public Figure getFigureAt(int x , int y) {
		for (Figure figure : grid) {
			if(figure.getX() ==x && figure.getY() ==y) {
				return figure;
			}
		}
		return null;
	}
	
	
	
}

package com.example.chess.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class GameList {

	 	@Id
	    @Column
	    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "games_list_seq_gen")
	    @SequenceGenerator(name = "games_list_seq_gen", sequenceName = "games_list_id_seq")
	    private Long id;

	    @Column
	    private String winner;

	    @Column
	    private String looser;

	    @Column
	    private Long gameId;
}

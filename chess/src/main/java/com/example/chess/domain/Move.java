package com.example.chess.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Move {

	
		@ManyToOne
		private Game game;
		
	   	@Id
	    @Column
	    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "moves_seq_gen")
	    @SequenceGenerator(name = "moves_seq_gen", sequenceName = "moves_id_seq")
	    private Long id;
	    @Column
	    private String positionEnd;
	    @Column
	    private String positionStart;
	    @Column
	    private Integer player;

	    @Column
	    private Long time;
}

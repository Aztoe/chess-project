package com.example.chess.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class GameRequest {

	 	@Id
	    @Column
	    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "game_request_seq_gen")
	    @SequenceGenerator(name = "game_request_seq_gen", sequenceName = "game_request_id_seq")
	    private Long id;

	    
	    @ManyToOne
	    private User receiver;

	    @ManyToOne
	    private User sender;

	    @Column
	    private Boolean isAccepted;

	    

	    @Override
	    public String toString() {
	        return "GameRequest{id=" + id +
	               ", sender=" + (sender != null ? sender.getUsername() : "null") +
	               ", receiver=" + (receiver != null ? receiver.getUsername() : "null") +
	               ", isAccepted=" + isAccepted + '}';
	    }
}

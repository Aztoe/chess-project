package com.example.chess.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class FriendRequest {
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "friend_request_seq_qen")
	 @SequenceGenerator(name = "friend_request_seq_gen", sequenceName = "friend_request_id_seq")
	private Long Id;
	
	@ManyToOne
	private User receiver;
	
	@Column
	private String sender;
	
	@Column
	private Boolean isAccepted;
	
	
}

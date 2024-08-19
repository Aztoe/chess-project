package com.example.chess.domain;

import org.springframework.security.core.GrantedAuthority;

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
public class Authority implements GrantedAuthority {
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "authority_seq_gen")
    @SequenceGenerator(name = "authority_seq_gen", sequenceName = "authority_id_seq")
	private Long id;
	
	private String authority;

	
	
	
	
	

}

package com.example.chess.domain;

import java.util.Collection;

import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "chess_user")
@Getter @Setter
public class User implements UserDetails {
	
		@Id
	    @Column
	    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chess_users_seq_gen")
	    @SequenceGenerator(name = "chess_users_seq_gen", sequenceName = "chess_users_id_seq")
	    private Long id;

	    @Column(unique = true)
	    private String username;

	    private String password;

	    @Column(unique = true)
	    private String email;

	    @Transient
	    private String confirmPassword;
	   
	    @Column
	    private Boolean isLogIn = false;
	    
	    @Column
	    private Boolean isPlaying = false;


	    @ManyToMany(fetch = FetchType.EAGER)
	    private Collection<Authority> authorities;

		@Override
		public boolean isAccountNonExpired() {
			return true;
		}

		@Override
		public boolean isAccountNonLocked() {
			return true;
		}

		@Override
		public boolean isCredentialsNonExpired() {
			return true;
		}

		@Override
		public boolean isEnabled() {
			return true;
		}

		

		public Boolean getIsPlaying() {
			return isPlaying;
		}

		public void setIsPlaying(Boolean isPlaying) {
			this.isPlaying = isPlaying;
		}
		
	    
	    
}

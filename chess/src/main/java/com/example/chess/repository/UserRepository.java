package com.example.chess.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.chess.domain.User;

public interface UserRepository extends JpaRepository<User, Long >{
	
	public User findByUsername(String username);

}

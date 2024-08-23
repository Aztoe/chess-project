package com.example.chess.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.chess.domain.Game;
import com.example.chess.domain.User;

@Repository
public interface GameRepository extends JpaRepository<Game, Long>{
	
	List<Game> findByWhitePlayerOrBlackPlayerAndIsFinish(User white, User black, Boolean isFinish);
}

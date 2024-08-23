package com.example.chess.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.chess.domain.GameList;
@Repository
public interface GameListRepository extends JpaRepository<GameList, Long> {
	@Query
	public GameList findByGameId(Long gameId);
	List<GameList> findByWinnerOrLooser(String winner, String looser);
}

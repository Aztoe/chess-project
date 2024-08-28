package com.example.chess.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.chess.domain.Figure;
import com.example.chess.domain.Game;

@Repository
public interface FigureRepository extends JpaRepository<Figure, Long>{
		long deleteByGame(Game game);
}

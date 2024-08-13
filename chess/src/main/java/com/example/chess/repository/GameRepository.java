package com.example.chess.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.chess.domain.Game;

@Repository
public interface GameRepository extends JpaRepository<Game, Long>{

}

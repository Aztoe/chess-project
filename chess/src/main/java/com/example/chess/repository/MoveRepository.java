package com.example.chess.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.JpaRepositoryConfigExtension;
import org.springframework.stereotype.Repository;

import com.example.chess.domain.Move;
@Repository
public interface MoveRepository extends JpaRepository<Move, Long>{

}

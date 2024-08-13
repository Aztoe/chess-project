package com.example.chess.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.chess.domain.Figure;

@Repository
public interface FigureRepository extends JpaRepository<Figure, Long>{

}

package com.example.chess.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.chess.domain.Game;
import com.example.chess.repository.FigureRepository;
import com.example.chess.repository.GameRepository;
import com.example.chess.service.GameService;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
@RequestMapping("/game")
public class GameController {
    @Autowired
    private GameService gameService;

    @Autowired
    private GameRepository games;

    @Autowired
    private FigureRepository figures;


    @GetMapping("/")
    public String display(final Model model) {
        games.deleteAll();
        figures.deleteAll();

        // 
        Game g = new Game();

        games.save(g);

        // 체스판 생성
        gameService.generateGrid(g);

        // save generated figures
        figures.saveAll(g.getGrid());
        log.info("figures saved from game/");

        model.addAttribute("game", g);

        return "game-start";
    }
}
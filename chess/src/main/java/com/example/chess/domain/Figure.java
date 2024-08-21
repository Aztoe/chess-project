package com.example.chess.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;



@Entity
@Getter 
public class Figure {
    public static final String CODE_PREFIX = "&#";

    public static final int CODE_NUMBER = 9812; //체스 말 번호

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "figures_seq_gen")
    @SequenceGenerator(name = "figures_seq_gen", sequenceName = "figures_id_seq")
    private Long id;

    @Column
    Integer	code;

    @Column
    Integer x;

    @Column
    Integer y;
    
    
    
    @Column
    String name;

    @Column
    int owner;
    
    @Column
    int moveCount;

    @ManyToOne
    private Game game;

    
    public void setId(Long id) {
        this.id = id;
    }
    public void setCode(int code) {
        if (code >= 0 && code <= 5) {
            this.code = code;
        }
    }
    
    public void setX(int x) {
        if (x >= 0 && x <= 7) {
            this.x = x;
        }
    }
    
    public void setY(int y) {
        if (y >= 0 && y <= 7) {
            this.y = y;
        }
    }
    
    public void setOwner(int owner) {
        this.owner = owner;
    }
    
    public void setGame(Game game) {
        this.game = game;
    }

    
    public void setName(String name) {
        if (FigureName.stringToFigureName(name) != null) {
            this.name = name;
        }
    }

    public String getHtmlCode() {
        String htmlCode = CODE_PREFIX + (CODE_NUMBER + code) + ";";

        if (owner == 1) {
            htmlCode = CODE_PREFIX + (CODE_NUMBER + code + 6) + ";";
        }

        return htmlCode;
    }
    
    public void updateMoveCount() {
    	this.moveCount += 1;
    }
   //체스판 A~H까지
    public String getMoveCode() {
        return (char) (x + 65) + Integer.toString(8 - y);
    }

	
	
	
	
}

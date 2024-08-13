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
@Getter @Setter
public class Figure {
    public static final String CODE_PREFIX = "&#";

    public static final int CODE_NUMBER = 9812;

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "figures_seq_gen")
    @SequenceGenerator(name = "figures_seq_gen", sequenceName = "figures_id_seq")
    private Long id;

    @Column
    int code;

    @Column
    int x;

    @Column
    int y;

    @Column
    String name;

    @Column
    int owner;

    @ManyToOne
    private Game game;

   

    public String getHtmlCode() {
        String htmlCode = CODE_PREFIX + (CODE_NUMBER + code) + ";";

        if (owner == 1) {
            htmlCode = CODE_PREFIX + (CODE_NUMBER + code + 6) + ";";
        }

        return htmlCode;
    }

	
	
	
	
}

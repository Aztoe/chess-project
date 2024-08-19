package com.example.chess.controller.form;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PromoteForm {
	
	private Long id;
	@NotEmpty
	private String name;
	
	
}

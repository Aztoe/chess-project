package com.example.chess.controller.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
@Getter @Setter
public class UserForm {
	
	private Long id;
	@NotEmpty
	@Size(min = 6, max = 32)
	private String username;
	@NotEmpty
	@Email
	private String email;
	
	private String password;
	
	private String confirmPassword;
	
}

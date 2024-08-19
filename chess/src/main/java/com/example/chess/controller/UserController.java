package com.example.chess.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.chess.controller.form.UserForm;
import com.example.chess.domain.User;
import com.example.chess.service.DbUserDetailService;
import com.example.chess.service.UserService;
import com.example.chess.service.impl.userServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

	
    private final DbUserDetailService userService;
	
	private final UserService user;
    @GetMapping("/login")
    public String login() {
        return "user/login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", user.createForm(null));
        return "user/register";
    }

    @PostMapping("/register")
    public String addUser(@Valid @ModelAttribute("user") UserForm form, BindingResult result, Model model) {
       
    	User nickname =user.findByUsername(form.getUsername());
    	
    	if(nickname !=null) {
    		result.rejectValue("nickname", null, "이미 중복된 닉네임이 있습니다");
    	}
    	
    	if (result.hasErrors()) {
            model.addAttribute("user", form);
            return "user/register";
        }
     
        user.register(form);
        log.info("successfully created user, need to register it in database");


        return "redirect:/login";
    }
}

package com.example.chess.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.thymeleaf.extras.springsecurity6.dialect.SpringSecurityDialect;

import com.example.chess.service.DbUserDetailService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)

@RequiredArgsConstructor
public class SecurityConfig   {
	
		private final DbUserDetailService userDetailService;
	
	
	    @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	        http
	            .authorizeHttpRequests(authorize -> authorize
	                .requestMatchers("/resources/**", "/register" ,"/").permitAll()
	                .anyRequest().authenticated()
	            )
	            .formLogin(form -> form
	                .loginPage("/login")
	                .permitAll()
	                .defaultSuccessUrl("/", true)
	            )
	            .logout(logout -> logout
	                .logoutUrl("/logout")
	                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
	                .invalidateHttpSession(true)
	                .permitAll()
	            );

	        return http.build();
	    }
	  
	  @Bean
	  public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
		  
		  return http.getSharedObject(AuthenticationManagerBuilder.class)
				  .authenticationProvider(authenticationProvider())
				  .build();	
	  }
	  
	  @Bean
	  public DaoAuthenticationProvider authenticationProvider() {
		  	DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	        authProvider.setUserDetailsService(userDetailService);
	        authProvider.setPasswordEncoder(encoder());
	        return authProvider;
	  }
	  @Bean
	    public PasswordEncoder encoder() {
	        return new BCryptPasswordEncoder();
	    }
	  
	
	  @Bean
	  public SpringSecurityDialect springSecurityDialect() {
		  return new SpringSecurityDialect();
	  }
	
	
}

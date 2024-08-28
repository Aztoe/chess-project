package com.example.chess;
import static org.mockito.Mockito.lenient;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.chess.controller.form.UserForm;
import com.example.chess.domain.User;
import com.example.chess.repository.UserRepository;
import com.example.chess.service.DbUserDetailService;
import com.example.chess.service.UserService;
import com.example.chess.service.impl.userServiceImpl;
@ExtendWith(MockitoExtension.class)
class DbUserDetailServiceTest {

	  	@Mock
	    private UserRepository users;
	  	
	  	@Mock
	    private PasswordEncoder passwordEncoder;

	    @InjectMocks
	    private DbUserDetailService service;
	    @InjectMocks 
	    private userServiceImpl userService;
	    
	    @Test

	    public void createFormNullTest() {
	        UserForm result = service.createForm(null);

	        assertThat(result).isNotNull();
	    }
	    
	    @Test
	    @DisplayName("User 객체를 기반으로 UserForm 객체를 생성할 수 있어야 한다.")

	    public void createFormTest() {
	        User u = new User();
	        u.setId(3L);
	        u.setEmail("foo@gmail.com");
	        u.setUsername("john.doe");

	        UserForm result = service.createForm(u);
	        assertThat(result.getEmail()).isEqualTo("foo@gmail.com");
	        assertThat(result.getId()).isEqualTo(3L);
	        assertThat(result.getUsername()).isEqualTo("john.doe");
	    }
	    
	    @Test
	    @DisplayName("UserForm 객체를 통해 새로운 사용자를 등록할 수 있어야 한다.")

	    public void saveUserTest() {
	        User u = new User();
	        u.setId(0L);
	        u.setEmail("foo@gmail.com");
	        u.setUsername("john.doe");

	        UserForm result = service.createForm(u);
	        result.setPassword("azerty123$");
	        result.setConfirmPassword("azerty123$");

	        userService.register(result);
	        
	        

	        lenient().when(users.save(Mockito.any(User.class))).thenReturn(u);
	    }

	    @Test
	    @DisplayName("User 객체의 비밀번호가 설정되어 있는지 확인한다.")

	    void testPasswordIsNotNull() {
	        User user = new User();
	        user.setPassword("encodedPassword");  // 비밀번호를 설정합니다.

	        String password = user.getPassword();

	        assertThat(password).isNotNull();
	    }

	  
}

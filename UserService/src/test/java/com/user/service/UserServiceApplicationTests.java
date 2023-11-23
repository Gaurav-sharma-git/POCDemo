package com.user.service;

import com.user.service.entities.Todo;
import com.user.service.entities.User;
import com.user.service.repositories.UserRepository;
import com.user.service.services.UserService;
import com.user.service.services.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UserServiceApplicationTests {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserServiceImpl userService;

//	@BeforeEach
//	public void setUp() {
//		// Create a mock userRepository
//		User user = new User();
//		user.setName("testuser");
//		user.setEmail("test@gmail");
//		Mockito.when(userRepository.save(user)).thenReturn(user);
//	}
	@Test
	void contextLoads() {
		User user = new User();
		user.setName("testuser");
		user.setEmail("test@gmail");
		Mockito.when(userRepository.save(user)).thenReturn(user);
		assertEquals(user.getEmail(),userService.saveUser(user).getEmail());

	}

}

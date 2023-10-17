package com.user.service.services;

import com.user.service.entities.Todo;
import com.user.service.entities.User;
import com.user.service.exceptions.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    User saveUser(User user);

    List<User> getAllUser();

    User getUser(Long id);

    User deleteUser(Long id);

    User updateUserTodoStatus(Long userId, Long todoId, String status);
    //todo delete update
}

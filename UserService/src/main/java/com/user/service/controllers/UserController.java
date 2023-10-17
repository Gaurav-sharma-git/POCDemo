package com.user.service.controllers;

import com.user.service.entities.Todo;
import com.user.service.entities.User;
import com.user.service.exceptions.ApiResponse;
import com.user.service.services.TodoServiceClient;
import com.user.service.services.UserService;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    TodoServiceClient todoServiceClient;

    @PostMapping
    public ResponseEntity<ApiResponse<User>> createUser(@RequestBody User user){
        User user1 = userService.saveUser(user);
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK.value(), "User created",user1), HttpStatus.CREATED);
    }

    @PostMapping("/{userId}/todo")
    public ResponseEntity<User> createUserWithTodo(@PathVariable Long userId, @RequestBody Todo todo) {
        //get
        User user= userService.getUser(userId);
        if (user != null) {
            todo.setUserId(userId);
            todoServiceClient.createTodo(todo);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/{userId}")
    @CircuitBreaker(name="TodoServiceBreaker", fallbackMethod = "todoServiceFallback")
    public ResponseEntity<ApiResponse<User>> getUser(@PathVariable Long userId){
    User user= userService.getUser(userId);
    return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK.value(), "sucess",user),HttpStatus.OK);
    }

    @GetMapping
    @CircuitBreaker(name="TodoServiceBreaker", fallbackMethod = "todoServiceFallback")
    //@Cacheable(value = "test", key = "'test'")
    public ResponseEntity<List<User>> getAllUser(){
      List<User> allUser = userService.getAllUser();
      return new ResponseEntity<>(allUser,HttpStatus.OK);
    }

    @PutMapping("/{userId}/todos/{todoId}")
    @CircuitBreaker(name="TodoServiceBreaker", fallbackMethod = "todoServiceFallback")
    public ResponseEntity<ApiResponse> updateTodoStatus(
            @PathVariable Long userId,
            @PathVariable Long todoId,
            @RequestBody String status
    ) {
        try {
            // Make a call to the Todo service to update the todo status
            userService.updateUserTodoStatus(userId, todoId, status);
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK.value(), "updated", null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }


    @DeleteMapping("/{userId}")
    @CircuitBreaker(name="TodoServiceBreaker", fallbackMethod = "todoServiceFallback")
    public ResponseEntity<User> deleteUser(@PathVariable Long userId){
        User user = userService.deleteUser(userId);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }


    // fallback method for circuit breaker
    public ResponseEntity<ApiResponse<User>> todoServiceFallback(FeignException ex) {

        System.out.println("fallbackkkk");

        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "service is down",null),HttpStatus.BAD_REQUEST);
    }

}

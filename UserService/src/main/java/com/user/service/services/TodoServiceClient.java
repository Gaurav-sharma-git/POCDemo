package com.user.service.services;

import com.user.service.entities.Todo;
import com.user.service.exceptions.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@FeignClient(url = "http://localhost:8083", name = "Todo-service")
public interface TodoServiceClient {

    @GetMapping("/todo/{userId}")
    List<Todo> getTodosByUserId(@PathVariable Long userId);

    @PostMapping("/todo")
    ResponseEntity<Todo> createTodo(@RequestBody Todo todo);

    @DeleteMapping("/todo/{todoId}")
    void deleteTodo(@PathVariable Long todoId);

    @PutMapping("/todo/update/{todoId}")
    Todo updateTodoStatus(@PathVariable Long todoId, @RequestBody String status);
}

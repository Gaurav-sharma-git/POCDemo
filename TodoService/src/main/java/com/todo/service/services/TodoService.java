package com.todo.service.services;

import com.todo.service.entities.Todo;

import java.util.List;

public interface TodoService {
    List<Todo> getTodosByUserId(Long userId);

    List<Todo> getAllTodo();
    Todo saveTodo(Todo todo);

    Todo updateTodoStatus(Long id , String status);

    void deleteTodo(Long id);
}

package com.todo.service.services;

import com.todo.service.entities.Todo;
import com.todo.service.entities.TodoStatus;
import com.todo.service.entities.User;
import com.todo.service.exceptions.ResourceNotFoundException;
import com.todo.service.repositories.TodoRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoServiceImpl implements TodoService{

    @Autowired
    TodoRepository todoRepository;
    @Autowired
    UserServiceClient userServiceClient;
    @Override
    public List<Todo> getTodosByUserId(Long userId) {
        return todoRepository.findByUserId(userId);
    }

    @Override
    public Todo saveTodo(Todo todo) {
        try {
            ResponseEntity<User> userResponse = userServiceClient.getUser(todo.getUserId());
        } catch (FeignException.NotFound e){
        throw new ResourceNotFoundException("user not found with id"+todo.getUserId());
    }
        return todoRepository.save(todo);
    }

    @Override
    public Todo updateTodoStatus(Long id , String status) {
        Todo todoFromDb = todoRepository.findById(id).get();
        TodoStatus newStatus = TodoStatus.valueOf(status);
        todoFromDb.setTodoStatus(newStatus);
        todoRepository.save(todoFromDb);
        return todoFromDb;
    }

    @Override
    public void deleteTodo(Long id) {
        todoRepository.deleteById(id);
    }
}

package com.todo.service.services;

import com.todo.service.entities.Todo;
import com.todo.service.entities.TodoStatus;
import com.todo.service.entities.User;
import com.todo.service.exceptions.ResourceNotFoundException;
import com.todo.service.repositories.TodoRepository;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TodoServiceImpl implements TodoService{

    @Autowired
    TodoRepository todoRepository;

    @Override
    public List<Todo> getTodosByUserId(Long userId) {
        log.info("into getTodoByUserId service layer");
        return todoRepository.findByUserId(userId);
    }

    @Override
    public List<Todo> getAllTodo() {
        log.info("into getAllTodo service layer");
        List<Todo> todoList = todoRepository.findAll();
        return todoList;
    }

    @Override
    public Todo saveTodo(Todo todo) {
//        try {
//            ResponseEntity<User> userResponse = userServiceClient.getUser(todo.getUserId());
//        } catch (FeignException.NotFound e){
//        throw new ResourceNotFoundException("user not found with id"+todo.getUserId());
//    }
        log.info("into saveTodo service layer");
        return todoRepository.save(todo);
    }

    @Override
    public Todo updateTodoStatus(Long id , String status) {
        log.info("into updateTodoStatus service layer");
        Todo todoFromDb = todoRepository.findById(id).get();
        TodoStatus newStatus = TodoStatus.valueOf(status);
        todoFromDb.setTodoStatus(newStatus);
        todoRepository.save(todoFromDb);
        return todoFromDb;
    }

    @Override
    public void deleteTodo(Long id) {
        log.info("into deleteTodo service layer");
        todoRepository.deleteById(id);
    }
}

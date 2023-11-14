package com.todo.service.controller;


import com.todo.service.entities.Todo;
import com.todo.service.entities.User;
import com.todo.service.exceptions.ApiResponse;
import com.todo.service.services.TodoService;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/todo")
public class TodoController {

    @Autowired
    TodoService todoService;

//    @Autowired
//    UserServiceClient userServiceClient;

    @PostMapping
    public ResponseEntity<ApiResponse> createTodo(@RequestBody Todo todo){
        log.info("into create todo controller");
        Todo savedTodo = todoService.saveTodo(todo);
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.CREATED.value(), "Todo created successfully", savedTodo), HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Todo>> getTodosByUserId(@PathVariable Long userId) {
        log.info("into getTodoByUserId controller");
        List<Todo> todoList= todoService.getTodosByUserId(userId);
        return new ResponseEntity<>(todoList,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Todo>> getAllTodos(){
        log.info("into getAllTodos controller");
        List<Todo> todoList=todoService.getAllTodo();
        return new ResponseEntity<>(todoList,HttpStatus.OK);

    }    @PutMapping("/update/{todoId}")
    public ResponseEntity<Todo> updateTodoStatus(@PathVariable Long todoId, @RequestBody String status){
        try {
            log.info("into updateTodoStatus controller todo controller");
            Todo todo = todoService.updateTodoStatus(todoId, status);
            return new ResponseEntity<>(todo,HttpStatus.OK);
        }catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{todoId}")
    public void deleteTodo(@PathVariable Long todoId){
        log.info("into delete todo controller");
        todoService.deleteTodo(todoId);
    }
}

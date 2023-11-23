package com.user.service.services;

import com.user.service.entities.Todo;
import com.user.service.entities.User;
import com.user.service.exceptions.ApiResponse;
import com.user.service.exceptions.DataBaseException;
import com.user.service.exceptions.ResourceNotFoundException;
import com.user.service.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;

    @Autowired
    TodoServiceClient todoServiceClient;

    @Override
    public User saveUser(User user) {
        User savedUser = null;
        try {
            savedUser = userRepository.save(user);
        } catch (Exception e) {
            throw new DataBaseException("unable to save user to database");
        }
        return savedUser;
    }

    @Override
    @Cacheable(value = "userCache", key = "'all-users'")
    public List<User> getAllUser() {
        List<User> allUser = userRepository.findAll();
        for(User user : allUser){
            user.setTodo(todoServiceClient.getTodosByUserId(user.getId()));
        }
        return allUser;
    }

    @Override
    @Cacheable(value = "userCache", key = "#id")
    public User getUser(Long id) {
        User user= userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("user not found with id"+id));
        //List<Todo> todoList= todoServiceClient.getTodosByUserId(id);
        //user.setTodo(todoList);
        return user;
    }

    @Override
    @CacheEvict(value = "userCache", key = "#id")
    public User deleteUser(Long id) {
        User user= userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("user not found with id"+id));
        // Retrieve all associated todos
        List<Todo> todos = todoServiceClient.getTodosByUserId(id);
        for (Todo todo : todos) {
            todoServiceClient.deleteTodo(todo.getId());
        }
         userRepository.deleteById(id);
        return user;
    }

    @Override
    @CachePut(value = "userCache",key = "#userId")
    public User updateUserTodoStatus(Long userId, Long todoId, String status) {
        User user= userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("user not found with id"+userId));
        List<Todo> todos = todoServiceClient.getTodosByUserId(userId);
        for (int i = 0; i < todos.size(); i++) {
            if (todos.get(i).getId() == todoId) {
                Todo updatedTodo = todoServiceClient.updateTodoStatus(todoId, status);
                todos.set(i, updatedTodo); // Replace the old Todo with the updated one
                break;
            }
        }
        user.setTodo(todos);//set
        return user;
    }

}

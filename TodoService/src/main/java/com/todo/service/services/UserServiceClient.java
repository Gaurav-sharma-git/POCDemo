package com.todo.service.services;


import com.todo.service.entities.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "http://localhost:8082", name = "user-service")
public interface UserServiceClient {

    @GetMapping("users/{userId}")
    public ResponseEntity<User> getUser(@PathVariable Long userId);
}

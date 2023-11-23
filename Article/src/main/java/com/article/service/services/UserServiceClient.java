package com.article.service.services;



import com.article.service.entities.SubscriberDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "http://localhost:8082",name = "user-service")
public interface UserServiceClient {

    @GetMapping("users/{userId}")
    SubscriberDTO getUser(@PathVariable Long userId);
}

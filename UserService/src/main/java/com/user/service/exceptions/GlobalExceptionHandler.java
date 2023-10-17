package com.user.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handlerResourceNotFoundException(ResourceNotFoundException resourceNotFoundException) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage(resourceNotFoundException.getMessage());
        apiResponse.setStatus(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataBaseException.class)
    public ResponseEntity<ApiResponse> handlerDataBaseException(ResourceNotFoundException resourceNotFoundException) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage(resourceNotFoundException.getMessage());
        apiResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
    }

}

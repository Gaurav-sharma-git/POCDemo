package com.article.service.exceptions;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        Throwable cause = ex.getRootCause();
        System.out.println("Root cause class: " + cause.getClass().getName());
        if (cause instanceof SQLIntegrityConstraintViolationException) {
            SQLIntegrityConstraintViolationException constraintViolationException = (SQLIntegrityConstraintViolationException) cause;
            String exceptionMessage = constraintViolationException.getMessage();
            if (exceptionMessage.contains("fk_user_id")) {
                return new ResponseEntity<>("Invalid subscriber user id", HttpStatus.BAD_REQUEST);
            } else if (exceptionMessage.contains("fk_article_id")) {
                return new ResponseEntity<>("Invalid article id", HttpStatus.BAD_REQUEST);
            }
        }

        return new ResponseEntity<>("Subscription failed due to a data integrity violation", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


package com.todo.service.exceptions;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse<T>{
    private int status;
    private String message;
    private T data;
}

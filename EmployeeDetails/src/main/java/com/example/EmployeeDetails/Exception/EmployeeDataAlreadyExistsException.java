package com.example.EmployeeDetails.Exception;

import org.springframework.web.bind.annotation.ExceptionHandler;


public class EmployeeDataAlreadyExistsException extends RuntimeException{

    private String message;
    public EmployeeDataAlreadyExistsException(){}
    public EmployeeDataAlreadyExistsException(String message){
        super(message);
        this.message=message;
    }
}

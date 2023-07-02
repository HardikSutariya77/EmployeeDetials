package com.example.EmployeeDetails.Exception;

public class EmployeeDataNotExistException extends RuntimeException{

    private String message;
    EmployeeDataNotExistException(){}
    public EmployeeDataNotExistException(String message){
        super(message);
        this.message=message;
    }
}

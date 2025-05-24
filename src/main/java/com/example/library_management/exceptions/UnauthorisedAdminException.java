package com.example.library_management.exceptions;

public class UnauthorisedAdminException extends Exception{
    public UnauthorisedAdminException(String message){
        super(message);
    }
}

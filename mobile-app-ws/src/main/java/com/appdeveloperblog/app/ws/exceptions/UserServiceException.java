package com.appdeveloperblog.app.ws.exceptions;


public class UserServiceException extends RuntimeException{

    private static final long serialVersionUID = 4110095065088415181L;

    public UserServiceException(String message){
        super(message);
    }
}

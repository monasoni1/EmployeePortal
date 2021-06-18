package com.project.UserPortal.Exceptions;

public class ResourceNotFoundException extends  RuntimeException{
    public ResourceNotFoundException(String message)
    {
        super(message);
    }
}

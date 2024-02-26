package com.andre.plataformavideos.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException (String message){
       super(message);
    }
}

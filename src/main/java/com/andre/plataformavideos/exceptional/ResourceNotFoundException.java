package com.andre.plataformavideos.exceptional;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException (String message){
       super(message);
    }
}

package com.swissquote.battledev2014.shoppinglistgenerator.api;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}

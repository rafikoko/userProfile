package com.rafikoko.userprofileservice.exception;

/**
 * Exception thrown when a resource (e.g. UserProfile) is not found.
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
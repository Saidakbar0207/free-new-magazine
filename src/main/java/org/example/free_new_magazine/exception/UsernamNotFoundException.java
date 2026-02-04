package org.example.free_new_magazine.exception;

public class UsernamNotFoundException extends RuntimeException {
    public UsernamNotFoundException(String message) {
        super(message);
    }
}

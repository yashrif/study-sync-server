package com.amadeus.studysync.auth;

public class AuthenticationUserNotFoundException extends RuntimeException {
    public AuthenticationUserNotFoundException(String message) {
        super(message);
    }

    public AuthenticationUserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthenticationUserNotFoundException(Throwable cause) {
        super(cause);
    }
}

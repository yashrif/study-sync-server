package com.amadeus.studysync.auth;

public class AuthenticationUserExistsException extends RuntimeException {
    public AuthenticationUserExistsException(String message) {
        super(message);
    }

    public AuthenticationUserExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthenticationUserExistsException(Throwable cause) {
        super(cause);
    }
}

package com.amadeus.studysync.auth;

import com.amadeus.studysync.exception.AlreadyExistsException;
import com.amadeus.studysync.exception.NotFoundException;
import com.amadeus.studysync.rest.AppErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AuthControllerExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<AppErrorResponse> handleException(AlreadyExistsException exc) {
        AppErrorResponse error = new AppErrorResponse();

        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(exc.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<AppErrorResponse> handleException(NotFoundException exc) {
        AppErrorResponse error = new AppErrorResponse();

        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(exc.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}

package com.chrisr.userdashboard.controller.exception;

import com.chrisr.userdashboard.exception.AppException;
import com.chrisr.userdashboard.exception.BadRequestException;
import com.chrisr.userdashboard.exception.UserAlreadyExistsException;
import com.chrisr.userdashboard.exception.UserNotFoundException;
import com.chrisr.userdashboard.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        return new ResponseEntity<>(generateErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(AppException ex) {
        return new ResponseEntity<>(generateErrorResponse(ex.getHttpStatus(), ex.getMessage()), ex.getHttpStatus());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(BadRequestException ex) {
        return new ResponseEntity<>(generateErrorResponse(ex.getHttpStatus(), ex.getMessage()), ex.getHttpStatus());
    }

//    @ExceptionHandler
//    public ResponseEntity<ErrorResponse> handleException(IllegalArgumentException ex) {
//        return new ResponseEntity<>(generateErrorResponse(ex.getHttpStatus(), ex.getMessage()), ex.getHttpStatus());
//    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(UserAlreadyExistsException ex) {
        return new ResponseEntity<>(generateErrorResponse(ex.getHttpStatus(), ex.getMessage()), ex.getHttpStatus());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(UserNotFoundException ex) {
        return new ResponseEntity<>(generateErrorResponse(ex.getHttpStatus(), ex.getMessage()), ex.getHttpStatus());
    }

    private ErrorResponse generateErrorResponse(HttpStatus httpStatus, String message) {
        ErrorResponse error = new ErrorResponse();
        error.setStatus(httpStatus.value());
        error.setMessage(message);
        error.setDatetime(LocalDateTime.now().toString());
        return error;
    }
}

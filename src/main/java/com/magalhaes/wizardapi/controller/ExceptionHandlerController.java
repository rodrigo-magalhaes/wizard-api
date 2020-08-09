package com.magalhaes.wizardapi.controller;

import com.magalhaes.wizardapi.dto.ErrorDTO;
import com.magalhaes.wizardapi.exception.HouseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(HouseException.class)
    public ResponseEntity<Object> handleHouseException(HouseException ex, WebRequest request) {
        String message = ex.getMessage() != null ? ex.getMessage() : "Error during house validation";
        return getResponse(ex, request, message, HttpStatus.GATEWAY_TIMEOUT);
    }

    @ExceptionHandler(value = {EntityNotFoundException.class})
    public ResponseEntity<Object> handle(EntityNotFoundException ex, WebRequest request) {
        String message = ex.getMessage() != null ? ex.getMessage() : "Entity not found";
        return getResponse(ex, request, message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handle(Exception ex, WebRequest request) {
        String message = ex.getMessage() != null ? ex.getMessage() : "Error";
        return getResponse(ex, request, message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Object> getResponse(Exception ex, WebRequest request, String message, HttpStatus status) {
        return handleExceptionInternal(ex, new ErrorDTO(message), null, status, request);
    }
}

package com.magalhaes.wizardapi.controller;

import com.magalhaes.wizardapi.dto.ErrorDTO;
import com.magalhaes.wizardapi.exception.HouseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(HouseException.class)
    public ResponseEntity<Object> handleHouseException(HouseException ex, WebRequest request) {
        return getResponse(ex, request, HttpStatus.GATEWAY_TIMEOUT);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handle(Exception ex, WebRequest request) {
        return getResponse(ex, request, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Object> getResponse(Exception ex, WebRequest request, HttpStatus status) {
        return handleExceptionInternal(ex, new ErrorDTO(ex.getMessage()),null, status, request);
    }
}

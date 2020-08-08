package com.magalhaes.wizardapi.exception;

public class HouseException extends RuntimeException {

    public HouseException() {
        super();
    }

    public HouseException(String message) {
        super("Error when requesting house information for id: " + message);
    }

    public HouseException(String message, Throwable cause) {
        super("Error when requesting house information for id: " + message, cause);
    }
}
